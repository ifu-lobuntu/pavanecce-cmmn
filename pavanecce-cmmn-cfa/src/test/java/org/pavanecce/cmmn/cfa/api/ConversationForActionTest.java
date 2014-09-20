package org.pavanecce.cmmn.cfa.api;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jbpm.cmmn.test.AbstractCmmnCaseTestCase;
import org.jbpm.services.task.impl.model.TaskDataImpl;
import org.junit.Test;
import org.kie.api.task.model.TaskSummary;
import org.pavanecce.cmmn.cfa.impl.ConversationForActionImpl;
import org.pavanecce.cmmn.cfa.impl.ConversationForActionServiceImpl;

public class ConversationForActionTest extends AbstractCmmnCaseTestCase {
	{
		super.isJpa = true;
	}

	public ConversationForActionTest() {
		super(true, true, "org.jbpm.persistence.jpa");
	}

	@Test
	public void testDirectRequest() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestDirectly(service);
		// Then
		List<ConversationActSummary> directRequests = service.getPendingRequests("Spielman");
		assertEquals(1, directRequests.size());
		ConversationActSummary request = directRequests.get(0);
		assertEquals(Timestamp.valueOf("2014-09-14 14:22:00.0"), request.getDateOfCommencement());
		assertEquals(Timestamp.valueOf("2015-09-14 14:22:00.0"), request.getDateOfCompletion());
		Map<String, Object> inputMap = service.getContentMap(request.getConversationId(), request.getInputContentId());
		assertEquals(4000, inputMap.get("numberOfBricks"));
		Map<String, Object> outputMap = service.getContentMap(request.getConversationId(), request.getOutputContentId());
		assertEquals(10, outputMap.get("numberOfWalls"));
	}

	@Test
	public void testPromise() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestDirectly(service);
		List<ConversationActSummary> directRequests = service.getPendingRequests("Spielman");
		ConversationActSummary request = directRequests.get(0);
		// When
		service.promise("Spielman", request.getActId(), "Anytime");
		// Then
		List<ConversationActSummary> spielmansInbox = service.getPendingRequests("Spielman");
		assertEquals(0, spielmansInbox.size());
		List<ConversationActSummary> ampiesInbox = service.getPendingRequests("Ampie");
		assertEquals(1, ampiesInbox.size());
		ConversationActSummary promise = ampiesInbox.get(0);
		assertEquals(Timestamp.valueOf("2014-09-14 14:22:00.0"), promise.getDateOfCommencement());
		assertEquals(Timestamp.valueOf("2015-09-14 14:22:00.0"), promise.getDateOfCompletion());
		Map<String, Object> inputMap = service.getContentMap(promise.getConversationId(), promise.getInputContentId());
		assertEquals(4000, inputMap.get("numberOfBricks"));
		Map<String, Object> outputMap = service.getContentMap(promise.getConversationId(), promise.getOutputContentId());
		assertEquals(10, outputMap.get("numberOfWalls"));
	}

	@Test
	public void testAccept() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestDirectly(service);
		List<ConversationActSummary> directRequests = service.getPendingRequests("Spielman");
		ConversationActSummary request = directRequests.get(0);
		service.promise("Spielman", request.getActId(), "Anytime");
		List<ConversationActSummary> ampiesInbox = service.getPendingRequests("Ampie");
		ConversationActSummary promise = ampiesInbox.get(0);
		// When
		service.accept("Ampie", promise.getActId(), "Thanks");
		// Then
		List<ConversationActSummary> spielmansInbox = service.getPendingRequests("Spielman");
		assertEquals(1, spielmansInbox.size());
		ConversationActSummary accept = spielmansInbox.get(0);
		assertEquals(Timestamp.valueOf("2014-09-14 14:22:00.0"), accept.getDateOfCommencement());
		assertEquals(Timestamp.valueOf("2015-09-14 14:22:00.0"), accept.getDateOfCompletion());
		Map<String, Object> inputMap = service.getContentMap(accept.getConversationId(), accept.getInputContentId());
		assertEquals(4000, inputMap.get("numberOfBricks"));
		Map<String, Object> outputMap = service.getContentMap(accept.getConversationId(), accept.getOutputContentId());
		assertEquals(10, outputMap.get("numberOfWalls"));
		List<TaskSummary> tasksOwned = getTaskService().getTasksOwned("Spielman", "en-UK");
		assertEquals(1, tasksOwned.size());
	}

	private void requestDirectly(ConversationForActionServiceImpl service) {
		ConversationForActionImpl cfa = new ConversationForActionImpl();
		cfa.setName("Build me a house");
		TaskDataImpl taskData = new TaskDataImpl();
		taskData.setDeploymentId((String) getRuntimeEngine().getKieSession().getEnvironment().get("deploymentId"));
		cfa.setTaskData(taskData);
		NegotiationStep step = new NegotiationStep();
		step.setComment("Please");
		step.setDateOfCommencement(Timestamp.valueOf("2014-09-14 14:22:00.0"));
		step.setDateOfCompletion(Timestamp.valueOf("2015-09-14 14:22:00.0"));
		step.setInput(Collections.singletonMap("numberOfBricks", (Object) 4000));
		step.setOutput(Collections.singletonMap("numberOfWalls", (Object) 10));
		// When
		service.requestDirectly("Ampie", "Spielman", cfa, step);
	}

	protected ConversationForActionServiceImpl createConversationService() {
		createRuntimeManager();
		ConversationForActionServiceImpl service = new ConversationForActionServiceImpl();
		service.setTaskService(getTaskService());
		return service;
	}

	@Override
	protected Class[] getClasses() {
		return new Class[0];
	}
}
