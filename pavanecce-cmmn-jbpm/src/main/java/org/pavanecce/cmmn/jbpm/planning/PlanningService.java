package org.pavanecce.cmmn.jbpm.planning;

import java.util.Collection;

import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.task.api.InternalTaskService;
import org.pavanecce.cmmn.jbpm.ApplicableDiscretionaryItem;

public class PlanningService {
	private InternalTaskService taskService;
	private RuntimeManager runtimeManager;

	public RuntimeManager getRuntimeManager() {
		return runtimeManager;
	}

	public void setRuntimeManager(RuntimeManager runtimeManager) {
		this.runtimeManager = runtimeManager;
	}

	public void setTaskService(InternalTaskService taskService) {
		this.taskService = taskService;
	}

	public void submitPlan(final long parentTaskId, final Collection<PlannedTask> plannedTasks, boolean resume) {
		taskService.execute(new SubmitPlanCommand(runtimeManager, plannedTasks, parentTaskId, resume));
	}

	public PlanningTableInstance startPlanning(final long parentTaskId, String user, boolean suspend) {
		PlanningTableInstance result = new PlanningTableInstance(getPlannedItemsForParentTask(parentTaskId, true), getApplicableDiscretionaryItems(
				parentTaskId, user, suspend));
		return result;
	}

	private Collection<ApplicableDiscretionaryItem> getApplicableDiscretionaryItems(final long parentTaskId, final String user, boolean suspend) {
		return taskService.execute(new GetApplicableDiscretionaryItemsCommand(runtimeManager, parentTaskId, user, suspend));

	}

	private Collection<PlannedTaskSummary> getPlannedItemsForParentTask(final long parentTaskId, final boolean createMissing) {
		return taskService.execute(new GetPlannedItemsForParentTaskCommand(parentTaskId, createMissing));
	}

	public PlannedTask preparePlannedTask(final long parentTaskId, final String discretionaryItemId) {
		return taskService.execute(new PreparePlannedTaskCommand(runtimeManager, discretionaryItemId, parentTaskId));
	}

	public void makeDiscretionaryItemAvailable(final long parentTaskId, final String discretionaryItemId) {
		taskService.execute(new MakeDiscretionaryItemAvailableCommand(runtimeManager, discretionaryItemId, parentTaskId));
	}

	protected long getWorkItemId(long parentTaskId) {
		return taskService.getTaskById(parentTaskId).getTaskData().getWorkItemId();
	}

	public PlannedTask getPlannedTaskById(final long id) {
		return taskService.execute(new AbstractPlanningCommand<PlannedTask>() {

			private static final long serialVersionUID = -6636279175990254543L;

			@Override
			public PlannedTask execute() {
				return find(PlannedTaskImpl.class, id);
			}
		});
	}
}
