package com.drive.common.beans.order;

import com.drive.common.beans.statut.Status;
import com.drive.common.beans.utils.AttributeValuePair;
import com.drive.common.beans.utils.Money;
import com.drive.common.beans.utils.Quantity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderLineItem
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderLineItem implements Serializable {

	private static final long serialVersionUID = -1337803410185896778L;

	private String lineItemNumber;
	private Quantity requestedQuantity;
	private List<Quantity> additionalQuantity = new ArrayList<>();
	private Money monetaryAmountIncludingTaxes;
	private String tradeItemDescription;
	private Status statusLine;
	private List<AttributeValuePair> additionalTradeItemIdentification;


}
