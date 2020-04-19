package com.selflearning.cachier.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return identifier.get();
	}
	
	@GetMapping("/get/string/{id}")
	@ResponseBody
	private Object getCachedStringData(@PathVariable(value = "id") String id) {
		return cachier.getData(id).get();
	}
}
