package com.drive.order.services.impl;


import com.drive.common.beans.Order;
import com.drive.common.beans.OrderCaptureUpdateMotiveStatus;
import com.drive.common.beans.OrderRequest;
import com.drive.common.beans.Status;
import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.order.exception.OrderValidationException;
import com.drive.order.services.OrderService;
import com.drive.order.services.OrdersCrudService;
import com.drive.order.services.sender.CaptureOrderRequestSender;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


import static com.drive.common.beans.OrderStatusCode.CAPTURED;
import static com.drive.common.beans.OrderTypeCode.PROMISE;
import static com.drive.common.beans.Status.fromStatusCode;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrdersCrudService ordersCrudService;
	private final DozerBeanMapper mapper;
	private final CaptureOrderRequestSender captureOrderRequestSender;

	public OrderServiceImpl(OrdersCrudService ordersCrudService,
	                       CaptureOrderRequestSender captureOrderRequestSender,
	                        DozerBeanMapper mapper) {
		this.ordersCrudService = ordersCrudService;
		this.captureOrderRequestSender = captureOrderRequestSender;
		this.mapper = mapper;

	}


	@Override
	public Order createPromiseOrder(OrderRequest orderRequest) throws OrderValidationException {
		final Order order = mapper.map(orderRequest, Order.class);
		order.setCreationDateTime(makeDate(orderRequest.getCreationDateTime()));
		order.setLastUpdateDateTime(makeDate(orderRequest.getLastUpdateDateTime()));


		order.setOrderTypeCode(PROMISE.toString());
		final Order createdOrder =ordersCrudService.create(order);
		BasicMessage basicMessage = BasicMessage.builder()
		                                        .orderId(createdOrder.getId())
		                                        .build();
		captureOrderRequestSender.send(basicMessage);

		return createdOrder;
	}

	@Override
	public void validateAndUpdateOrder(String orderId) throws Exception {
		final Order order = ordersCrudService.findById(orderId);
		validateOrder(order);
		final Status validationStatus = fromStatusCode(CAPTURED, OrderCaptureUpdateMotiveStatus.CAPTURE_OK);
		if (CollectionUtils.isEmpty(order.getAdditionalStatus())){
			order.setAdditionalStatus(new ArrayList<>());
		}
		order.getAdditionalStatus().add(validationStatus);
	}

	@Override
	public Order findById(String id) throws Exception {
		final Order order = ordersCrudService.findById(id);
		return order;
	}

	@Override
	public Order updateCategorization(String id, OrderCategoryType category){
		return ordersCrudService.update(id, category);

	}

	@Override
	public Order updateStatut(String id, Status status){
		return ordersCrudService.updateStatut(id, status);

	}

	private void validateOrder(Order order) throws OrderValidationException {
		final List<String> errors = new ArrayList<>();
		if (isBlank(order.getCreationDateTime().toString()) || !validateDateTime(order.getCreationDateTime().toString())) {
			errors.add("orderDate");
		}
		if(isBlank(order.getOrderNumber())){
			errors.add("orderNumber");
		}
		if (!errors.isEmpty()) {
			throw new OrderValidationException(errors.toString());
		}
	}
	private LocalDateTime makeDate(String dateTime) {
		try {
			return LocalDateTime.parse(dateTime);
		} catch(DateTimeParseException|NullPointerException e) {
			return null;
		}
	}
	private static boolean validateDateTime(String dateTime) {
		try {
			LocalDateTime.parse(dateTime);
			return true;
		} catch(DateTimeParseException e) {
			return false;
		}
	}
}
