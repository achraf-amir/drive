package com.drive.orchestrator.services.impl;


import com.drive.common.beans.keys.ProcessDefinitionId;
import com.drive.common.beans.messaging.BasicMessage;
import com.drive.orchestrator.services.CamundaAbstractService;
import com.drive.orchestrator.services.WorkflowService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.dozer.DozerBeanMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.drive.common.beans.keys.WorkFlowVarsNameKeys.ORDER_ID;

@Service
public class WorkflowServiceImpl extends CamundaAbstractService implements WorkflowService {

	private final DozerBeanMapper mapper;



	public WorkflowServiceImpl(
					RuntimeService runtimeService,
					DozerBeanMapper mapper
	) {
		super(runtimeService, LoggerFactory.getLogger(WorkflowServiceImpl.class));
		this.mapper = mapper;

	}

	@Override
	public void launchMainWorkflow(BasicMessage orderMessage) {
		Map<String, Object> variables;
		if ( orderMessage.getWfVars()!= null && !orderMessage.getWfVars().isEmpty()){
			variables = orderMessage.getWfVars();
		}else {
			variables = new HashMap<>();
		}

		final String orderId = orderMessage.getOrderId();
		variables.put(ORDER_ID.toString(), orderId);
		 launchWorkflow(variables, ProcessDefinitionId.MAIN_WORKFLOW, orderId);
	}


	private void launchWorkflow(
					final Map<String, Object> variables,
					final ProcessDefinitionId processId,
					final String businessKey
	) {
		final ProcessInstance instance = startProcess(processId, businessKey, variables);
		logger.info(
						"orderId {} : launch {} process instance {} for {}",
						instance.getBusinessKey(),
						processId,
						instance.getProcessInstanceId(),
						instance.getProcessDefinitionId()
		);
	}
}
