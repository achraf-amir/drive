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

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.CATEGORIZATION;
import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service
@RabbitListener(queues = "${drive.queue.response.categorize-order}")
public class CategorizeOrderController {

	private final Logger logger;
	private final RuntimeService runtimeService;

	public CategorizeOrderController(
					RuntimeService runtimeService
	) {
		this.runtimeService = runtimeService;
		logger = LoggerFactory.getLogger(CategorizeOrderController.class);
	}

	@RabbitHandler
	public void receive(BasicMessage basicMessage) {

		Map<String, Object> vars = null;
		final String correlationId = basicMessage.getCorrelationId();
		final String orderId = basicMessage.getOrderId();
		String orderCategory = (String)basicMessage.getWfVars().get(CATEGORIZATION.toString());
		if(MapUtils.isEmpty(vars)){
			vars = new HashMap<>();
		}
		vars.put(CATEGORIZATION.toString(), orderCategory);


		logger.info(
						"orderId {}, correlationId {} : received on queue  and value {}",
						orderId, correlationId, basicMessage
		);

		runtimeService.createMessageCorrelation("Message_Categorize_Order") //
		              .processInstanceVariableEquals(ORDER_ID.toString(), orderId) //
		              .setVariables(vars) //
		              .correlateWithResult();
	}

}
