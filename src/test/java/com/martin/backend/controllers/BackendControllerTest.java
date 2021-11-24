package com.martin.backend.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.martin.backend.service.RepositoryService;

@SuppressWarnings("deprecation")
public class BackendControllerTest {

	@Mock
	RestTemplate restTemplate;

	@Mock
	RepositoryService repositoryService;

	@Mock
	ResponseEntity<String> response;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void backendControllerTest() {
		BackendController bc = new BackendController();
		bc.apiKey = "junit_key";
		bc.apiUrl = "junit_url";
		bc.repositoryService = repositoryService;
		bc.restTemplate = restTemplate;

		assertNotNull(bc);
		assertNotNull(bc.toString());
		assertNotNull(bc.hashCode());
		assertNotNull(bc.getClass());

		assertFalse(bc.equals(new BackendController()));
		when(repositoryService.getZip()).thenReturn("00000");
		when(restTemplate.exchange(Mockito.eq("junit_url/data/2.5/weather?zip=00000,junit_key&units=imperial"),
				Mockito.eq(HttpMethod.GET), Mockito.eq(null), Mockito.eq(new ParameterizedTypeReference<String>() {
				}))).thenReturn(new ResponseEntity<String>("junit_test_body", HttpStatus.OK));
		String results = bc.getWeatherByZip();

		assertNotNull(results);

	}
}
