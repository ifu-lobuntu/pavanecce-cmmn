package org.pavanecce.cmmn.cfa.impl;

import org.pavanecce.cmmn.jbpm.task.AbstractTaskCommand;

public abstract class AbstractConversationForActionCommand<T> extends AbstractTaskCommand<T> {

	private static final long serialVersionUID = -4217142163951701835L;

	public AbstractConversationForActionCommand() {
		super();
	}

	protected long getWorkItemId(long parentTaskId) {
		return getTaskById(parentTaskId).getTaskData().getWorkItemId();
	}
}