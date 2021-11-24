package com.martin.backend.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ApplicationConfigTest {

	@Test
	public void applicationConfigTest() {

		ApplicationConfig appConfig = new ApplicationConfig("false");

		assertFalse(appConfig.isHttps());

		assertNotNull(appConfig);
		assertNotNull(appConfig.toString());
		assertNotNull(appConfig.hashCode());
		assertNotNull(appConfig.getClass());

		assertNotNull(appConfig.restTemplate());

		assertNull(appConfig.apiUrl());

		assertFalse(appConfig.equals(new ApplicationConfig("false")));

		appConfig = new ApplicationConfig("true");
		assertTrue(appConfig.isHttps());
		assertNull(appConfig.apiUrl());

		appConfig = new ApplicationConfig(null);
		assertFalse(appConfig.isHttps());

	}

}
