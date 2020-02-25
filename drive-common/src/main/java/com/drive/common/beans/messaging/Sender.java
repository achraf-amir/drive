package com.drive.common.beans.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

public class Sender<T> {

	private final static Logger logger = LoggerFactory.getLogger(Sender.class);

	private final RabbitTemplate template;
	private final Queue queue;
	private final String exchange;

	/**
	 * @param template To send message
	 * @param queue    Into send message
	 *
	 * @since 1.0.0
	 */
	public Sender(RabbitTemplate template, Queue queue) {
		super();
		this.template = template;
		this.queue = queue;
		this.exchange = null;
	}

	/**
	 * @param template To send message
	 * @param queue    Into send message
	 * @param exchange exchange name
	 */
	public Sender(RabbitTemplate template, Queue queue, String exchange) {
		this.template = template;
		this.queue = queue;
		this.exchange = exchange;
	}

	/**
	 * Send message
	 *
	 * @param toSend message to send
	 *
	 * @since 1.0.0
	 */
	public void send(T toSend) {
		final String queueName = queue.getName();
		logger.info("Send message [{}] into queue [{}]", toSend, queueName);
		try {
			send(toSend, queueName);
		} catch(AmqpException ex) {
			logger.error("Message [{}] into queue [{}] hasn't send", ex, toSend, queueName);
			throw ex;
		}
	}


	public void sendWithHeaders(T toSend, Map<String, Object> headers) {
		final String queueName = queue.getName();
		logger.info("Send message [{}] into queue [{}] with headers [{}]", toSend, queueName, headers);
		try {
			send(toSend, queueName, headers);
		} catch(AmqpException ex) {
			logger.error("Message [{}] into queue [{}] with headers [{}] hasn't send", ex, toSend, queueName, headers);
			throw ex;
		}
	}

	private void send(T toSend, String queueName, Map<String, Object> headers) throws AmqpException {
		if (isEmpty(exchange)) {
			template.convertAndSend(queueName, toSend, message -> {
				                        message.getMessageProperties().getHeaders().putAll(headers);
				                        return message;
			                        }
			);
			logger.info("Message [{}] into queue [{}] has been send with headers [{}]", toSend, queueName, headers);
		} else {
			template.convertAndSend(exchange, queueName, toSend, message -> {
				                        message.getMessageProperties().getHeaders().putAll(headers);
				                        return message;
			                        }
			);
			logger.info("Message [{}] into queue [{}] of exchange [{}] and headers [{}] has been send", toSend, queueName,
			            exchange, headers);
		}
	}

	private void send(T toSend, String queueName) throws AmqpException {
		if (isEmpty(exchange)) {
			template.convertAndSend(queueName, toSend);
			logger.info("Message [{}] into queue [{}] has been send", toSend, queueName);
		} else {
			template.convertAndSend(exchange, queueName, toSend);
			logger.info("Message [{}] into queue [{}] of exchange [{}] has been send", toSend, queueName, exchange);
		}
	}
}
