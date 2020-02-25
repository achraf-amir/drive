package com.drive.orchestrator.services;

import com.drive.common.beans.messaging.BasicMessage;

public interface OrderValidationService {

	BasicMessage validateOrder(String orderId);
	String getQueueValidationOrder();
}
