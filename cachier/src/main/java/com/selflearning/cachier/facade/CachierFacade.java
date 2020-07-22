package com.selflearning.cachier.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selflearning.cachier.Cache;
import com.selflearning.cachier.CacheIdentifier;
import com.selflearning.cachier.constant.CachingScheme;
import com.selflearning.cachier.functional.RefreshCacheInterface;
import com.selflearning.cachier.manager.Cachier;
import com.selflearning.cachier.utility.CachingUtil;

@Component
public class CachierFacade {

	@Autowired
	private Cachier cachier;
	
	public Object cacheData(String key, Object data) {
		return cacheData(key, data, CachingScheme.GUID);
	}
	
	public Object cacheData(String key, Object data, CachingScheme cachingScheme) {
		final String identifier = CachingUtil.getIdentifier(cachingScheme, null);
		return cacheData(key, data, cachingScheme, identifier);
	}
	
	public Object cacheData(String key, Object data, CachingScheme cachingScheme, String customIdentifier) {
		return cacheData(key, data, cachingScheme, customIdentifier, null);
	}
	
	public Object cacheData(String key, Object data, CachingScheme cachingScheme, String customIdentifier, RefreshCacheInterface refreshCache) {
		final Optional<String> cacheData = cachier.cacheData(key, data, cachingScheme, customIdentifier, refreshCache);
		return cacheData.isPresent() ? cacheData.get() : null;
	}
	
	public Object getData(String identifier, String key) {
		final Optional<Cache> cache = cachier.getCache(identifier);
		return cache.isPresent() ? cache.get().getData(key) : null;
	}
	
	public<T> T getData(String identifier, String key, Class<T> classType) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(CachingScheme.CUSTOM, identifier);
		final Optional<Cache> cache = cachier.getCache(cacheIdentifier);
		return cache.isPresent() && cache.get().getData(key) != null ? classType.cast(cache.get().getData(key)) : null;
	}
	
	public String updateCacheData(String key, Object data, String customIdentifier) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(CachingScheme.CUSTOM, customIdentifier);
		return updateCacheData(key, data, cacheIdentifier);
	}
	
	public String updateCacheData(String key, Object data, final CacheIdentifier cacheIdentifier) {
		final Optional<String> identifier = cachier.updateCacheData(key, data, cacheIdentifier);
		return identifier.isPresent() ? identifier.get() : null;
	}
	
	public void removeCache(final String identifier) {
		final CacheIdentifier cacheIdentifier = new CacheIdentifier(CachingScheme.CUSTOM, identifier);
		cachier.removeCache(cacheIdentifier);
	}
}
