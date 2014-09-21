package org.pavanecce.cmmn.cfa.impl;

import org.pavanecce.cmmn.cfa.api.ConversationActKind;
import org.pavanecce.cmmn.cfa.api.ConversationForActionState;

public class PromiseCommand extends AbstractConversationForActionCommand<Void> {

	private static final long serialVersionUID = -3174779982271593408L;

	private long conversationActId;

	private String comment;

	public PromiseCommand(String userId, long conversationActId, String comment) {
		this.userId = userId;
		this.conversationActId = conversationActId;
		this.comment = comment;
	}

	@Override
	public Void execute() {
		ConversationActImpl previous = find(ConversationActImpl.class, conversationActId);
		ConversationActImpl response = super.createResponseCopy(previous,ConversationActKind.PROMISE);
		response.setResultingConversationState(ConversationForActionState.PROMISED);
		response.setComment(comment);
		boolean wasDirectRequest = previous.getOwner() != null && previous.getOwner().getId().equals(userId);
		if (wasDirectRequest) {
			previous.setResponsePending(false);
		} else {
			previous.setResponsePending(true);
		}
		persist(response);
		return null;
	}

}
