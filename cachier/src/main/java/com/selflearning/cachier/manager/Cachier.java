package com.selflearning.cachier.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.selflearning.cachier.Cache;
import com.selflearning.cachier.CacheIdentifier;
import com.selflearning.cachier.constant.CachingScheme;
import com.selflearning.cachier.exception.InvalidCacheIdentifierException;
import com.selflearning.cachier.functional.RefreshCacheInterface;
import com.selflearning.cachier.relationship.RelationshipIdentifier;
import com.selflearning.cachier.utility.CachingUtil;

@Component
@EnableScheduling
public class Cachier {
	private volatile HashMap<CacheIdentifier, Cache> cachingMap = new HashMap<CacheIdentifier, Cache>();
	final private Long defaultRefereshInterval = 100000L;
	final private Long defaultDeathWaitTime = 100000L;
	
	public Optional<String> cacheData(String key, Object data) {
		return cacheData(key, data, CachingScheme.GUID);
	}
	
	public Optional<String> cacheData(String key, Object data, CachingScheme cachingScheme) {
		final String identifier = CachingUtil.getIdentifier(cachingScheme, null);
		return cacheData(key, data, cachingScheme, identifier);
	}
	
	public Optional<String> cacheData(String key, Object data, CachingScheme cachingScheme, String customIdentifier) {
		return cacheData(key, data, cachingScheme, customIdentifier, null);
	}
	
	public Optional<String> cacheData(String key, Object data, CachingScheme cachingScheme, String customIdentifier, RefreshCacheInterface refreshCache) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(cachingScheme, customIdentifier);
		Cache cache = new Cache();
		cache.setCachedDateTime(new Date());
		cache.setData(key, data);
		cache.setCacheIdentifier(cacheIdentifier);
		cache.setRefreshCache(refreshCache);
		cache.setRefreshTimeInterval(defaultRefereshInterval);
		cache.setDeathTime(defaultDeathWaitTime);
		final Cache newCache = cachingMap.putIfAbsent(cacheIdentifier, cache);
		if(newCache == null) {
			return cacheIdentifier.getId();
		}
		System.out.println("Cache Already exists against Id: " + cacheIdentifier);
		return Optional.empty();
	}
	
	public void setDeathTime(CacheIdentifier cacheIdentifier, Long waitingTime) throws InvalidCacheIdentifierException {
		final Cache cache = cachingMap.get(cacheIdentifier);
		if(cache == null) {
			throw new InvalidCacheIdentifierException(cacheIdentifier);
		}
		cache.setDeathTime(System.currentTimeMillis() + waitingTime);
	}
	
	public void setDeathTime(String cacheIdentifier, Long waitingTime) throws InvalidCacheIdentifierException {
		if(!StringUtils.hasText(cacheIdentifier)) {
			throw new InvalidCacheIdentifierException(cacheIdentifier);
		}
		setDeathTime(new CacheIdentifier(cacheIdentifier), waitingTime);
	}
	
	public void addRelationshipAccount(CacheIdentifier cacheIdentifier, RelationshipIdentifier relationshipIdentifier) {
		final Cache cache = cachingMap.get(cacheIdentifier);
		cache.addRelationshipAccount(relationshipIdentifier);
	}
	
	public RelationshipIdentifier getRelationshipAccount(CacheIdentifier cacheIdentifier) throws InvalidCacheIdentifierException {
		final Cache cache = cachingMap.get(cacheIdentifier);
		if(cache == null) {
			throw new InvalidCacheIdentifierException(cacheIdentifier);
		}
		return cache.getRelationshipIdentifier();
	}
	
	public Optional<String> updateCacheData(String key, Object data, String customIdentifier) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(CachingScheme.CUSTOM, customIdentifier);
		final Cache existingCache = cachingMap.get(cacheIdentifier);
		if(existingCache == null) {
			System.out.println("No cache present in the system against Id:" + cacheIdentifier);
			return Optional.empty();
		}
		Cache updatedCache = new Cache();
		updatedCache.setCachedDateTime(existingCache.getCachedDateTime());
		final Object existingData = updatedCache.getData(key);
		if(existingData == null) {
			throw new RuntimeException("Cannot update data as there is no existing for given key: " + key);
		}
		updatedCache.setCacheIdentifier(cacheIdentifier);
		updatedCache.setRefreshCache(existingCache.getRefreshCache());
		updatedCache.setRefreshTimeInterval(existingCache.getRefreshTimeInterval());
		cachingMap.put(cacheIdentifier, updatedCache);
		System.out.println("Updated cache against Id: " + cacheIdentifier);
		return Optional.of(cacheIdentifier.toString());
	}
	
	public Optional<Cache> getData(String identifier) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(CachingScheme.CUSTOM, identifier);
		final Cache cache = cachingMap.get(cacheIdentifier);
		if(cache == null) {
			return Optional.empty();
		}
		return Optional.of(cache);
	}
	
	@Scheduled(fixedDelay = 5000)
	private void cacheCleaner() {
		cachingMap.values().removeIf(cache -> {
			final long currentTimeMillis = System.currentTimeMillis();
			final Long deathTime = cache.getDeathTime();
			if(deathTime == null || deathTime.longValue() < currentTimeMillis) {
				System.out.println("Removing cache with identifier: " + cache.getIdentifierId());
				return true;
			}
			return false;
		});
	}
	
	/*
	 * Needs to cater the cases when cache is updated and old method reference is still in queue of refreshing cache.
	 * @Scheduled(fixedDelay = 5000)
	public void refereshCaching() {
		cachingMap.values().parallelStream().forEach(cache -> {
			final RefreshCacheInterface refreshCacheMethod = cache.getRefreshCache();
			if(refreshCacheMethod != null) {
				final Long refreshTimeInterval = cache.getRefreshTimeInterval();
				if(refreshTimeInterval != null && refreshTimeInterval.longValue() > 0) {
					try {
						Thread.sleep(refreshTimeInterval);
					} catch (InterruptedException exception) {
						System.out.println("Thread failure for refresh cache for identifier: " + cache.getIdentifierId());
						exception.printStackTrace();
					}
				}
				refreshCacheMethod.refreshCache();
			} else {
				System.out.println("No refresh scheduler for cache identifier: " + cache.getIdentifierId());
			}
		});
	}*/
}
