package com.martin.backend.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.martin.backend.service.RepositoryService;

@SuppressWarnings("deprecation")
public class RepositoryServiceTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void repositoryServiceTest() throws IllegalArgumentException, IllegalAccessException {
		RepositoryService repoService = new RepositoryService("JUNIT_COLUMN", "JUNIT_TABLE");
		repoService.jdbcTemplate = jdbcTemplate;

		assertNotNull(repoService);
		assertNotNull(repoService.toString());
		assertNotNull(repoService.hashCode());
		assertNotNull(repoService.getClass());

		assertNotNull(repoService.getQuery());

		when(jdbcTemplate.queryForObject(repoService.getQuery(), String.class)).thenReturn("JUNIT");
		assertNotNull(repoService.getZip());

		assertFalse(repoService.equals(new RepositoryService("JUNIT_COLUMN", "JUNIT_TABLE")));
	}

}
