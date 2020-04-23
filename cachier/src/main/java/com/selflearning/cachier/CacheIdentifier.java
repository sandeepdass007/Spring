package com.selflearning.cachier;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StringUtils;

import com.selflearning.cachier.constant.CachingScheme;
import com.selflearning.cachier.utility.CachingUtil;

public class CacheIdentifier {

	private String id;
	
	public Optional<String> getId() {
		if(StringUtils.hasText(id)) {
			return Optional.of(id);
		}
		throw new RuntimeException("Invalid Cache Identified");
	}
	
	CacheIdentifier(CachingScheme cachingScheme) {
		this.id = CachingUtil.getIdentifier(cachingScheme, null);
	}
	
	public CacheIdentifier(CachingScheme cachingScheme, String customIdentifier) {
		this.id = CachingUtil.getIdentifier(cachingScheme, customIdentifier);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		final AtomicInteger val = new AtomicInteger(0);
		this.id.chars().boxed().forEach(x -> val.addAndGet(x));
		int result = 1;
        result = prime * result + val.get();
		return Objects.hashCode(result);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CacheIdentifier cacheIdentifier = (CacheIdentifier)obj;
        return cacheIdentifier.id.equals(id);
	}
	
	@Override
	public String toString() {
		return this.id;
	}
}
