package org.pavanecce.cmmn.cfa.api;

import org.jbpm.cmmn.task.internal.model.InternalPlannableTask;
import org.pavanecce.cmmn.cfa.impl.ConversationActImpl;

public interface InternalConversationForAction extends ConversationForAction,InternalPlannableTask {
	void setRequest(ConversationActImpl act);

}
