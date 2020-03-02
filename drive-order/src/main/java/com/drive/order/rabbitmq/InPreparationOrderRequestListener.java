package com.drive.order.rabbitmq;

import com.drive.common.beans.keys.WorkFlowVarsNameKeys;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.BasicMessage.BasicMessageBuilder;
import com.drive.common.beans.order.Order;
import com.drive.common.beans.statut.Status;
import com.drive.order.services.OrderService;
import com.drive.order.services.sender.CategorizeOrderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.statut.OrderStatusCode.CATEGORIZED;
import static com.drive.common.beans.statut.OrderStatusCode.IN_PREPARATION;
import static com.drive.common.beans.statut.Status.fromStatusCode;

@Service
@RabbitListener(queues = "${drive.queue.request.inpreparation-order}")
public class InPreparationOrderRequestListener {

	private final OrderService orderService;
	private final Logger logger;

	public InPreparationOrderRequestListener(
					OrderService orderService
	) {
		this.orderService = orderService;
		this.logger = LoggerFactory.getLogger(InPreparationOrderRequestListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();
		try {
			orderService.updateStatut(orderId, fromStatusCode(IN_PREPARATION));
		} catch(Exception e) {
      logger.error("unable to find order with id {}", orderId);
		}
	}
}
