package org.pavanecce.cmmn.cfa.impl;

import org.pavanecce.cmmn.cfa.api.ConversationForAction;

public class RequestDirectlyCommand extends AbstractConversationForActionCommand<Void> {

	private static final long serialVersionUID = 11231231414L;
	private ConversationForAction cfa;

	public RequestDirectlyCommand(ConversationForAction cfa, String userId) {
		super();
		this.cfa=cfa;
		this.userId=userId;
	}

	@Override
	public Void execute() {
		
		return null;
	}

}
