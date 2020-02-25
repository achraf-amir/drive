package com.drive.orchestrator.services.impl;


import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.OrderValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("orderValidationService")
public class OrderValidationServiceImpl implements OrderValidationService {

	private String queueValidationOrder;

	public OrderValidationServiceImpl(
					@Value("${drive.queue.request.validate-order}")String queueValidationOrder) {
		this.queueValidationOrder = queueValidationOrder;
	}

	public BasicMessage validateOrder(String orderId){
		return BasicMessage
						.builder()
						.orderId(orderId)
						.build();

	}

	@Override
	public String getQueueValidationOrder() {return queueValidationOrder;}


}
