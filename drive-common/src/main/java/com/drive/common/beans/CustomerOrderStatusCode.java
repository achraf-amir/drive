package com.drive.common.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@RequiredArgsConstructor
public enum CustomerOrderStatusCode implements StatusCode {
	PRIS_EN_COMPTE("Prise en compte", OrderStatusCode.CAPTURED, OrderStatusCode.CAPTURED),
	VALIDATED("Validée", OrderStatusCode.VALIDATED, null),
	MODIFIED("Modifiée", OrderStatusCode.CHANGED, null),
	IN_PREPARATION("En cours de préparation", OrderStatusCode.ROUTED, OrderStatusCode.IN_PREPARATION),
	PREPARED("Préparée", OrderStatusCode.PREPARED, null),
	DISPATCHED("Expédiée", OrderStatusCode.LOADED, null),
	CANCELED("Annulée", OrderStatusCode.CANCELLED, OrderStatusCode.CANCELLED),
	DELIVERED("Livrée", OrderStatusCode.DELIVERED, OrderStatusCode.EXPEDIABLE);

	@Getter
	private final String reason;

	@Getter
	private final OrderStatusCode orderStatusCode;

	@Getter
	private final OrderStatusCode orderStatusCodeForHermod;

	@Override
	public String getCode() {
		return name();
	}

	public static CustomerOrderStatusCode fromOrderStatusCode(String orderStatusCode, boolean isHermod) {
		// gestion hermod status
		if (isHermod) {
			for(CustomerOrderStatusCode status : values()) {
				if (status.orderStatusCodeForHermod != null &&
								StringUtils.equals(status.orderStatusCodeForHermod.getCode(), orderStatusCode)) {
					return status;
				}
			}
		} else {
			for(CustomerOrderStatusCode status : values()) {
				if (StringUtils.equals(status.orderStatusCode.getCode(), orderStatusCode)) {
					return status;
				}
			}
		}
		return null;
	}

	public static CustomerOrderStatusCode fromOrderStatusCode(OrderStatusCode orderStatusCode, boolean isHermod) {
		return fromOrderStatusCode(orderStatusCode.getCode(), isHermod);
	}
}
