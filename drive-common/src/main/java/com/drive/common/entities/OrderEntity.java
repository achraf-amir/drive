package com.drive.common.entities;


import com.drive.common.beans.Status;
import com.drive.common.beans.keys.OrderCategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

	@Id
	private String id;
	@CreatedDate
	private LocalDateTime creationDateTime;

	@LastModifiedDate
	private LocalDateTime lastUpdateDateTime;
	private String orderNumber;
	private List<Status> additionalStatus;
	private String orderTypeCode;
	private String orderCategory;
	private OrderCategoryType orderCategoryType;


}
