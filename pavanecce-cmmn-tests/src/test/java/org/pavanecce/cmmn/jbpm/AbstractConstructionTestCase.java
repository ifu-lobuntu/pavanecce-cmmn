package org.pavanecce.cmmn.jbpm;

import org.jbpm.cmmn.instance.CaseInstance;
import org.jbpm.cmmn.test.AbstractCmmnCaseTestCase;
import org.pavanecce.cmmn.jbpm.ocm.OcmCaseFileItemSubscriptionInfo;
import org.pavanecce.cmmn.jbpm.ocm.OcmCaseSubscriptionInfo;

import test.cmmn.ConstructionCase;
import test.cmmn.House;
import test.cmmn.HousePlan;
import test.cmmn.RoofPlan;
import test.cmmn.RoomPlan;
import test.cmmn.Wall;
import test.cmmn.WallPlan;

public class AbstractConstructionTestCase extends AbstractCmmnCaseTestCase {
	protected HousePlan housePlan;
	protected House house;
	protected CaseInstance caseInstance;

	public AbstractConstructionTestCase() {
		super(true, true, "org.jbpm.persistence.jpa");
	}

	public AbstractConstructionTestCase(boolean setupDataSource, boolean sessionPersistence, String persistenceUnitName) {
		super(setupDataSource, sessionPersistence, persistenceUnitName);
	}

	public AbstractConstructionTestCase(boolean setupDataSource, boolean sessionPersistence) {
		super(setupDataSource, sessionPersistence);
	}

	@Override
	protected Class<?>[] getClasses() {
		return new Class<?>[] { ConstructionCase.class, HousePlan.class, House.class, Wall.class, WallPlan.class, RoofPlan.class,
				OcmCaseSubscriptionInfo.class, OcmCaseFileItemSubscriptionInfo.class, RoomPlan.class };
	}

}
