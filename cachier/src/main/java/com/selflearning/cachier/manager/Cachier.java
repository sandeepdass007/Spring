package com.selflearning.cachier.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.selflearning.cachier.Cache;
import com.selflearning.cachier.CacheIdentifier;
import com.selflearning.cachier.constant.CachingScheme;
import com.selflearning.cachier.functional.RefreshCacheInterface;
import com.selflearning.cachier.utility.CachingUtil;

@Component
@EnableScheduling
public class Cachier {
	private Map<CacheIdentifier, Cache> cachingMap = new HashMap<CacheIdentifier, Cache>();
	final private Long defaultRefereshInterval = 100000L;
	
	public Optional<String> cacheData(Object data) {
		return cacheData(data, CachingScheme.GUID);
	}
	
	public Optional<String> cacheData(Object data, CachingScheme cachingScheme) {
		final String identifier = CachingUtil.getIdentifier(cachingScheme, null);
		return cacheData(data, cachingScheme, identifier);
	}
	
	public Optional<String> cacheData(Object data, CachingScheme cachingScheme, String customIdentifier) {
		return cacheData(data, cachingScheme, customIdentifier, null);
	}
	
	public Optional<String> cacheData(Object data, CachingScheme cachingScheme, String customIdentifier, RefreshCacheInterface refreshCache) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(cachingScheme, customIdentifier);
		Cache cache = new Cache();
		cache.setCachedDateTime(new Date());
		cache.setData(data);
		cache.setCacheIdentifier(cacheIdentifier);
		cache.setRefreshCache(refreshCache);
		cache.setRefreshTimeInterval(defaultRefereshInterval);
		cachingMap.put(cacheIdentifier, cache);
		return cacheIdentifier.getId();
	}
	
	public Optional<Object> getData(String identifier) {
		final Optional<Entry<CacheIdentifier, Cache>> queriedData = cachingMap.entrySet().parallelStream().filter(cacheIdentifier -> {
			if(cacheIdentifier.getKey().getId().get().equalsIgnoreCase(identifier)) {
				return true;
			}
			return false;
		}).findFirst();
		if(queriedData.isPresent()) {
			return Optional.of(queriedData.get().getValue().getData());
		}
		return Optional.empty();
	}
	
	@Scheduled(fixedDelay = 5000)
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
				System.out.println("No referesh scheduler for cache identifier: " + cache.getIdentifierId());
			}
		});
	}
}
