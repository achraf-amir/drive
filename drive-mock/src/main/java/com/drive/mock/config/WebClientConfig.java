package com.drive.mock.config;


import com.drive.common.rest.OrderWebClient;
import com.drive.common.rest.WebClientBuilderFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class WebClientConfig {

	@Bean
	public OrderWebClient orderWebClient(
					@Value("${drive.order.api.url}") String orderRepoUrl,
					final WebClientBuilderFactory webClientBuilderFactory
	) {
		return new OrderWebClient(orderRepoUrl, webClientBuilderFactory);
	}


}
