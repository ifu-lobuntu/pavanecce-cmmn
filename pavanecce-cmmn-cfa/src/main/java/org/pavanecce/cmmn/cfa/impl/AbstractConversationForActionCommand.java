package org.pavanecce.cmmn.cfa.impl;

import org.jbpm.shared.services.api.JbpmServicesPersistenceManager;
import org.pavanecce.cmmn.jbpm.task.AbstractTaskCommand;

public abstract class AbstractConversationForActionCommand<T> extends AbstractTaskCommand<T> {

	private static final long serialVersionUID = -4217142163951701835L;

	public AbstractConversationForActionCommand(JbpmServicesPersistenceManager pm) {
		super(pm);
	}

	protected long getWorkItemId(long parentTaskId) {
		return ts.getTaskById(parentTaskId).getTaskData().getWorkItemId();
	}
}