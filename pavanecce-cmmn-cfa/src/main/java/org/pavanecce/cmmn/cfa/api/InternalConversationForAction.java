package org.pavanecce.cmmn.cfa.api;

import org.jbpm.cmmn.task.internal.model.InternalPlannableTask;

public interface InternalConversationForAction extends ConversationForAction, InternalPlannableTask {
	void setRequest(ConversationAct request);

	void setCommitment(ConversationAct commitment);

	void setOutcome(ConversationAct outcome);

	void setCurrentAct(ConversationAct result);

	void setConversationState(ConversationForActionState s);

}
