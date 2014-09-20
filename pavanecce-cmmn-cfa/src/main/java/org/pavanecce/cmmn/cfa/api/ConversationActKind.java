package org.pavanecce.cmmn.cfa.api;

import static org.pavanecce.cmmn.cfa.api.AllowedConversationRole.Any;
import static org.pavanecce.cmmn.cfa.api.AllowedConversationRole.CounterNegotiator;
import static org.pavanecce.cmmn.cfa.api.AllowedConversationRole.Initiator;
import static org.pavanecce.cmmn.cfa.api.AllowedConversationRole.Owner;
import static org.pavanecce.cmmn.cfa.api.AllowedConversationRole.Renegotiator;

public enum ConversationActKind {
	REQUEST(Initiator), PROMISE(Owner), COUNTER_TO_INITIATOR(Owner), COUNTER_TO_OWNER(Initiator), ACCEPT(Initiator), START(Owner), WITHDRAW(Initiator), REJECT(
			Owner), ASSERT(Owner), DECLARE_INADEQUATE(Initiator), DECLARE_ADEQUATE(Initiator), RENEGOTIATE(Any), RENEGE(Owner), COUNTER_TO_RENEGOTIATOR(
			CounterNegotiator), COUNTER_FROM_RENEGOTIATOR(Renegotiator),ACCEPT_NEW_TERMS(CounterNegotiator);
	private AllowedConversationRole allowed;

	private ConversationActKind(AllowedConversationRole  allowed) {
		this.allowed = allowed;
	}

	public AllowedConversationRole  getAllowedRole() {
		return allowed;
	}
}
