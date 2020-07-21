package com.selflearning.cachier.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selflearning.cachier.Cache;
import com.selflearning.cachier.manager.Cachier;

@Component
public class CachingServiceImpl {
	@Autowired
	private Cachier cachier;
	
	public String cacheStringData(String key, String data) {
		final Optional<String> identifier = cachier.cacheData(key, data);
		if(identifier.isPresent()) {
			return identifier.get();
		}
		return "Error while caching data!";
	}
	
	public Object getCachedStringData(String id, String key) {
		final Optional<Cache> data = cachier.getData(id);
		if(data.isPresent()) {
			return data.get().getData(key);
		}
		return "No Data Found!";
	}
	
	public boolean updateStringData(String identifier, String key, String data) {
		final Optional<String> _identifier = cachier.updateCacheData(key, data, identifier);
		return _identifier.isPresent();
	}
}
