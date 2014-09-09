package org.pavanecce.cmmn.jbpm.task;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.services.task.commands.TaskCommand;
import org.jbpm.services.task.commands.TaskContext;
import org.jbpm.services.task.impl.TaskServiceEntryPointImpl;
import org.jbpm.services.task.impl.model.ContentDataImpl;
import org.jbpm.services.task.impl.model.ContentImpl;
import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.jbpm.shared.services.api.JbpmServicesPersistenceManager;
import org.jbpm.shared.services.impl.events.JbpmServicesEventImpl;
import org.jbpm.shared.services.impl.events.JbpmServicesEventListener;
import org.kie.api.task.model.Task;
import org.kie.internal.command.Context;
import org.kie.internal.task.api.ContentMarshallerContext;
import org.kie.internal.task.api.TaskIdentityService;
import org.kie.internal.task.api.TaskInstanceService;
import org.kie.internal.task.api.TaskQueryService;
import org.pavanecce.cmmn.jbpm.infra.CmmnTaskLifecycleEventListener;

public abstract class AbstractTaskCommand<T> extends TaskCommand<T> {

	private static final long serialVersionUID = -6267126920414609188L;
	private JbpmServicesPersistenceManager pm;
	private TaskServiceEntryPointImpl ts;

	public AbstractTaskCommand() {
		super();
	}

	public abstract T execute();

	@Override
	public final T execute(Context context) {
		TaskContext taskContext = (TaskContext) context;
		init(taskContext.getTaskService());
		return execute();
	}

	protected void addContent(Long id, Map<String, Object> params) {
		ts.addContent(id, params);
	}

	protected void persist(Object o) {
		this.pm.persist(o);
	}

	protected void merge(Object o) {
		this.pm.merge(o);
	}

	protected <X> X find(Class<X> c, Object id) {
		return pm.find(c, id);
	}

	protected Task getTaskById(long taskId) {
		return ts.getTaskById(taskId);
	}

	protected Task getTaskByWorkItemId(long id) {
		return ts.getTaskByWorkItemId(id);
	}

	protected TaskIdentityService getTaskIdentityService() {
		return ts.getTaskIdentityService();
	}

	protected TaskQueryService getTaskQueryService() {
		return ts.getTaskQueryService();
	}
	protected TaskInstanceService getTaskInstanceService(){
		return ts.getTaskInstanceService();
	}
	private void init(TaskServiceEntryPointImpl ts) {
		try {
			this.ts = ts;
			if (pm == null) {
				// TODO Won't work in CDI
				Field pmField = ts.getTaskAdminService().getClass().getDeclaredField("pm");
				pmField.setAccessible(true);
				pm = (JbpmServicesPersistenceManager) pmField.get(ts.getTaskAdminService());
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final String toString() {
		return getClass().getSimpleName() + "(" + taskId + ", " + userId + ");";
	}

	private Object getCorrectContentObject(Map<String, Object> parameters, String contentParamName) {
		Object contentObject = parameters.get(contentParamName);
		if (contentObject == null) {
			contentObject = new HashMap<String, Object>(parameters);
		}
		return contentObject;
	}

	protected long ensureContentPresent(Task task, long existingId, Map<String, Object> newContentAsMap, String contentNameInMap) {
		ContentImpl existingContent = null;
		ContentMarshallerContext mc = ts.getMarshallerContext(task);
		if (existingId < 0 || (existingContent = pm.find(ContentImpl.class, existingId)) == null) {
			Object correctContentObject = getCorrectContentObject(newContentAsMap, contentNameInMap);
			ContentDataImpl contentData = ContentMarshallerHelper.marshal(correctContentObject, mc.getEnvironment());
			ContentImpl newlyCreatedContent = new ContentImpl(contentData.getContent());
			persist(newlyCreatedContent);
			return newlyCreatedContent.getId();
		} else {
			Object contentObjectToUpdateTaskWith = mergeContentObjectIfPossible(newContentAsMap, contentNameInMap, existingContent, mc);
			ContentDataImpl contentData = ContentMarshallerHelper.marshal(contentObjectToUpdateTaskWith, mc.getEnvironment());
			existingContent.setContent(contentData.getContent());
			return existingContent.getId();
		}
	}

	@SuppressWarnings("unchecked")
	private Object mergeContentObjectIfPossible(Map<String, Object> newContentAsMap, String contentNameInMap, ContentImpl existingContent,
			ContentMarshallerContext mc) {
		Object contentObjectToUpdateTaskWith = ContentMarshallerHelper.unmarshall(existingContent.getContent(), mc.getEnvironment());
		if (contentObjectToUpdateTaskWith instanceof Map) {
			Map<String, Object> existingOutputAsMap = (Map<String, Object>) contentObjectToUpdateTaskWith;
			Object newContentObject = getCorrectContentObject(newContentAsMap, contentNameInMap);
			if (newContentObject instanceof Map) {
				// merge
				existingOutputAsMap.putAll((Map<String, Object>) newContentObject);
			} else if (newContentObject != null) {
				// replace
				contentObjectToUpdateTaskWith = newContentObject;
			}
		} else {
			contentObjectToUpdateTaskWith = getCorrectContentObject(newContentAsMap, contentNameInMap);
		}
		return contentObjectToUpdateTaskWith;
	}

	private List<CmmnTaskLifecycleEventListener> getCmmnEventListeners() {
		JbpmServicesEventImpl<Task> cdiEvent = (JbpmServicesEventImpl<Task>) ts.getTaskLifecycleEventListeners();
		List<CmmnTaskLifecycleEventListener> result = new ArrayList<CmmnTaskLifecycleEventListener>();
		for (JbpmServicesEventListener<?> el : cdiEvent.getListeners()) {
			if (el instanceof CmmnTaskLifecycleEventListener) {
				result.add((CmmnTaskLifecycleEventListener) el);
			}
		}
		return result;
	}

	protected void fireBeforeTaskStartedAutomaticallyEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskStartedAutomaticallyEvent(t);
		}
	}

	protected void fireAfterTaskStartedAutomaticallyEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskStartedAutomaticallyEvent(t);
		}
	}

	protected void fireBeforeTaskExitCriteriaEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskExitCriteriaEvent(t);
		}
	}

	protected void fireAfterTaskExitCriteriaEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskExitCriteriaEvent(t);
		}
	}

	protected void fireBeforeTaskCompletedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskCompletedEvent(t);
		}
	}

	protected void fireAfterTaskCompletedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskCompletedEvent(t);
		}
	}

	protected void fireBeforeTaskAddedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskAddedEvent(t);
		}
	}

	protected void fireAfterTaskAddedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskAddedEvent(t);
		}
	}

	protected void fireBeforeTaskReactivatedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskReactivatedEvent(t);
		}
	}

	protected void fireAfterTaskReactivatedEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskReactivatedEvent(t);
		}
	}

	protected void fireBeforeTaskReenabledEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskReenabledEvent(t);
		}
	}

	protected void fireAfterTaskReenabledEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskReenabledEvent(t);
		}
	}

	protected void fireBeforeTaskResumedFromParentEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskParentResumedEvent(t);
		}
	}

	protected void fireAfterTaskResumedFromParentEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskParentResumedEvent(t);
		}
	}

	protected void fireBeforeTaskSuspendedFromParentEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.beforeTaskParentSuspendedEvent(t);
		}
	}

	protected void fireAfterTaskSuspendedFromParentEvent(Task t) {
		for (CmmnTaskLifecycleEventListener l : getCmmnEventListeners()) {
			l.afterTaskParentSuspendedEvent(t);
		}
	}
}