package com.drive.common.rest;


import com.drive.common.beans.order.Order;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class OrderWebClient {

	private static final Logger logger = LoggerFactory.getLogger(OrderWebClient.class);

	private WebClient webClient;


	public OrderWebClient(
					final String baseUrl,
					final WebClientBuilderFactory webClientFactory
	) {
		final ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
		this.webClient = webClientFactory
						.newBuilder(logger, "Order client")
						.clientConnector(httpConnector)
						.defaultHeader(ACCEPT, APPLICATION_JSON_UTF8_VALUE)
						.baseUrl(baseUrl)
						.build();
	}


	public Order getOrderById(@NonNull final String id) throws Exception {
		try {
			return webClient
							.get().uri("/orders/{id}", id).retrieve()
							.onStatus(
											NOT_FOUND::equals,
											clientResponse -> Mono.error(new Exception(id))
							)
							.bodyToMono(Order.class).block();
		} catch(Exception ex) {
			throw ex;
		}
	}

}
