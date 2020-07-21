package com.selflearning.cachier;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.selflearning.cachier.exception.InvalidCacheIdentifierException;
import com.selflearning.cachier.manager.Cachier;

@SpringBootApplication
public class CachierApplication implements CommandLineRunner {

	@Autowired
	private Cachier cachier;
	
	public static void main(String[] args) {
		SpringApplication.run(CachierApplication.class, args);
	}

	/**
	 *	for testing purposes
	 */
	@Override
	public void run(String... args) throws Exception {
		final Optional<String> cacheIdentifier = cachier.cacheData("key1", "Random Data");
		cacheIdentifier.ifPresent(identifier -> {
			try {
				System.out.println(cachier.getData(identifier).get().getData("key1"));
				System.out.println("Setting death time 5secs for identifier: " + identifier);
				cachier.setDeathTime(identifier, 5000L);
			} catch (InvalidCacheIdentifierException e) {
				e.printStackTrace();
			}
		});
	}

}
