package org.pavanecce.cmmn.cfa.impl;

import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.task.api.InternalTaskService;
import org.pavanecce.cmmn.cfa.api.ConversationForAction;

public class ConversationForActionService {
	private InternalTaskService taskService;
	private RuntimeManager runtimeManager;

	public RuntimeManager getRuntimeManager() {
		return runtimeManager;
	}

	public void setRuntimeManager(RuntimeManager runtimeManager) {
		this.runtimeManager = runtimeManager;
	}

	public void setTaskService(InternalTaskService taskService) {
		this.taskService = taskService;
	}
	public void requestDirectly(ConversationForAction cfa, String userId){
		taskService.execute(new RequestDirectlyCommand(cfa, userId));
	}
}
