package com.drive.common.beans.statut;

public enum OrderTypeCode {
	/**
	 * Represent a Commercial Order, which is a set of Promise Orders
	 */
	COMMERCIAL(221),
	/**
	 * Represent a Promise Order: a promise on the shipping of one or more items.
	 */
	PROMISE(220),
	/**
	 * Represent a Logistical Order or Fulfilment Order.
	 */
	LOGISTICAL(226),
	/**
	 * Represent a delivery order
	 */
	DELIVERY(402),
	/**
	 * Represent a REPAIR Order, used to compensate and correct other orders.
	 */
	REPAIR(225);

	private int code;

	OrderTypeCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.valueOf(this.code);
	}

	public boolean codeEquals(String value) {
		return this.toString().equals(value);
	}

	public String getCode() {
		return code + "";
	}
}
