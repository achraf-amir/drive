package com.drive.order.services;


import com.drive.common.beans.Order;
import com.drive.common.beans.OrderRequest;
import com.drive.common.beans.Status;
import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.order.exception.OrderValidationException;

public interface OrderService {

	Order createPromiseOrder(OrderRequest order) throws OrderValidationException;

	Order findById(String id) throws Exception;

	Order updateCategorization(String id, OrderCategoryType category);
	Order updateStatut(String id, Status status);
	 void validateAndUpdateOrder(String orderId) throws Exception;
}
