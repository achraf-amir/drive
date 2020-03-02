package com.drive.common.beans.utils;

public enum MeasurementUnitCode {
	PIECE("PIECE"),
	L("Liter"),
	CM("Centimeter"),
	MM("Millimeter"),
	KG("Kilogram"),
	G("Gram");

	private String name;

	MeasurementUnitCode(String name) {
		this.name = name;
	}
}
