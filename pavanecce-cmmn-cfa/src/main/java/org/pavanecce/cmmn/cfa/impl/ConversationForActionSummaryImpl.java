package org.pavanecce.cmmn.cfa.impl;

import java.util.Date;

import org.jbpm.cmmn.task.jpa.query.PlannableTaskSummaryImpl;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.User;
import org.kie.internal.task.api.model.InternalTaskSummary;
import org.kie.internal.task.api.model.SubTasksStrategy;
import org.pavanecce.cmmn.cfa.api.ConversationForActionSummary;

public class ConversationForActionSummaryImpl extends PlannableTaskSummaryImpl implements ConversationForActionSummary {

	public ConversationForActionSummaryImpl() {
		super();
	}

	public ConversationForActionSummaryImpl(InternalTaskSummary task, String discretionaryItemId, String planItemName) {
		super(task, discretionaryItemId, planItemName);
	}

	public ConversationForActionSummaryImpl(long id, String name, String description, Status status, int priority, String actualOwner,
			String createdBy, Date createdOn, Date activationTime, Date expirationTime, String processId, long processInstanceId,
			long parentId, String deploymentId, String discretionaryItemId, String planItemName) {
		super(id, name, description, status, priority, actualOwner, createdBy, createdOn, activationTime, expirationTime, processId,
				processInstanceId, parentId, deploymentId, discretionaryItemId, planItemName);
	}

	public ConversationForActionSummaryImpl(long id, String name, String description, Status status, int priority, String actualOwner,
			String createdBy, Date createdOn, Date activationTime, Date expirationTime, String processId, long processInstanceId,
			long parentId, String deploymentId) {
		super(id, name, description, status, priority, actualOwner, createdBy, createdOn, activationTime, expirationTime, processId,
				processInstanceId, parentId, deploymentId);
	}

	public ConversationForActionSummaryImpl(long id, String name, String subject, String description, Status status, int priority,
			boolean skipable, User actualOwner, User createdBy, Date createdOn, Date activationTime, Date expirationTime, String processId,
			int processSessionId, long processInstanceId, String deploymentId, SubTasksStrategy subTaskStrategy, long parentId) {
		super(id, name, subject, description, status, priority, skipable, actualOwner, createdBy, createdOn, activationTime, expirationTime,
				processId, processSessionId, processInstanceId, deploymentId, subTaskStrategy, parentId);
	}

}
