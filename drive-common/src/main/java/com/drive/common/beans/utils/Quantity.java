package com.drive.common.beans.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Quantity
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quantity implements Serializable {

	private static final long serialVersionUID = 6353756145663755839L;

	private Double quantity;

	private MeasurementUnitCode measurementUnitCode;

	private QuantityTypeCode quantityTypeCode;

	public static Quantity from(Float quantity) {
		return from(Double.valueOf(quantity.toString()));
	}

	public static Quantity from(Double quantity) {
		if (quantity == null) {
			return null;
		}
		return from(null, quantity, (MeasurementUnitCode)null);
	}

	public static Quantity from(QuantityTypeCode type, Float quantity, String unit) {
		return from(type, Double.valueOf(quantity.toString()), MeasurementUnitCode.valueOf(unit));
	}

	public static Quantity from(QuantityTypeCode type, Double quantity, String unit) {
		return from(type, quantity, MeasurementUnitCode.valueOf(unit));
	}

	public static Quantity from(QuantityTypeCode type, Double quantity, MeasurementUnitCode unit) {
		return new Quantity(quantity, unit, type);
	}
}
