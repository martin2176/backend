package com.martin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.martin.backend.service.RepositoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BackendController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	String apiUrl;

	@Value("${APIKEY}")
	String apiKey;

	private final String URL_PARAM = "&units=imperial";

	/**
	 * GET REST call Requests zipcode entry from database Finalizes full apiUrl with
	 * parameters Makes restTemplate.exchanging call Returns the body of the results
	 * 
	 * @return
	 */
	@GetMapping("weatherbyzip")
	public String getWeatherByZip() {
		String zip = repositoryService.getZip();

		log.info("Zip returned from database :: {}", zip);

		final String uri = apiUrl + "/data/2.5/weather?zip=" + zip + "," + apiKey + URL_PARAM;

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				});

		String result = response.getBody();
		log.info("Weather API response - {}", result);
		return result;
	}

}
