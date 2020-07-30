package com.selflearning.cachier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selflearning.cachier.exception.InvalidCacheIdentifierException;
import com.selflearning.cachier.service.impl.CachingServiceImpl;

@Service
@RequestMapping("/cache")
public class CachingService {

	@Autowired
	private CachingServiceImpl cachingServiceImpl;
	
	@PostMapping("/set/string/{key}/{data}")
	@ResponseBody
	private String cacheStringData(@PathVariable(value = "key") String key, @PathVariable(value = "data") String data) {
		return cachingServiceImpl.cacheStringData(key, data);
	}
	
	@GetMapping("/get/string/{id}/{key}")
	@ResponseBody
	private Object getCachedStringData(@PathVariable(value = "id") String id, @PathVariable(value = "key") String key) {
		return cachingServiceImpl.getCachedStringData(id, key);
	}
	
	@PutMapping("/update/string/{id}/{data}")
	@ResponseBody
	private boolean updateStringData(@PathVariable(value = "id") String identifier, @PathVariable(value = "key") String key, @PathVariable(value = "data") String data) throws InvalidCacheIdentifierException {
		return cachingServiceImpl.updateStringData(identifier, key, data);
	}
}
