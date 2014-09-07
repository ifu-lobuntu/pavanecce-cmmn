package org.pavanecce.cmmn.cfa.api;

public enum ConversationForActionState {
	REQUESTED {
		public void promise(ConversationForAction n) {
			n.setState(PROMISED);
		}

		public void withdraw(ConversationForAction n) {
			n.setState(NEGOTIATION_FAILED);
		}

		public void reject(ConversationForAction n) {
			n.setState(NEGOTIATION_FAILED);
		}

		public void counterToA(ConversationForAction n) {
			n.setState(COUNTERED);
		}

	},
	PROMISED {
		public void accept(ConversationForAction n) {
			n.setState(COMMITTED);
		}
	},
	COUNTERED {
		public void accept(ConversationForAction n) {
			n.setState(COMMITTED);
		}
		public void withdraw(ConversationForAction n) {
			if(n.isCommitted()){
				n.setState(WITHDRAWN);
			}else{
				n.setState(NEGOTIATION_FAILED);
			}
		}

		public void counterToB(ConversationForAction n) {
			if(n.isCommitted()){
				throw new UnsupportedOperationException("counterToB can only be called if the negotation is not yet committed to");
			}else{
				n.setState(REQUESTED);
			}
		}
		@Override
		public void counterToRenegotiator(ConversationForAction n) {
			if(n.isCommitted()){
				n.setState(RENEGOTIATION_REQUESTED);
			}else{
				throw new UnsupportedOperationException("counterToRenegotiator can only be called if the negotation was previously committed to");
			}
		}
	},
	NEGOTIATION_FAILED {
	},
	COMMITTED {
		@Override
		public void start(ConversationForAction n) {
			n.setState(IN_PROGRESS);
		}
	},
	RENEGOTIATION_REQUESTED {
		public void accept(ConversationForAction n) {
			n.setState(COMMITTED);
		}

		public void withdraw(ConversationForAction n) {
			n.setState(NEGOTIATION_FAILED);
		}

		public void reject(ConversationForAction n) {
			n.setState(NEGOTIATION_FAILED);
		}

		public void counterToA(ConversationForAction n) {
			n.setState(COUNTERED);
		}
	},
	IN_PROGRESS{
		@Override
		public void assertCompletion(ConversationForAction n) {
			n.setState(AWAITING_ACCEPTANCE);
		}
	},
	AWAITING_ACCEPTANCE{
		@Override
		public void declareAdequate(ConversationForAction n) {
			n.setState(CONSUMMATED);
		}
	},
	CONSUMMATED{
	},
	WITHDRAWN {
	},
	RENEGUED {
	};

	private ConversationForActionState() {
	}

	public void promise(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'promise'");
	}

	public void accept(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'accept'");
	}
	public void start(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'start'");
	}

	public void withdraw(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'withdraw'");
	}

	public void reject(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'reject'");

	}

	public void counterToA(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'counterToA'");
	}

	public void counterToB(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'counterToB'");
	}

	public void counterToRenegotiator(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'counterToRenegotiator'");

	}

	public void declareAdequate(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'declareAdequate'");
	}
	public void declareInadequate(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'counterFromRenegotiator'");
	}
	public void assertCompletion(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'assertCompletion'");
	}
	public void counterFromRenegotiator(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'counterFromRenegotiator'");
	}

	public void renegue(ConversationForAction n) {
		throw new UnsupportedOperationException(this.name() + " does not support the operation 'renegue'");

	}
}
