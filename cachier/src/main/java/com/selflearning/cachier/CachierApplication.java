package com.selflearning.cachier;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.selflearning.cachier.exception.InvalidCacheIdentifierException;
import com.selflearning.cachier.manager.Cachier;
import com.selflearning.cachier.manager.relationship.RelationshipManager;
import com.selflearning.cachier.manager.relationship.exception.InvalidRelationshipIdentifierException;

@SpringBootApplication
public class CachierApplication implements CommandLineRunner {

	@Autowired
	private Cachier cachier;
	
	@Autowired
	private RelationshipManager relationshipManager;
	
	public static void main(String[] args) {
		SpringApplication.run(CachierApplication.class, args);
	}

	/**
	 *	for testing purposes
	 */
	@Override
	public void run(String... args) throws Exception {
		final Optional<String> cacheIdentifier1 = cachier.cacheData("Random Data");
		final Optional<String> cacheIdentifier2 = cachier.cacheData("Random Data");
		cacheIdentifier1.ifPresent(identifier -> {
			try {
				relationshipManager
					.addRelationship(new CacheIdentifier(cacheIdentifier2.get()), new CacheIdentifier(cacheIdentifier1.get()));
				relationshipManager
					.getAllRelationships(new CacheIdentifier(cacheIdentifier2.get()))
					.forEach(System.out::println);
				relationshipManager
					.getAllRelationships(new CacheIdentifier(cacheIdentifier1.get()))
					.forEach(System.out::println);
			} catch (InvalidRelationshipIdentifierException | InvalidCacheIdentifierException e) {
				e.printStackTrace();
			}
		});
	}

}
