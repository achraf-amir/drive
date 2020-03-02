package com.drive.common.beans.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

/**
 * Money
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Money implements Serializable {

	public static final int DEFAULT_SCALE = 2;

	public static final RoundingMode DEFAULT_ROUNDING = HALF_EVEN;

	public static final String EURO_CURRENCY = "EUR";

	private static final long serialVersionUID = 8340049569622215526L;

	private String currency;

	private Integer scale;

	private Integer unscaledAmount;

	private String currencyAlphaCode;

	public BigDecimal value() {
		return BigDecimal.valueOf(unscaledAmount, scale);
	}

	public static Money from(float value) {
		BigDecimal bigDecimalValue = new BigDecimal(Float.toString(value)).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
		return from(bigDecimalValue);
	}

	public static Money from(BigDecimal value) {
		value = value.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
		return from(value, EURO_CURRENCY);
	}

	public static Money from(float value, String currency) {
		BigDecimal bigDecimalValue = new BigDecimal(Float.toString(value)).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
		return from(bigDecimalValue, currency);
	}

	public static Money from(BigDecimal value, String currency) {
		return from(value, currency, currency);
	}

	private static Money from(BigDecimal bigDecimal, String currency, String currencyAlphaCode) {
		return Money
						.builder()
						.unscaledAmount(bigDecimal.unscaledValue().intValue())
						.scale(bigDecimal.scale())
						.currency(currency)
						.currencyAlphaCode(currencyAlphaCode)
						.build();
	}

}
