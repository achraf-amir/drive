package com.drive.order.inteceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {

		HttpHeaders headers = request.getHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(LogCorrelationManager.CORRELATION_ID_KEY, LogCorrelationManager.lastCorrelationId());
		return execution.execute(request, body);
	}

}
