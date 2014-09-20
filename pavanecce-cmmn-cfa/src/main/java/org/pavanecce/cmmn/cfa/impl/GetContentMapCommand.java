package org.pavanecce.cmmn.cfa.impl;

import java.util.Map;

import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.kie.api.task.model.Content;
import org.kie.internal.task.api.ContentMarshallerContext;

public class GetContentMapCommand extends AbstractConversationForActionCommand<Map<String,Object>> {

	private static final long serialVersionUID = 11231231414L;
	private long contentId;

	public GetContentMapCommand(long conversationForActionId, long contentId) {
		super();
		this.contentId=contentId;
		super.taskId=conversationForActionId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> execute() {
		Content c = taskContext.getTaskContentService().getContentById(contentId);
		ContentMarshallerContext mc = taskContext.getTaskContentService().getMarshallerContext(getTaskById(taskId));
		return (Map<String, Object>) ContentMarshallerHelper.unmarshall(c.getContent(), mc.getEnvironment());
	}

}
