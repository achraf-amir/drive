package com.drive.orchestrator.controllers;



import com.drive.common.beans.messaging.BasicMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.IS_ORDER_VALID;
import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;


@Service
public class GenericListener {

	private final ObjectMapper objectMapper;
	private final Logger logger;
	protected  RuntimeService runtimeService;
	@Autowired
	private ProcessEngine camunda;
	public GenericListener(
					ObjectMapper objectMapper,
					RuntimeService runtimeService
	) {
		this.runtimeService = runtimeService;
		this.objectMapper = objectMapper;
		this.logger = LoggerFactory.getLogger(GenericListener.class);
	}

	@RabbitListener(queues = {
					"${drive.queue.response.validate-order}"
	})

	public void receiveMessage(Message message) {
		Map<String, Object> vars = null;
		final String queueName = message.getMessageProperties().getConsumerQueue();

		final BasicMessage basicMessage;
		try {
			basicMessage = objectMapper.readValue(message.getBody(), BasicMessage.class);
		} catch (IOException e) {
			logger.error("error parsing message: {}", e.getMessage());
			throw new RuntimeException(e);
		}

		final String correlationId = basicMessage.getCorrelationId();
		final String orderId = basicMessage.getOrderId();
		boolean isOrderValid = (boolean)basicMessage.getWfVars().get(IS_ORDER_VALID.toString());
		if(MapUtils.isEmpty(vars)){
			vars = new HashMap<>();
		}
		vars.put(IS_ORDER_VALID.toString(), isOrderValid);


		logger.info(
						"orderId {}, correlationId {} : received on queue {}  and value {}",
						orderId, correlationId, queueName, new String(message.getBody())
		);

		runtimeService.createMessageCorrelation("Message_Validate_Order") //
		       .processInstanceVariableEquals(ORDER_ID.toString(), orderId) //
		       .setVariables(vars) //
		       .correlateWithResult();



	}

}
