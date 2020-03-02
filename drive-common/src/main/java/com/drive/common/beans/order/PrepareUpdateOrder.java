package com.drive.common.beans.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "order")
@XmlType(name = "", propOrder = {
				"prepareUpdateOrderLineItems",
				"totalMonetaryAmountIncludingTaxes"

})
public class PrepareUpdateOrder extends UpdateOrder{

	private static final long serialVersionUID = 1L;

	private double totalMonetaryAmountIncludingTaxes;
	private List<PrepareUpdateOrderLineItem> prepareUpdateOrderLineItems;

	@XmlElement(name = "item")
	@XmlElementWrapper(name = "items")
	public void setPrepareUpdateOrderLineItems(List<PrepareUpdateOrderLineItem> prepareUpdateOrderLineItems) {
		this.prepareUpdateOrderLineItems = prepareUpdateOrderLineItems;
	}

	@XmlElement( name = "totalOrderAmountPreparated")
	public void setTotalMonetaryAmountIncludingTaxes(double totalMonetaryAmountIncludingTaxes) {
		this.totalMonetaryAmountIncludingTaxes = totalMonetaryAmountIncludingTaxes;
	}
}
