package com.drive.orchestrator.config;

import com.drive.orchestrator.services.sender.ValidateOrderRequestSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	@Bean
	public ValidateOrderRequestSender validateOrderRequestSender(
					@Value("${drive.queue.request.validate-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new ValidateOrderRequestSender(rabbitTemplate, queue);
	}
}
