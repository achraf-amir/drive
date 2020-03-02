package com.drive.mock.amq;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.messaging.BasicMessage.BasicMessageBuilder;
import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.PrepareUpdateOrder;
import com.drive.common.rest.OrderWebClient;
import com.drive.mock.sender.InPreparationRequestSender;
import com.drive.mock.sender.PreparedRequestSender;
import com.drive.mock.services.PrepareOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "${drive.queue.request.prepare-order}")
public class PrepareOrderListener {

	/** Logger. */
	private final Logger logger;
	private final InPreparationRequestSender inPreparationRequestSender;
	private final PrepareOrderService prepareOrderService;
	private final PreparedRequestSender preparedRequestSender;

	/** Le orderWebClient. */
	private final OrderWebClient orderWebClient;

	public PrepareOrderListener(
					OrderWebClient orderWebClient,
					InPreparationRequestSender inPreparationRequestSender,
					PrepareOrderService prepareOrderService,
					PreparedRequestSender preparedRequestSender
	) {
		this.orderWebClient = orderWebClient;
		this.prepareOrderService = prepareOrderService;
		this.inPreparationRequestSender = inPreparationRequestSender;
		this.preparedRequestSender = preparedRequestSender;
		logger = LoggerFactory.getLogger(PrepareOrderListener.class);
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		String orderId = message.getOrderId();
		BasicMessageBuilder basicMessageBuilder = message.toBuilder();
		Order order = null;
		try {
			 order = orderWebClient.getOrderById(orderId);
			logger.info("found order{}", order);
			 inPreparationRequestSender.send(message);
			 //just wait to view in preparation order
			 Thread.sleep(10000);
			 PrepareUpdateOrder prepareUpdateOrder = prepareOrderService.prepareOrder(order);

			basicMessageBuilder.orderToUpdate(prepareUpdateOrder);

		} catch(Exception e) {
			logger.error("order not found");
		}
		preparedRequestSender.send(basicMessageBuilder.build());

	}
}
