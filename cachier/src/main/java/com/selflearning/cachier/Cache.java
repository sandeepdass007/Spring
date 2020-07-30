package com.selflearning.cachier;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.selflearning.cachier.functional.RefreshCacheInterface;

public class Cache {

	private HashMap<String, Object> data = new HashMap<String, Object>();
	private Date cachedDateTime;
	private Long deathTime;
	private RefreshCacheInterface refreshCache;
	private Long refreshTimeInterval;
	private CacheIdentifier cacheIdentifier;
	private HashSet<CacheIdentifier> aliases = new HashSet<CacheIdentifier>();
	
	public Object getData(String key) {
		return data.get(key);
	}
	public void setData(String key, Object data) {
		this.data.put(key, data);
	}
	public Date getCachedDateTime() {
		return cachedDateTime;
	}
	public void setCachedDateTime(Date cachedDateTime) {
		this.cachedDateTime = cachedDateTime;
	}
	public Long getDeathTime() {
		return deathTime;
	}
	public void setDeathTime(Long deathTime) {
		this.deathTime = deathTime;
	}
	public RefreshCacheInterface getRefreshCache() {
		return refreshCache;
	}
	public void setRefreshCache(RefreshCacheInterface refreshCache) {
		this.refreshCache = refreshCache;
	}
	public Long getRefreshTimeInterval() {
		return refreshTimeInterval;
	}
	public void setRefreshTimeInterval(Long refreshTimeInterval) {
		this.refreshTimeInterval = refreshTimeInterval;
	}
	public CacheIdentifier getCacheIdentifier() {
		return cacheIdentifier;
	}
	public void setCacheIdentifier(CacheIdentifier cacheIdentifier) {
		this.cacheIdentifier = cacheIdentifier;
	}
	public String getIdentifierId() {
		return getCacheIdentifier().getId().get();
	}
	public HashSet<CacheIdentifier> getAliases() {
		return aliases;
	}
}
