package com.selflearning.cachier.exception;

import com.selflearning.cachier.CacheIdentifier;

public class InvalidCacheIdentifierException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3223845967235439362L;
	
	private CacheIdentifier cacheIdentifier;
	
	public InvalidCacheIdentifierException(CacheIdentifier cacheIdentifier) {
		super();
		this.cacheIdentifier = cacheIdentifier;
	}
	
	public InvalidCacheIdentifierException(String cacheIdentifier) {
		super();
		this.cacheIdentifier = new CacheIdentifier(cacheIdentifier);
	}
	
	@Override
	public String toString() {
		return "Cache Identifier is invalid: " + cacheIdentifier;
	}
}
