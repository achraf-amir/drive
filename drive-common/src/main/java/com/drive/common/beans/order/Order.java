package com.drive.common.beans.order;


import com.drive.common.beans.keys.OrderCategoryType;
import com.drive.common.beans.statut.Status;
import com.drive.common.deserializer.DriveLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {

	private static final long serialVersionUID = -6843996345823173304L;
	private String Id;
	@JsonDeserialize(using = DriveLocalDateTimeDeserializer.class)
	private LocalDateTime creationDateTime;
	@JsonDeserialize(using = DriveLocalDateTimeDeserializer.class)
	private LocalDateTime lastUpdateDateTime;
	private String orderNumber;
	private List<Status> additionalStatus;
	private String orderTypeCode;
	private String orderCategory;
	private OrderCategoryType orderCategoryType;
	private Status orderStatut;
	private List<OrderLineItem> orderLineItem;

}
