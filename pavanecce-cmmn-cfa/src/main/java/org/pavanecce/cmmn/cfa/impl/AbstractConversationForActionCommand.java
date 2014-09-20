package org.pavanecce.cmmn.cfa.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jbpm.cmmn.task.api.impl.AbstractTaskCommand;
import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.Group;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.User;
import org.kie.internal.task.api.TaskModelProvider;
import org.kie.internal.task.api.model.InternalContent;
import org.pavanecce.cmmn.cfa.api.ConversationForAction;

public abstract class AbstractConversationForActionCommand<T> extends AbstractTaskCommand<T> {

	private static final long serialVersionUID = -4217142163951701835L;

	protected UserGroupCallbackHelper userGroupCallbackHelper = new UserGroupCallbackHelper();

	private List<String> groupsForUser;

	public AbstractConversationForActionCommand() {
		super();
	}

	protected ConversationActImpl createResponseCopy(long previousId) {
		ConversationActImpl previous = find(ConversationActImpl.class, previousId);
		return createResponseCopy(previous);
	}

	protected ConversationActImpl createResponseCopy(ConversationActImpl previous) {
		ConversationActImpl result= new ConversationActImpl();
		result.setConversationForAction(previous.getConversationForAction());
		result.setFaultContentId(maybeCloneContent(previous.getFaultContentId()));
		result.setFaultName(previous.getFaultName());
		result.setFaultType(previous.getFaultType());
		result.setInputContentId(maybeCloneContent(previous.getInputContentId()));
		result.setOutputContentId(maybeCloneContent(previous.getOutputContentId()));
		result.setCommitted(previous.isCommitted());
		result.setOwner(previous.getOwner());
		result.setRenegotiator(previous.getRenegotiator());
		result.setResultingConversationState(previous.getResultingConversationState());
		result.setPreviousStep(previous);
		result.setActor(new UserImpl(userId));
		result.setAddressedTo(previous.getActor());
		if (previous.getDateOfCommencement() != null) {
			result.setDateOfCommencement(new Date(previous.getDateOfCommencement().getTime()));
		}
		if (previous.getDateOfCompletion() != null) {
			result.setDateOfCompletion(new Date(previous.getDateOfCompletion().getTime()));
		}
		result.setResponsePending(true);
		return result;
	}

	private long maybeCloneContent(long contentId) {
		if (contentId >= 0) {
			Content contentById = super.taskContext.getTaskContentService().getContentById(contentId);
			if (contentById != null) {
				InternalContent newContent = (InternalContent) TaskModelProvider.getFactory().newContent();
				newContent.setContent(contentById.getContent());
				super.persist(newContent);
				return newContent.getId();
			}
		}
		return -1;
	}

	protected long ensureContentIdPresent(ConversationForAction cfa, Map<String, Object> input) {
		Map<String, Object> map = Collections.<String, Object> singletonMap("key", input);
		long ensureContentIdPresent = ensureContentIdPresent(cfa, -1, map, "key");
		return ensureContentIdPresent;
	}

	private List<String> getUsersGroups() {
		if (this.groupsForUser == null) {
			this.groupsForUser = this.taskContext.getUserGroupCallback().getGroupsForUser(userId, null, null);
		}
		return groupsForUser;
	}

	protected boolean isUserBusinessAdministrator(ConversationForAction cfa) {

		List<OrganizationalEntity> orgEntities = cfa.getPeopleAssignments().getBusinessAdministrators();
		boolean isUserInOrgEntities = false;
		if (orgEntities != null) {
			for (OrganizationalEntity oe : orgEntities) {
				if (oe instanceof User) {
					if (oe.getId().equals(userId)) {
						isUserInOrgEntities = true;
						break;
					}
				} else {
					if(getUsersGroups().contains(oe.getId())){
						isUserInOrgEntities = true;
						break;
					}
				}
			}
		}
		return isUserInOrgEntities;
	}

	protected boolean isUserInitator(ConversationForAction cfa) {
		boolean isUserInitiator = false;
		if (cfa.getPeopleAssignments().getTaskInitiator() != null && cfa.getPeopleAssignments().getTaskInitiator().getId().equals(userId)) {
			isUserInitiator = true;
		}
		return isUserInitiator;
	}

}