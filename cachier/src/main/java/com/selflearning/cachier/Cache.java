package com.selflearning.cachier;

import java.util.Date;

import com.selflearning.cachier.functional.RefreshCacheInterface;
import com.selflearning.cachier.relationship.RelationshipIdentifier;

public class Cache {

	private Object data;
	private Date cachedDateTime;
	private RefreshCacheInterface refreshCache;
	private Long refreshTimeInterval;
	private CacheIdentifier cacheIdentifier;
	private RelationshipIdentifier relationshipIdentifier;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Date getCachedDateTime() {
		return cachedDateTime;
	}
	public void setCachedDateTime(Date cachedDateTime) {
		this.cachedDateTime = cachedDateTime;
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
	public RelationshipIdentifier getRelationshipIdentifier() {
		return relationshipIdentifier;
	}
	public void setRelationshipIdentifier(RelationshipIdentifier relationshipIdentifier) {
		this.relationshipIdentifier = relationshipIdentifier;
	}
	public String getIdentifierId() {
		return getCacheIdentifier().getId().get();
	}
	
	public void addRelationshipAccount(RelationshipIdentifier relationshipIdentifier) {
		setRelationshipIdentifier(relationshipIdentifier);
	}
}
