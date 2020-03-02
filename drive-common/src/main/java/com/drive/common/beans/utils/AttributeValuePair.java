package com.drive.common.beans.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeValuePair implements Serializable {

	private static final long serialVersionUID = 973477908950036877L;

	private String typeCode;

	private String value;

}
