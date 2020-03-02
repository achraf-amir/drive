package com.drive.common.beans.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
				@JsonSubTypes.Type(value = PrepareUpdateOrder.class, name = "PrepareUpdateOrder")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrder implements Serializable {

	private static final long serialVersionUID = -973477908950036L;

	@JsonProperty("orderId")
	protected String orderId;

	@JsonProperty("updateDate")
	protected Date updateDate;

}
