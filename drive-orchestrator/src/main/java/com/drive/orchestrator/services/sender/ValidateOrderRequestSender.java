package com.drive.orchestrator.services.sender;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ValidateOrderRequestSender extends Sender<BasicMessage> {

	public ValidateOrderRequestSender(
					RabbitTemplate template,
					Queue queue
	) {
		super(template, queue);
	}
}
