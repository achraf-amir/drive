package com.drive.order.services;


import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.OrderRequest;
import com.drive.common.beans.order.PrepareUpdateOrder;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.order.exception.OrderValidationException;

public interface OrderService {

	Order createPromiseOrder(OrderRequest order) throws OrderValidationException;

	Order findById(String id) throws Exception;

	Order updateCategorization(String id, OrderCategoryType category);
	Order updateStatut(String id, Status status);
  void validateAndUpdateOrder(String orderId) throws Exception;
	void saveUpdateOrder(final PrepareUpdateOrder updateOrder) throws Exception;
}
