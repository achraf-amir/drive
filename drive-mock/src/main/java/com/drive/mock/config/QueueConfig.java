package com.drive.mock.config;

import com.drive.mock.sender.InPreparationRequestSender;
import com.drive.mock.sender.PrepareRequestSender;
import com.drive.mock.sender.PreparedRequestSender;
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
	public PrepareRequestSender prepareRequestSender(
					@Value("${drive.queue.request.prepare-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new PrepareRequestSender(rabbitTemplate, queue);
	}

	@Bean
	public InPreparationRequestSender inPreparationRequestSender(
					@Value("${drive.queue.request.inpreparation-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new InPreparationRequestSender(rabbitTemplate, queue);
	}

	@Bean
	public PreparedRequestSender preparedRequestSender(
					@Value("${drive.queue.request.prepared-order}") String queueName,
					RabbitTemplate rabbitTemplate
	) {
		final Queue queue = new Queue(queueName);
		return new PreparedRequestSender(rabbitTemplate, queue);
	}





}
