package org.pavanecce.cmmn.jbpm.task;

import java.util.Map;

import org.kie.api.task.model.Task;
import org.kie.internal.task.api.model.InternalTaskData;

public class SetTaskOutputCommand extends AbstractTaskCommand<Long> {

	private static final long serialVersionUID = 5765007237796573932L;
	Map<String, Object> outputAsMap;

	public SetTaskOutputCommand(long taskId, Map<String, Object> outputAsMap) {
		super();
		this.taskId = taskId;
		this.outputAsMap = outputAsMap;
	}

	public Long execute() {
		Task task = getTaskById(taskId);
		InternalTaskData itd = (InternalTaskData) task.getTaskData();
		itd.setOutputContentId(ensureContentPresent(task, itd.getOutputContentId(), outputAsMap, "Output"));
		return (Long) task.getId();
	}
}
