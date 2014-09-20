package org.pavanecce.cmmn.cfa.impl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.jbpm.cmmn.task.jpa.model.PlannableTaskImpl;
import org.pavanecce.cmmn.cfa.api.ConversationAct;
import org.pavanecce.cmmn.cfa.api.ConversationForActionState;
import org.pavanecce.cmmn.cfa.api.InternalConversationForAction;

@Entity(name = "ConversationForActionImpl")
@DiscriminatorValue("ConversationForAction")
public class ConversationForActionImpl extends PlannableTaskImpl implements InternalConversationForAction {
	@OneToOne
	private ConversationActImpl request;

	@OneToOne
	private ConversationActImpl commitment;

	@OneToOne
	private ConversationActImpl outcome;

	@Enumerated
	ConversationForActionState state;
	@Override
	public ConversationAct getRequest() {
		return request;
	}

	@Override
	public ConversationAct getCommitment() {
		return commitment;
	}

	@Override
	public ConversationAct getOutcome() {
		return outcome;
	}

	public ConversationForActionState getState() {
		return state;
	}

	public void setState(ConversationForActionState state) {
		this.state = state;
	}

	public void setRequest(ConversationActImpl request) {
		this.request = request;
	}

	public void setCommitment(ConversationActImpl commitment) {
		this.commitment = commitment;
	}

	public void setOutcome(ConversationActImpl outcome) {
		this.outcome = outcome;
	}
	

}
