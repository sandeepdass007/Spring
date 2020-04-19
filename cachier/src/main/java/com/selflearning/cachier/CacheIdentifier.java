package com.selflearning.cachier;

import java.util.Optional;

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
}
