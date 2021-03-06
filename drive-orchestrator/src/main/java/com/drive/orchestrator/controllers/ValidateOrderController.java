package com.drive.orchestrator.controllers;

import com.drive.common.beans.messaging.BasicMessage;
import org.apache.commons.collections.MapUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.IS_ORDER_VALID;
import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service
@RabbitListener(queues = "${drive.queue.response.validate-order}")
public class ValidateOrderController {

	private final Logger logger;
	private final RuntimeService runtimeService;

	public ValidateOrderController(
					RuntimeService runtimeService
	) {
		this.runtimeService = runtimeService;
		logger = LoggerFactory.getLogger(ValidateOrderController.class);
	}

	@RabbitHandler
	public void receive(BasicMessage basicMessage) {

		Map<String, Object> vars = null;
		final String correlationId = basicMessage.getCorrelationId();
		final String orderId = basicMessage.getOrderId();
		boolean isOrderValid = (boolean)basicMessage.getWfVars().get(IS_ORDER_VALID.toString());		if(MapUtils.isEmpty(vars)){
			vars = new HashMap<>();
		}
		vars.put(IS_ORDER_VALID.toString(), isOrderValid);


		logger.info(
						"orderId {}, correlationId {} : received on queue  and value {}",
						orderId, correlationId, basicMessage
		);

		runtimeService.createMessageCorrelation("Message_Validate_Order") //
		              .processInstanceVariableEquals(ORDER_ID.toString(), orderId) //
		              .setVariables(vars) //
		              .correlateWithResult();
	}

}
