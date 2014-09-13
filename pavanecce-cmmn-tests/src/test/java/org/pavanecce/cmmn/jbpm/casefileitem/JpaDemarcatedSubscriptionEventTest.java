package org.pavanecce.cmmn.jbpm.casefileitem;

import org.jbpm.cmmn.instance.impl.CaseInstanceImpl;
import org.jbpm.cmmn.instance.subscription.impl.AbstractDurableSubscriptionManager;
import org.jbpm.cmmn.instance.subscription.impl.DemarcatedSubscriptionContext;
import org.junit.Test;

public class JpaDemarcatedSubscriptionEventTest extends JpaPersistentSubscriptionEventTest {
	@Override
	protected AbstractDurableSubscriptionManager<?, ?> getSubscriptionManager() {
		return null;
	}

	@Override
	protected void maybeStartSubscription() {
		getPersistence().start();
		DemarcatedSubscriptionContext.activateSubscriptionsFrom((CaseInstanceImpl) getRuntimeEngine().getKieSession().getProcessInstance(caseInstance.getId()));
	}

	@Override
	protected void endSubscription() {
		DemarcatedSubscriptionContext.deactiveSubscriptions();
	}

	@Test
	public void testCreationOfObjectInCollectionFileItem() throws Exception {
		super.testCreationOfObjectInCollectionFileItem();
	}
}
