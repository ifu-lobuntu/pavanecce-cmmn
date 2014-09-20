package org.pavanecce.cmmn.cfa.api;

import org.kie.api.task.model.Task;
import org.pavanecce.cmmn.cfa.impl.ConversationActImpl;

public interface ConversationForAction extends Task{
	ConversationAct getRequest();
	ConversationAct getCommitment();
	ConversationAct getOutcome();
	
}
