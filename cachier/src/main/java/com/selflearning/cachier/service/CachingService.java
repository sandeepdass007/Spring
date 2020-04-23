package com.selflearning.cachier.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selflearning.cachier.Cache;
import com.selflearning.cachier.manager.Cachier;

@Service
@RequestMapping("/cache")
public class CachingService {

	@Autowired
	private Cachier cachier;
	
	@PostMapping("/set/string/{data}")
	@ResponseBody
	private String cacheStringData(@PathVariable(value = "data") String data) {
		final Optional<String> identifier = cachier.cacheData(String.valueOf(data));
		if(identifier.isPresent()) {
			return identifier.get();
		}
		return "Error while caching data!";
	}
	
	@GetMapping("/get/string/{id}")
	@ResponseBody
	private Object getCachedStringData(@PathVariable(value = "id") String id) {
		final Optional<Cache> data = cachier.getData(id);
		if(data.isPresent()) {
			return data.get().getData();
		}
		return "No Data Found!";
	}
	
	@PutMapping("/update/string/{id}/{data}")
	@ResponseBody
	private boolean updateStringData(@PathVariable(value = "id") String identifier, @PathVariable(value = "data") String data) {
		final Optional<String> _identifier = cachier.updateCacheData(data, identifier);
		return _identifier.isPresent();
	}
}
