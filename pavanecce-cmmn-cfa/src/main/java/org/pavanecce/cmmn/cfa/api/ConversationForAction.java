package org.pavanecce.cmmn.cfa.api;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.jbpm.services.task.impl.model.OrganizationalEntityImpl;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.internal.task.api.model.AccessType;

@Entity
public class ConversationForAction {
	@Enumerated
	private ConversationForActionState state;
	@Basic()
	@Column(name = "is_committed")
	private boolean isCommitted;
	@Enumerated
	private AccessType inputAccessType;

	@Basic()
	private String inputType;

	@Basic()
	private long inputContentId = -1;

	@Basic()
	private AccessType outputAccessType;

	@Basic()
	private String outputType;

	@Basic()
	private long outputContentId = -1;

	@Basic()
	private String faultName;

	@Basic()
	private AccessType faultAccessType;

	@Basic()
	private String faultType;
	
	@Basic()
	private long faultContentId = -1;

	@ManyToOne
	private OrganizationalEntityImpl A;
	
	@ManyToOne
	private OrganizationalEntityImpl B;
	
	@ManyToOne
	private OrganizationalEntityImpl renegotiator;
	
	public ConversationForActionState getState() {
		return state;
	}

	public void setState(ConversationForActionState state) {
		this.state = state;
	}

	public boolean isCommitted() {
		return isCommitted;
	}

	public void setCommitted(boolean isCommitted) {
		this.isCommitted = isCommitted;
	}

	public AccessType getInputAccessType() {
		return inputAccessType;
	}

	public void setInputAccessType(AccessType inputAccessType) {
		this.inputAccessType = inputAccessType;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public long getInputContentId() {
		return inputContentId;
	}

	public void setInputContentId(long inputContentId) {
		this.inputContentId = inputContentId;
	}

	public AccessType getOutputAccessType() {
		return outputAccessType;
	}

	public void setOutputAccessType(AccessType outputAccessType) {
		this.outputAccessType = outputAccessType;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public long getOutputContentId() {
		return outputContentId;
	}

	public void setOutputContentId(long outputContentId) {
		this.outputContentId = outputContentId;
	}

	public String getFaultName() {
		return faultName;
	}

	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}

	public AccessType getFaultAccessType() {
		return faultAccessType;
	}

	public void setFaultAccessType(AccessType faultAccessType) {
		this.faultAccessType = faultAccessType;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public long getFaultContentId() {
		return faultContentId;
	}

	public void setFaultContentId(long faultContentId) {
		this.faultContentId = faultContentId;
	}

	public OrganizationalEntityImpl getA() {
		return A;
	}

	public void setA(OrganizationalEntity a) {
		A = (OrganizationalEntityImpl) a;
	}

	public OrganizationalEntity getB() {
		return B;
	}

	public void setB(OrganizationalEntity b) {
		B = (OrganizationalEntityImpl) b;
	}

	public OrganizationalEntity getRenegotiator() {
		return renegotiator;
	}

	public void setRenegotiator(OrganizationalEntity renegotiator) {
		this.renegotiator = (OrganizationalEntityImpl) renegotiator;
	}

}
