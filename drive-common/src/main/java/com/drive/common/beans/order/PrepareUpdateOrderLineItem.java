package com.drive.common.beans.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "item")
@XmlType(name = "", propOrder = {
				"cdbase",
				"preparedQuantity",
				"totalAmountPreparated"
})
public class PrepareUpdateOrderLineItem {
	private static final long serialVersionUID = 1L;

	private String cdbase;

	private double preparedQuantity;

	private double totalAmountPreparated;

	@XmlElement(name = "externalReference")
	public void setCdbase(String cdbase) {
		this.cdbase = cdbase;
	}
	@XmlElement(name = "quantityPreparated")
	public void setPreparedQuantity(double preparedQuantity) {
		this.preparedQuantity = preparedQuantity;
	}

	@XmlElement(name = "totalAmountPreparated")
	public void setTotalAmountPreparated(double totalAmountPreparated) {
		this.totalAmountPreparated = totalAmountPreparated;
	}
}
