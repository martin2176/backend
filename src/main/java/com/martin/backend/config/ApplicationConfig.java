package com.martin.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import datadog.trace.api.DDTags;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

@Configuration
public class ApplicationConfig {

	@Value("${api.url.https}")
	private String httpsApiUrl;

	@Value("${api.url.http}")
	private String httpApiUrl;

	private @Getter boolean isHttps;

	@Bean("restTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Checks if the USE_HTTPS parameter is an empty or null IF it is empty or null
	 * it sets the isHttps variable to false ELSE it sets the isHttps variable to
	 * the parsed boolean value of USE_HTTPS
	 * 
	 * @param useHttps
	 */
	public ApplicationConfig(@Value("${USE_HTTPS}") String useHttps) {
		isHttps = useHttps == null || useHttps.isEmpty() ? false : Boolean.parseBoolean(useHttps);
	}

	/**
	 * Creates a bean named apiUrl IF isHttps is true, it returns the value of
	 * httpsApiUrl ELSE it returns the httpApiUrl
	 * 
	 * @return
	 */
	@Bean("apiUrl")
	public String apiUrl() {
        Tracer tracer = GlobalTracer.get();

        // Service and resource name tags are required.
        // You can set them when creating the span:
        Span span = tracer.buildSpan("http.request")
            .withTag(DDTags.SERVICE_NAME, "openweathermap")
            .withTag(DDTags.RESOURCE_NAME, "/data/?/weather")
            .start();
        try (Scope scope = tracer.activateSpan(span)) {
            // Alternatively, set tags after creation
            span.setTag("my.tag", "value");
		return isHttps ? httpsApiUrl : httpApiUrl;
		}
        finally {
            // Close span in a finally block
            span.finish();
        }

	}
}
