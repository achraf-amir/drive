package com.drive.order.rabbitmq;

import com.drive.common.beans.statut.OrderCaptureUpdateMotiveStatus;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.statut.OrderStatusCode.CANCELLED;
import static com.drive.common.beans.statut.Status.fromStatusCode;

@Service
@RabbitListener(queues = "${drive.queue.request.error-order}")
public class CancelOrderRequestListener {

	private final OrderService orderService;
	private final Logger logger;

	public CancelOrderRequestListener(
					OrderService orderService
	) {
		this.orderService = orderService;
		this.logger = LoggerFactory.getLogger(CancelOrderRequestListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();
		 Status validationStatus = null;
		try {
			validationStatus = fromStatusCode(CANCELLED, OrderCaptureUpdateMotiveStatus.VALIDATION_KO);
		} catch(Exception e) {
			logger.error(
							"orderId {}: Unable to cancel order with message {}",
							message.getOrderId(),
							message);
		}
		orderService.updateStatut(orderId, validationStatus);

	}
}
