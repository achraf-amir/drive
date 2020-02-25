package com.drive.order.services.sender;


import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class CaptureOrderRequestSender extends Sender<BasicMessage> {
	/**
	 * @param template To send message
	 * @param queue    Into send message
	 *
	 * @since 1.0.0
	 */
	public CaptureOrderRequestSender(
					RabbitTemplate template,
					Queue queue
	) {
		super(template, queue);
	}
}
