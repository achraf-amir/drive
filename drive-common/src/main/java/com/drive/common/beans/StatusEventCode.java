package com.drive.common.beans;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class StatusEventCode implements Serializable {

	private static final long serialVersionUID = 2108730206877691180L;

	@Getter
	private OrderTypeCode orderTypeCode;

	@Getter
	private String message;

	private StatusEventCode(final OrderTypeCode orderTypeCode, final String message) {
		this.orderTypeCode = orderTypeCode;
		this.message = message;
	}

	public static StatusEventCode none() {
		return new StatusEventCode(null, null);
	}

	public static StatusEventCode promise(final String message) {
		return new StatusEventCode(OrderTypeCode.PROMISE, message);
	}

	public static StatusEventCode delivery(final String message) {
		return new StatusEventCode(OrderTypeCode.DELIVERY, message);
	}

	public static StatusEventCode fromStatusCode(final String statusCode) {
		try {
			final OrderStatusCode orderStatusCode = OrderStatusCode.valueOf(statusCode);
			return orderStatusCode.getEventCode();
		} catch(IllegalArgumentException ignore) {
			return null;
		}
	}

	public static boolean matchOrderTypeCode(final StatusEventCode eventCode, final String orderTypeCode) {
		return eventCode != null && eventCode.getOrderTypeCode() != null
						&& StringUtils.equals(orderTypeCode, eventCode.getOrderTypeCode().getCode());
	}
}
