package com.drive.order.rabbitmq;

import com.drive.common.beans.keys.WorkFlowVarsNameKeys;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.BasicMessage.BasicMessageBuilder;
import com.drive.order.services.OrderService;
import com.drive.order.services.sender.ValidateOrderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "${drive.queue.request.validate-order}")
public class ValidateOrderRequestListener {

	private final OrderService orderService;
	private final Logger logger;
	private final ValidateOrderSender validateOrderSender;

	public ValidateOrderRequestListener(
					OrderService orderService,
					ValidateOrderSender validateOrderSender
	) {
		this.orderService = orderService;
		this.validateOrderSender = validateOrderSender;
		this.logger = LoggerFactory.getLogger(ValidateOrderRequestListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();
		BasicMessageBuilder messageBuilder = message.toBuilder();
		try {
			orderService.validateAndUpdateOrder(orderId);
			messageBuilder.wfVar(WorkFlowVarsNameKeys.IS_ORDER_VALID.toString(), true);
		} catch(Exception e) {
			messageBuilder.wfVar(WorkFlowVarsNameKeys.IS_ORDER_VALID.toString(), false);
			logger.error(
							"orderId {}: Unable to validate order with message {}",
							message.getOrderId(),
							message);
		}

    validateOrderSender.send(messageBuilder.build());
	}
}
