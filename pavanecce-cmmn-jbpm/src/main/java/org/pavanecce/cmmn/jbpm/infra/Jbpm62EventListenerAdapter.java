package org.pavanecce.cmmn.jbpm.infra;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

import org.jbpm.services.task.events.AfterTaskActivatedEvent;
import org.jbpm.services.task.events.AfterTaskAddedEvent;
import org.jbpm.services.task.events.AfterTaskClaimedEvent;
import org.jbpm.services.task.events.AfterTaskCompletedEvent;
import org.jbpm.services.task.events.AfterTaskDelegatedEvent;
import org.jbpm.services.task.events.AfterTaskExitedEvent;
import org.jbpm.services.task.events.AfterTaskFailedEvent;
import org.jbpm.services.task.events.AfterTaskForwardedEvent;
import org.jbpm.services.task.events.AfterTaskReleasedEvent;
import org.jbpm.services.task.events.AfterTaskResumedEvent;
import org.jbpm.services.task.events.AfterTaskSkippedEvent;
import org.jbpm.services.task.events.AfterTaskStartedEvent;
import org.jbpm.services.task.events.AfterTaskStoppedEvent;
import org.jbpm.services.task.events.AfterTaskSuspendedEvent;
import org.jbpm.services.task.events.BeforeTaskCompletedEvent;
import org.jbpm.services.task.wih.ExternalTaskEventListener;
import org.kie.api.task.model.Task;

public class Jbpm62EventListenerAdapter extends ExternalTaskEventListener implements CmmnTaskLifecycleEventListener {

	public Jbpm62EventListenerAdapter() {
		super();
	}

	public Map<Method, List<Annotation>> getObserverMethods() {
		Method[] methods = Jbpm62EventListenerAdapter.class.getMethods();
		Map<Method, List<Annotation>> observerMethods = new ConcurrentHashMap<Method, List<Annotation>>();
		for (Method m : methods) {
			if (m.getParameterAnnotations().length > 0) {
				Annotation[][] annotations = m.getParameterAnnotations();
				for (Annotation[] a : annotations) {
					for (Annotation b : a) {
						if (b.annotationType().getCanonicalName().equals(Observes.class.getCanonicalName())) {
							observerMethods.put(m, Arrays.asList(a));
						}
					}
				}
			}
		}
		return observerMethods;
	}
    @Override
    public void afterTaskReleasedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskReleasedEvent Task ti) {
        
    }

    @Override
    public void afterTaskResumedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskResumedEvent Task ti) {
        
    }

    @Override
    public void afterTaskSuspendedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskSuspendedEvent Task ti) {
        
    }

    @Override
    public void afterTaskForwardedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskForwardedEvent Task ti) {
        
    }

    @Override
    public void afterTaskDelegatedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskDelegatedEvent Task ti) {
        
    }


	@Override
	public void afterTaskAddedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskAddedEvent Task task) {

	}


	@Override
	public void afterTaskClaimedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskClaimedEvent Task task) {
	}

	@Override
	public void afterTaskStoppedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskStoppedEvent Task task) {
	}


	@Override
	public void afterTaskStartedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskStartedEvent Task task) {
	}

	@Override
	public void afterTaskFailedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskFailedEvent Task task) {
	}

	@Override
	public void afterTaskExitedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskExitedEvent Task task) {
	}

	@Override
	public void afterTaskSkippedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskSkippedEvent Task task) {
	}

	@Override
	public void afterTaskCompletedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskCompletedEvent Task task) {
	}

	@Override
	public void afterTaskActivatedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskActivatedEvent Task task) {
	}
	@Override
	public void beforeTaskAddedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @AfterTaskAddedEvent  Task t) {
	}

	@Override
	public void beforeTaskCompletedEvent(@Observes(notifyObserver=Reception.IF_EXISTS) @BeforeTaskCompletedEvent  Task task) { 
	}
	//CMMN

	@Override
	public void afterTaskReactivatedEvent(Task task) {
	}

	@Override
	public void afterTaskReenabledEvent(Task task) {
	}

	@Override
	public void afterTaskParentSuspendedEvent(Task task) {
	}

	@Override
	public void afterTaskParentResumedEvent(Task task) {
	}

	@Override
	public void afterTaskExitCriteriaEvent(Task task) {
	}

	@Override
	public void afterTaskStartedAutomaticallyEvent(Task task) {
	}
	@Override
	public void beforeTaskReactivatedEvent(Task task) {
	}

	@Override
	public void beforeTaskReenabledEvent(Task task) {
	}

	@Override
	public void beforeTaskParentSuspendedEvent(Task task) {
	}

	@Override
	public void beforeTaskParentResumedEvent(Task task) {
	}

	@Override
	public void beforeTaskExitCriteriaEvent(Task task) {
	}

	@Override
	public void beforeTaskStartedAutomaticallyEvent(Task task) {
	}


}