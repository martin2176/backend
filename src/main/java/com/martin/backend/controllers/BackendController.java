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
import datadog.trace.api.DDTags;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

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
	
	@Value("${api.url.param}")
	String URL_PARAM;
	
	@Value("${api.url.context}")
	String URL_CONTEXT;
	
	@Value("${api.url.apikey.prefix}")
	String URL_API_KEY_PREFIX;

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

		final String uri = apiUrl + URL_CONTEXT + zip + "," + URL_API_KEY_PREFIX + apiKey + URL_PARAM;
		
		log.info(uri);
        Tracer tracer = GlobalTracer.get();
        Span span = tracer.buildSpan("call.saas.weatherapi")
            .withTag(DDTags.SERVICE_NAME, "openweathermap")
            .withTag(DDTags.RESOURCE_NAME, "web.request")
            .start();
		try (Scope scope = tracer.activateSpan(span)) {
            // Alternatively, set tags after creation
            span.setTag("TYPE", "Web");
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				});
		String result = response.getBody();
		log.info("Weather API response - {}", result);
		return result;
		}
		finally {
            span.finish();
        }

	}

}
