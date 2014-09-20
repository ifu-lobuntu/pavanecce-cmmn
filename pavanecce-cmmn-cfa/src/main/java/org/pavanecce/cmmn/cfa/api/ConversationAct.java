package org.pavanecce.cmmn.cfa.api;

import org.kie.api.task.model.OrganizationalEntity;

public interface ConversationAct {
	ConversationForAction getConversationForAction();

	ConversationActKind getKind();

	ConversationForActionState getResultingConversationState();

	boolean isCommitted();

	String getFaultName();

	String getFaultType();

	OrganizationalEntity getRenegotiator();

	long getInputContentId();

	long getOutputContentId();

	long getFaultContentId();

	OrganizationalEntity getOwner();

	Long getId();

}