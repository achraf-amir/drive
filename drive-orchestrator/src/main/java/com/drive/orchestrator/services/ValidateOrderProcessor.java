package com.drive.orchestrator.services;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.sender.ValidateOrderRequestSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service("validateOrderProcessor")
public class ValidateOrderProcessor implements JavaDelegate {

	private final Logger logger;
	private final ObjectMapper objectMapper;
	private final ValidateOrderRequestSender validateOrderRequestSender;
	protected final RuntimeService runtimeService;

	public ValidateOrderProcessor(
					final ObjectMapper objectMapper,
					ValidateOrderRequestSender validateOrderRequestSender,
					 RuntimeService runtimeService

					) {
		this.validateOrderRequestSender = validateOrderRequestSender;
		this.objectMapper = objectMapper;
		logger = LoggerFactory.getLogger(ValidateOrderProcessor.class);
		this.runtimeService = runtimeService;
	}

@Override
	public void execute(DelegateExecution execution) {
	logger.info("achrrrrrraf");
	/*	Map<String, Object> vars = execution.getVariables();

		if(MapUtils.isEmpty(vars)){
			vars = new HashMap<>();
		}

		vars.put("isOrderValid", true);*/
		BasicMessage basicMessage = BasicMessage.builder()
						.orderId((String)execution.getVariable(ORDER_ID.toString()))
		                                        .correlationId(execution.getProcessInstanceId())
						.build();
		// Serialize message to send by rabbitMQ message
		 /*String message = null;
		try {
			message = objectMapper.writeValueAsString(basicMessage);
		} catch(JsonProcessingException e) {
			logger.error("cannot serialize message into Json");
		}
		vars.put("message", message);
		execution.setVariables(vars);*/
		validateOrderRequestSender.send(basicMessage);


}
}

