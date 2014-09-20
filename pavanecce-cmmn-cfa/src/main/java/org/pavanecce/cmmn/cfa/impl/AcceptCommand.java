package org.pavanecce.cmmn.cfa.impl;

import org.pavanecce.cmmn.cfa.api.ConversationActKind;
import org.pavanecce.cmmn.cfa.api.ConversationForActionState;

public class AcceptCommand extends AbstractConversationForActionCommand<Void> {

	private static final long serialVersionUID = -3174779982271593408L;

	private long conversationActId;

	private String comment;

	public AcceptCommand(String userId, long conversationActId, String comment) {
		this.userId = userId;
		this.conversationActId = conversationActId;
		this.comment = comment;
	}

	@Override
	public Void execute() {
		ConversationActImpl previous = find(ConversationActImpl.class, conversationActId);
		ConversationActImpl accept = super.createResponseCopy(previous);
		accept.setKind(ConversationActKind.ACCEPT);
		accept.setResultingConversationState(ConversationForActionState.COMMITTED);
		accept.setCommitted(true);
		accept.setComment(comment);
		previous.setResponsePending(false);
		persist(accept);
		previous.getConversationForAction().setCommitment(accept);
		getTaskInstanceService().claim(accept.getConversationForAction().getId(), previous.getOwner().getId());
		return null;
	}

}
