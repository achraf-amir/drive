package com.drive.orchestrator.services.error;

import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.sender.ErrorOrderRequestSender;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service("submitErrorActivity")
public class SubmitErrorActivity implements JavaDelegate {

	private final ErrorOrderRequestSender errorOrderRequestSender;
	private final Logger logger;

	public SubmitErrorActivity(
					ErrorOrderRequestSender errorOrderRequestSender
	) {
		this.errorOrderRequestSender =  errorOrderRequestSender;
		logger = LoggerFactory.getLogger(SubmitErrorActivity.class);
	}


	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		logger.info("send error order");
		BasicMessage basicMessage = BasicMessage.builder()
						.orderId((String)delegateExecution.getVariable(ORDER_ID.toString()))
						.correlationId(delegateExecution.getProcessInstanceId())
						.build();
		errorOrderRequestSender.send(basicMessage);
		throw new BpmnError("order_error");

	}
}
