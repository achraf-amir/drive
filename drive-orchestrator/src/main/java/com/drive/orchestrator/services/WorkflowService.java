package com.drive.orchestrator.services;


import com.drive.common.beans.messaging.BasicMessage;

public interface WorkflowService {
	/**
	 * Launch main workflow.
	 *
	 */
	void launchMainWorkflow(BasicMessage order);
	
}
