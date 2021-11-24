package com.martin.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@Slf4j
public class BackendApplication {

	@Autowired
  	JdbcTemplate jdbcTemplate;

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("weatherbyzip")
	public String getWeatherByZip() {

		final String uri = "https://api.openweathermap.org/data/2.5/weather?zip=" 
							+ getZip() 
							+ ",US&appid=54d8983398d8e060a3067122b9300179";

		ResponseEntity<String> response = restTemplate.exchange(
				uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<String>(){});

		String result = response.getBody();
		log.info("Weather API response - {}", result);
		return result;
	}

	public String getZip() {
		String zip = jdbcTemplate.queryForObject("select zip from weather.locations", String.class);
		log.info("Zip returned from database :: {}", zip );
		return zip;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
