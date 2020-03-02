package com.drive.common.rest;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.BiConsumer;

import static java.util.stream.Collectors.joining;


@Component
public class WebClientBuilderFactory {

	/**
	 * Create WebClient builder with base url and filter to log with given logger.
	 *
	 * @param logger logger to use when filter web client.
	 *
	 * @return WebClient builder with filter to log and base url.
	 *
	 */
	public WebClient.Builder newBuilder(
					final Logger logger,
					final String service
	) {
		return WebClient
						.builder()
						.filter(logRequest(logger, service))
						.filter(logResponse(logger, service));
	}

	/**
	 * Return filter to log response from web client.
	 *
	 * @param logger logger into log.
	 *
	 * @return filter
	 *
	 * @since 0.9.0
	 */
	private ExchangeFilterFunction logResponse(
					final Logger logger,
					final String service
	) {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			HttpStatus httpStatus = clientResponse.statusCode();
			final BiConsumer<String, Object[]> loggerMessage;
			if (httpStatus.isError()) {
				loggerMessage = logger::error;
			} else {
				loggerMessage = logger::info;
			}
			loggerMessage.accept("response from service {} with code {}", new Object[] {service, httpStatus});

			if (logger.isDebugEnabled()) {
				String headers = mapHeader(clientResponse.headers().asHttpHeaders());
				logger.debug(
								"response from service {} with code {}, headers [{}] and cookie [{}] ({})",
								service,
								httpStatus,
								headers,
								mapCookies(clientResponse.cookies()),
								clientResponse
				);
			}
			return Mono.just(clientResponse);
		});
	}

	/**
	 * Return filter to log request from web client.
	 *
	 * @param logger logger into log.
	 *
	 * @return filter
	 *
	 */
	private ExchangeFilterFunction logRequest(final Logger logger, final String service) {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			logger.info("request from service {} [{} {}]", service, clientRequest.method(), clientRequest.url());
			if (logger.isDebugEnabled()) {
				final String params = mapHeader(clientRequest.headers());
				logger.debug(
								"request from service {} [{} {}] with headers [{}] ({})",
								service,
								clientRequest.method(),
								clientRequest.url(),
								params,
								clientRequest
				);
			}
			return Mono.just(clientRequest);
		});
	}

	/**
	 * Return string represent header.
	 *
	 * @param map headers map.
	 *
	 * @return string.
	 *
	 */
	private String mapHeader(final MultiValueMap<String, String> map) {
		return map
						.entrySet()
						.stream()
						.map(entry -> entry.getKey() + " = [" + String.join(",", entry.getValue()) + "]")
						.collect(joining(";"));
	}

	/**
	 * Return string represent cookies.
	 *
	 * @param map cookies map.
	 *
	 * @return string.
	 *
	 * @since 0.9.0
	 */
	private String mapCookies(final MultiValueMap<String, ResponseCookie> map) {
		return map
						.entrySet()
						.stream()
						.map(
										entry ->
														entry.getKey() +
																		" = " +
																		entry
																						.getValue()
																						.stream()
																						.map(ResponseCookie::toString)
																						.collect(joining(",")) +
																		"]"
						)
						.collect(joining(";"));
	}
}
