package com.drive.order.services.impl;


import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.common.beans.order.Order;
import com.drive.common.beans.order.OrderLineItem;
import com.drive.common.beans.order.OrderRequest;
import com.drive.common.beans.order.PrepareUpdateOrder;
import com.drive.common.beans.order.PrepareUpdateOrderLineItem;
import com.drive.common.beans.statut.OrderCaptureUpdateMotiveStatus;
import com.drive.common.beans.statut.Status;
import com.drive.common.beans.utils.AttributeValuePair;
import com.drive.common.beans.utils.Quantity;
import com.drive.common.beans.utils.QuantityTypeCode;
import com.drive.order.exception.OrderValidationException;
import com.drive.order.services.OrderService;
import com.drive.order.services.OrdersCrudService;
import com.drive.order.services.sender.CaptureOrderRequestSender;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.drive.common.beans.statut.OrderStatusCode.CAPTURED;
import static com.drive.common.beans.statut.OrderTypeCode.PROMISE;
import static com.drive.common.beans.statut.Status.fromStatusCode;
import static com.drive.order.utils.AttributeValueUtils.findValueFromTypeCode;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.leftPad;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrdersCrudService ordersCrudService;
	private final DozerBeanMapper mapper;
	private final CaptureOrderRequestSender captureOrderRequestSender;
	static final String ITEM_REFERENCE = "CDBASE";

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
		ordersCrudService.updateStatut(order.getId(), validationStatus);
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

	@Override
	public void saveUpdateOrder(final PrepareUpdateOrder updateOrder) throws Exception {
		final String orderId = updateOrder.getOrderId();
		final Order order = findById(orderId);
		emptyIfNull(updateOrder.getPrepareUpdateOrderLineItems())
						.forEach(
										(final PrepareUpdateOrderLineItem updateLine) -> {
											final List<OrderLineItem> linesToUpdate = getOrderLineItem(order, updateLine.getCdbase());

											final Quantity preparedQuantity =  Quantity
															.builder()
															.quantity(updateLine.getPreparedQuantity())
															.quantityTypeCode(QuantityTypeCode.PICKED_ROUNDED)
															.build();

											linesToUpdate.forEach(initQuantityIfNotPresent(preparedQuantity));

												final OrderLineItem orderLineItem = linesToUpdate.get(0);
												orderLineItem.getAdditionalQuantity()
												             .stream()
												             .filter(quantity -> preparedQuantity.getQuantityTypeCode() == quantity.getQuantityTypeCode())
												             .forEach(quantity -> quantity.setQuantity(preparedQuantity.getQuantity()));
										}
						);

		order.setLastUpdateDateTime(LocalDateTime.now());
		ordersCrudService.update(order);
	}

	public static List<OrderLineItem> getOrderLineItem(
					final Order promiseOrder, final String updateLineCdBase
	) {
		return promiseOrder
						.getOrderLineItem()
						.stream()
						.filter(
										(OrderLineItem orderLine) -> {
											final List<AttributeValuePair> additionalTradeItemId =
															ofNullable(orderLine.getAdditionalTradeItemIdentification())
																				.orElse(emptyList());
											final String orderLineCdBase = findValueFromTypeCode(additionalTradeItemId, ITEM_REFERENCE);
											final String paddedLineCdBase = leftPad(orderLineCdBase, updateLineCdBase.length(), '0');
											return StringUtils.equals(updateLineCdBase, paddedLineCdBase);
										}
						)
						.collect(Collectors.toList());
	}
	private static Consumer<OrderLineItem> initQuantityIfNotPresent(final Quantity quantity) {
		return (OrderLineItem orderLineItem) -> {
			final Optional<Quantity> quantityOp = getQtyByType(orderLineItem, quantity.getQuantityTypeCode());
			if (!quantityOp.isPresent()) {
				orderLineItem.getAdditionalQuantity().add(
								Quantity.builder()
								        .quantity(0D)
								        .quantityTypeCode(quantity.getQuantityTypeCode())
								        .measurementUnitCode(quantity.getMeasurementUnitCode())
								        .build()
				);
			}
		};
	}

	static Optional<Quantity> getQtyByType(
					final OrderLineItem orderLineItem, final QuantityTypeCode quantityType
	) {
		return emptyIfNull(orderLineItem.getAdditionalQuantity())
						.stream()
						.filter(quantity -> quantityType == quantity.getQuantityTypeCode())
						.findFirst();
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
