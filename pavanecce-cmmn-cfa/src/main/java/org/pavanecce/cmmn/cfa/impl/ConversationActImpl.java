package org.pavanecce.cmmn.cfa.impl;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jbpm.services.task.impl.model.OrganizationalEntityImpl;
import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.User;
import org.pavanecce.cmmn.cfa.api.ConversationAct;
import org.pavanecce.cmmn.cfa.api.ConversationActKind;
import org.pavanecce.cmmn.cfa.api.ConversationForAction;
import org.pavanecce.cmmn.cfa.api.ConversationForActionState;

@Entity(name="ConversationActImpl")
public class ConversationActImpl implements ConversationAct {

	@Id
	@GeneratedValue
	Long id;

	@ManyToOne
	private ConversationForActionImpl conversationForAction;

	@ManyToOne
	private ConversationActImpl previousStep;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfCommencement;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfCompletion;

	@Enumerated(EnumType.STRING)
	private ConversationActKind kind;

	@ManyToOne
	private OrganizationalEntityImpl addressedTo;

	@ManyToOne
	private UserImpl actor;

	@Basic
	boolean isResponsePending;

	@Enumerated
	private ConversationForActionState resultingConversationState;

	@Basic()
	@Column(name = "is_committed")
	private boolean isCommitted;

	@Column(name = "input_content_id")
	private long inputContentId=-1;

	@Column(name = "output_content_id")
	private long outputContentId=-1;

	@Column(name = "fault_content_id")
	private long faultContentId=-1;

	@Basic()
	private String faultName;

	@Basic()
	private String faultType;

	@ManyToOne
	private OrganizationalEntityImpl owner;

	@ManyToOne
	private OrganizationalEntityImpl renegotiator;

	private String comment;

	@Override
	public boolean isCommitted() {
		return isCommitted;
	}

	public void setCommitted(boolean isCommitted) {
		this.isCommitted = isCommitted;
	}

	@Override
	public String getFaultName() {
		return faultName;
	}

	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}

	@Override
	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	@Override
	public OrganizationalEntity getRenegotiator() {
		return renegotiator;
	}

	public void setRenegotiator(OrganizationalEntity renegotiator) {
		this.renegotiator = (OrganizationalEntityImpl) renegotiator;
	}


	@Override
	public OrganizationalEntityImpl getOwner() {
		return owner;
	}

	public void setOwner(OrganizationalEntity owner) {
		this.owner = (OrganizationalEntityImpl) owner;
	}


	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConversationActKind getKind() {
		return kind;
	}

	public ConversationForActionState getResultingConversationState() {
		return resultingConversationState;
	}

	public ConversationForAction getConversationForAction() {
		return conversationForAction;
	}

	public void setResultingConversationState(ConversationForActionState resultingConversationState) {
		this.resultingConversationState = resultingConversationState;
	}

	public Date getDateOfCommencement() {
		return dateOfCommencement;
	}

	public void setDateOfCommencement(Date dateOfCommencement) {
		this.dateOfCommencement = dateOfCommencement;
	}

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public void setConversationForAction(ConversationForActionImpl conversationForAction) {
		this.conversationForAction = conversationForAction;
	}

	public void setKind(ConversationActKind kind) {
		this.kind = kind;
	}

	public ConversationActImpl getPreviousStep() {
		return previousStep;
	}

	public void setPreviousStep(ConversationActImpl previousStep) {
		this.previousStep = previousStep;
	}

	public OrganizationalEntityImpl getAddressedTo() {
		return addressedTo;
	}

	public void setAddressedTo(OrganizationalEntity user) {
		this.addressedTo = (OrganizationalEntityImpl) user;
	}

	public UserImpl getActor() {
		return actor;
	}

	public void setActor(User user) {
		this.actor = (UserImpl) user;
	}

	public boolean isResponsePending() {
		return isResponsePending;
	}

	public void setResponsePending(boolean responsePending) {
		this.isResponsePending = responsePending;
	}

	public long getInputContentId() {
		return inputContentId;
	}

	public void setInputContentId(long inputContentId) {
		this.inputContentId = inputContentId;
	}

	public long getOutputContentId() {
		return outputContentId;
	}

	public void setOutputContentId(long outputContentId) {
		this.outputContentId = outputContentId;
	}

	public long getFaultContentId() {
		return faultContentId;
	}

	public void setFaultContentId(long faultContentId) {
		this.faultContentId = faultContentId;
	}

	public void setConversationForAction(ConversationForAction cfa) {
		this.conversationForAction=(ConversationForActionImpl) cfa;
	}

	public void setComment(String comment) {
		this.comment=comment;
	}

}
