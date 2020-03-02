package com.drive.common.beans.keys;

public enum WorkFlowVarsNameKeys {

	ORDER_ID("orderId"),
	IS_ORDER_VALID("isOrderValid"),
	CATEGORIZATION("orderCategory");


	private String name;

	WorkFlowVarsNameKeys(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
