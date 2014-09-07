package org.pavanecce.cmmn.cfa.impl;

import org.jbpm.services.task.commands.TaskContext;
import org.jbpm.shared.services.api.JbpmServicesPersistenceManager;
import org.pavanecce.cmmn.cfa.api.ConversationForAction;

public class RequestDirectlyCommand extends AbstractConversationForActionCommand<Void> {

	private static final long serialVersionUID = 11231231414L;
	private ConversationForAction cfa;

	public RequestDirectlyCommand(JbpmServicesPersistenceManager pm, ConversationForAction cfa, String userId) {
		super(pm);
		this.cfa=cfa;
		this.userId=userId;
	}

	@Override
	public Void execute(TaskContext context) {
		
		return null;
	}

}
