package com.drive.orchestrator.services;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.sender.CategorizeOrderRequestSender;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service("categorizeOrderProcessor")
public class CategorizeOrderProcessor implements JavaDelegate {

	private final Logger logger;
	private final CategorizeOrderRequestSender categorizeOrderRequestSender;
	protected final RuntimeService runtimeService;

	public CategorizeOrderProcessor(
					CategorizeOrderRequestSender categorizeOrderRequestSender,
					 RuntimeService runtimeService

					) {
		this.categorizeOrderRequestSender = categorizeOrderRequestSender;
		logger = LoggerFactory.getLogger(CategorizeOrderProcessor.class);
		this.runtimeService = runtimeService;
	}

@Override
	public void execute(DelegateExecution execution) {
	logger.info("start excecute CategorizeOrderProcessor");
		BasicMessage basicMessage = BasicMessage.builder()
						.orderId((String)execution.getVariable(ORDER_ID.toString()))
            .correlationId(execution.getProcessInstanceId())
						.build();

		categorizeOrderRequestSender.send(basicMessage);
}
}

