package com.drive.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// To not load configuration if spring RabbitMQ not load
@ConditionalOnClass(AsyncRabbitTemplate.class)
public class RabbitMQConfiguration {
	/**
	 * Message converter to use for receiver and sender message
	 *
	 * @return Json converter
	 */
	@Bean
	public MessageConverter messageConverter(ObjectMapper mapper) {
		return new Jackson2JsonMessageConverter(mapper);
	}
}
