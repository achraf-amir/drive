package com.drive.common.beans.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BasicMessage implements Serializable {

	private static final long serialVersionUID = -3811476619566278181L;
	private String orderId;
	@Singular("wfVar")
	private Map<String, Object> wfVars;
	private String correlationId;

}
