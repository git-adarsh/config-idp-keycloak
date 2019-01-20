package com.auth.ms.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

	private RestTemplate restTemplate = new RestTemplate();

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

}
