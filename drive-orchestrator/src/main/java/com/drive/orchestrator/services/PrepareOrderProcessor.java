package com.drive.orchestrator.services;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.sender.PrepareOrderRequestSender;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service("prepareOrderProcessor")
public class PrepareOrderProcessor implements JavaDelegate {

	private final Logger logger;
	private final PrepareOrderRequestSender prepareOrderRequestSender;
	protected final RuntimeService runtimeService;

	public PrepareOrderProcessor(
					PrepareOrderRequestSender prepareOrderRequestSender,
					 RuntimeService runtimeService

					) {
		this.prepareOrderRequestSender = prepareOrderRequestSender;
		logger = LoggerFactory.getLogger(PrepareOrderProcessor.class);
		this.runtimeService = runtimeService;
	}

@Override
	public void execute(DelegateExecution execution) {
	logger.info("start excecute prepareOrderProcessor");
		BasicMessage basicMessage = BasicMessage.builder()
						.orderId((String)execution.getVariable(ORDER_ID.toString()))
            .correlationId(execution.getProcessInstanceId())
						.build();

		prepareOrderRequestSender.send(basicMessage);
}
}

