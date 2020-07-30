package com.selflearning.cachier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import com.selflearning.cachier.facade.CachierFacade;

@SpringBootApplication
public class CachierApplication implements CommandLineRunner {

	@Autowired
	private CachierFacade cachierFacade;
	
	public static void main(String[] args) {
		SpringApplication.run(CachierApplication.class, args);
	}

	/**
	 *	for testing purposes
	 */
	@Override
	public void run(String... args) throws Exception {
		final String cacheIdentifier = (String) cachierFacade.cacheData("key1", "Random Data");
		if(StringUtils.hasText(cacheIdentifier)) {
			cachierFacade.addAlias(cacheIdentifier, "myownkey");
			final String data = cachierFacade.getData("myownkey", "key1", String.class);
			System.out.println(data);
		}
	}

}
