package com.drive.order.services;


import com.drive.common.beans.Order;
import com.drive.common.beans.Status;
import com.drive.common.beans.keys.OrderCategoryType;

public interface OrdersCrudService {

	Order create(Order order);
	Order findById(String id) throws Exception;
	Order update(String id, OrderCategoryType categoryType);
	Order updateStatut(String id, Status status);

}
