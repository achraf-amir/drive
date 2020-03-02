package com.drive.order.services;


import com.drive.common.beans.order.Order;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.keys.OrderCategoryType;

public interface OrdersCrudService {

	Order create(Order order);
	Order findById(String id) throws Exception;
	Order update(String id, OrderCategoryType categoryType);
	Order updateStatut(String id, Status status);

	void update(Order order);
}
