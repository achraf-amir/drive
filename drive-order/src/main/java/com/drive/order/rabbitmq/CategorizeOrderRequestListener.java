package com.drive.order.rabbitmq;

import com.drive.common.beans.order.Order;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.keys.WorkFlowVarsNameKeys;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.BasicMessage.BasicMessageBuilder;
import com.drive.order.services.OrderService;
import com.drive.order.services.sender.CategorizeOrderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.statut.OrderStatusCode.CATEGORIZED;
import static com.drive.common.beans.statut.Status.fromStatusCode;

@Service
@RabbitListener(queues = "${drive.queue.request.categorize-order}")
public class CategorizeOrderRequestListener {

	private final OrderService orderService;
	private final Logger logger;
	private final CategorizeOrderSender categorizeOrderSender;

	public CategorizeOrderRequestListener(
					OrderService orderService,
					CategorizeOrderSender categorizeOrderSender
	) {
		this.orderService = orderService;
		this.categorizeOrderSender = categorizeOrderSender;
		this.logger = LoggerFactory.getLogger(CategorizeOrderRequestListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();
		BasicMessageBuilder messageBuilder = message.toBuilder();
		Status validationStatus;
		try {
			final Order order =  orderService.findById(orderId);
			orderService.updateStatut(orderId, fromStatusCode(CATEGORIZED));
			messageBuilder.wfVar(WorkFlowVarsNameKeys.CATEGORIZATION.toString(), order.getOrderCategory());
		} catch(Exception e) {
			messageBuilder.wfVar(WorkFlowVarsNameKeys.CATEGORIZATION.toString(), null);


			logger.error(
							"orderId {}: Unable to validate order with message {}",
							message.getOrderId(),
							message);
		}

    categorizeOrderSender.send(messageBuilder.build());
	}
}
