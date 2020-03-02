package com.drive.order.config;

import com.drive.order.services.sender.CaptureOrderRequestSender;
import com.drive.order.services.sender.CategorizeOrderSender;
import com.drive.order.services.sender.ValidateOrderSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class QueueConfig {

	@Bean
	public CaptureOrderRequestSender captureOrderRequestSender(
					@Value("${drive.queue.request.capture-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new CaptureOrderRequestSender(rabbitTemplate, queue);
	}

	@Bean
	public ValidateOrderSender validateOrderSender(
					@Value("${drive.queue.response.validate-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new ValidateOrderSender(rabbitTemplate, queue);
	}

	@Bean
	public CategorizeOrderSender categorizeOrderSender(
					@Value("${drive.queue.response.categorize-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new CategorizeOrderSender(rabbitTemplate, queue);
	}

}
