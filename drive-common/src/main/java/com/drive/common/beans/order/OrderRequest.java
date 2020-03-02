package com.drive.common.beans.order;

import com.drive.common.beans.utils.AttributeValuePair;
import com.drive.common.beans.statut.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest implements Serializable {

	private static final long serialVersionUID = -1;
	private String creationDateTime;
	private String lastUpdateDateTime;
	private String orderNumber;
	private List<Status> additionalStatus;
	private String orderTypeCode;
	private String orderCategory;
	private List<OrderLineItem> orderLineItem;

}
