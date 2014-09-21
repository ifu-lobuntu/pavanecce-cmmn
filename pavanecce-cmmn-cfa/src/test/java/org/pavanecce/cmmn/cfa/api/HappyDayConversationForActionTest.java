package org.pavanecce.cmmn.cfa.api;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jbpm.cmmn.test.AbstractCmmnCaseTestCase;
import org.jbpm.services.task.impl.model.TaskDataImpl;
import org.junit.Test;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.pavanecce.cmmn.cfa.impl.ConversationForActionImpl;
import org.pavanecce.cmmn.cfa.impl.ConversationForActionServiceImpl;

public class HappyDayConversationForActionTest extends AbstractCmmnCaseTestCase {
	{
		super.isJpa = true;
	}

	public HappyDayConversationForActionTest() {
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
		ConversationActSummary promise = requestAndPromise(service);
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
		List<ConversationForActionSummary> ownedCommitments = service.getOwnedCommitments("Spielman");
		assertEquals(1, ownedCommitments.size());
		ConversationForAction cfa = (ConversationForAction) getTaskService().getTaskById(ownedCommitments.get(0).getId());
		assertEquals(4000, service.getContentMap(cfa.getId(), cfa.getCommitment().getInputContentId()).get("numberOfBricks"));
		assertEquals(10, service.getContentMap(cfa.getId(), cfa.getCommitment().getOutputContentId()).get("numberOfWalls"));
		List<ConversationForActionSummary> initiatedCommitments = service.getInitiatedCommitments("Ampie");
		assertEquals(1, initiatedCommitments.size());
	}

	protected ConversationActSummary requestAndPromise(ConversationForActionServiceImpl service) {
		requestDirectly(service);
		List<ConversationActSummary> directRequests = service.getPendingRequests("Spielman");
		ConversationActSummary request = directRequests.get(0);
		service.promise("Spielman", request.getActId(), "Anytime");
		List<ConversationActSummary> ampiesInbox = service.getPendingRequests("Ampie");
		ConversationActSummary promise = ampiesInbox.get(0);
		return promise;
	}

	@Test
	public void testStart() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestPromiseAndAccept(service);
		ConversationActSummary accept = service.getPendingRequests("Spielman").get(0);
		service.start("Spielman", accept.getActId(), "I'm starting");
		// Then
		List<TaskSummary> spielmansInbox = getTaskService().getTasksOwned("Spielman", "en-UK");
		assertEquals(1, spielmansInbox.size());
		TaskSummary task = spielmansInbox.get(0);
		assertEquals(Status.InProgress, task.getStatus());
	}
	@Test
	public void testAssertComplete() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestToStart(service);
		List<TaskSummary> spielmansInbox = getTaskService().getTasksOwned("Spielman", "en-UK");
		TaskSummary task = spielmansInbox.get(0);
		ConversationForAction cfa = (ConversationForAction) getTaskService().getTaskById(task.getId());
		// When
		service.assertComplete("Spielman", cfa.getCurrentAct().getId(),Collections.singletonMap("numberOfWalls", (Object) 9),  "Finished!");
		// Then
		List<ConversationActSummary> ampiesInbox = service.getPendingRequests("Ampie");
		assertEquals(1, ampiesInbox.size());
		ConversationActSummary assertComplete = ampiesInbox.get(0);
		assertEquals(ConversationActKind.ASSERT,assertComplete.getKind());
		assertEquals(9, service.getContentMap(cfa.getId(), assertComplete.getOutputContentId()).get("numberOfWalls"));
		assertEquals(9, service.getContentMap(cfa.getId(), cfa.getTaskData().getOutputContentId()).get("numberOfWalls"));
	}
	@Test
	public void testDeclareAdequate() {
		// Given
		ConversationForActionServiceImpl service = createConversationService();
		requestToAssertComplete(service);
		List<ConversationActSummary> ampiesInbox = service.getPendingRequests("Ampie");
		//When
		service.declareAdequate("Ampie", ampiesInbox .get(0).getActId(), "Well done");
		// Then
		long taskId = ampiesInbox .get(0).getConversationId();
		assertEquals(1, getTaskService().getCompletedTaskByUserId("Spielman"));
		assertEquals(0, service.getInitiatedCommitments("Ampie").size());
		assertEquals(0, service.getOwnedCommitments("Spielman").size());
		assertEquals(0, getTaskService().getTasksOwned("Spielman", "en-UK").size());
		ConversationActSummary assertComplete = ampiesInbox.get(0);
		ConversationForAction cfa = (ConversationForAction) getTaskService().getTaskById(assertComplete.getConversationId());
		assertEquals(9, service.getContentMap(taskId, cfa.getTaskData().getOutputContentId()).get("numberOfWalls"));
	}

	protected void requestToAssertComplete(ConversationForActionServiceImpl service) {
		requestToStart(service);
		List<TaskSummary> spielmansInbox = getTaskService().getTasksOwned("Spielman", "en-UK");
		TaskSummary task = spielmansInbox.get(0);
		ConversationForAction cfa1 = (ConversationForAction) getTaskService().getTaskById(task.getId());
		// When
		service.assertComplete("Spielman", cfa1.getCurrentAct().getId(),Collections.singletonMap("numberOfWalls", (Object) 9),  "Finished!");
	}

	protected void requestToStart(ConversationForActionServiceImpl service) {
		requestPromiseAndAccept(service);
		ConversationActSummary accept = service.getPendingRequests("Spielman").get(0);
		service.start("Spielman", accept.getActId(), "I'm starting");
	}

	protected void requestPromiseAndAccept(ConversationForActionServiceImpl service) {
		ConversationActSummary promise = requestAndPromise(service);
		service.accept("Ampie", promise.getActId(), "Thanks");
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

	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getClasses() {
		return new Class[0];
	}
}