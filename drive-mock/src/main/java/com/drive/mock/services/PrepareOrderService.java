package com.drive.mock.services;

import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.PrepareUpdateOrder;

public interface PrepareOrderService {

	PrepareUpdateOrder prepareOrder(Order order);
}
