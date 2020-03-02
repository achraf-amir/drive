package com.drive.order.rabbitmq;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.order.PrepareUpdateOrder;
import com.drive.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.statut.OrderStatusCode.IN_PREPARATION;
import static com.drive.common.beans.statut.OrderStatusCode.PREPARED;
import static com.drive.common.beans.statut.Status.fromStatusCode;

@Service
@RabbitListener(queues = "${drive.queue.request.prepared-order}")
public class PreparedOrderRequestListener {

	private final OrderService orderService;
	private final Logger logger;

	public PreparedOrderRequestListener(
					OrderService orderService
	) {
		this.orderService = orderService;
		this.logger = LoggerFactory.getLogger(PreparedOrderRequestListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();

		try {
			orderService.saveUpdateOrder((PrepareUpdateOrder)message.getOrderToUpdate());
			orderService.updateStatut(message.getOrderId(), fromStatusCode(PREPARED));
		} catch(Exception e) {
      logger.error("unable to find order with id {}", orderId);
		}
	}
}
