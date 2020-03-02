package com.drive.orchestrator.config;

import com.drive.orchestrator.services.sender.CategorizeOrderRequestSender;
import com.drive.orchestrator.services.sender.ErrorOrderRequestSender;
import com.drive.orchestrator.services.sender.PrepareOrderRequestSender;
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

	@Bean
	public CategorizeOrderRequestSender categorizeOrderRequestSender(
					@Value("${drive.queue.request.categorize-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new CategorizeOrderRequestSender(rabbitTemplate, queue);
	}

	@Bean
	public ErrorOrderRequestSender errorOrderRequestSender(
					@Value("${drive.queue.request.error-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new ErrorOrderRequestSender(rabbitTemplate, queue);
	}

	@Bean
	public PrepareOrderRequestSender prepareOrderRequestSender(
					@Value("${drive.queue.request.prepare-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new PrepareOrderRequestSender(rabbitTemplate, queue);
	}




}
