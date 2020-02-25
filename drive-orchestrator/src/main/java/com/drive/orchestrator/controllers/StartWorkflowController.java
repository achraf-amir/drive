package com.drive.orchestrator.controllers;


import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "${drive.queue.request.capture-order}")
public class StartWorkflowController {

	/** Logger. */
	private final Logger logger;

	/** Service. */
	private final WorkflowService workflowService;

	public StartWorkflowController(
					WorkflowService workflowService
	) {
		this.workflowService = workflowService;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@RabbitHandler
	public void receive(BasicMessage message) {
		final String orderId = message.getOrderId();
		logger.info("orderId {} : receive start workflow message", orderId);
		try {
			 workflowService.launchMainWorkflow(message);
		} catch(Exception e) {
			logger.error("orderId {} : cannot start main workflow", e, orderId);
			throw e;
		}
	}
}
