package com.drive.orchestrator.services;

import com.drive.common.beans.keys.ProcessDefinitionId;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

import java.util.Map;

public abstract class CamundaAbstractService {

	/** Logger. */
	protected final Logger logger;

	/** Runtime Service. */
	protected final RuntimeService runtimeService;

	public CamundaAbstractService(
					final RuntimeService runtimeService,
					final Logger logger
	) {
		this.runtimeService = runtimeService;
		this.logger = logger;
	}

	protected ProcessInstance startProcess(
					@NonNull final ProcessDefinitionId processDefinitionId,
					@NonNull final String businessKey,
					@Nullable final Map<String, Object> variables
	) {
		final String processDefinitionKey = processDefinitionId.toString();
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}
}
