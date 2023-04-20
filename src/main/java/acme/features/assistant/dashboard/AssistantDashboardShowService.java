/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.dashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int assistantId;
		AssistantDashboard dashboard;
		Map<ActivityType, Integer> tutorialCount;
		int tutorialSessionsByAssistantId;
		int tutorialsByAssistantId;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();

		tutorialCount = this.repository.numberOfTutorialsByActivityType();
		tutorialSessionsByAssistantId = this.repository.countOfSessionsByAssistantId(assistantId);
		tutorialsByAssistantId = this.repository.countOfTutorialsByAssistantId(assistantId);

		dashboard = new AssistantDashboard();

		dashboard.setTotalTutorials(tutorialCount);
		dashboard.setAverageTimeTutorialSessions(this.repository.averageTimeOfSessionsByAssistantId(assistantId));
		dashboard.setStandardDesviationTimeTutorialSessions(this.repository.deviationTimeOfSessionsByAssistantId(assistantId, dashboard.getAverageTimeTutorialSessions(), tutorialSessionsByAssistantId));
		dashboard.setMinTimeTutorialSessions(this.repository.minimumTimeOfSessionsByAssistantId(assistantId));
		dashboard.setMaxTimeTutorialSessions(this.repository.maximumTimeOfSessionsByAssistantId(assistantId));
		dashboard.setAverageTimeTutorials(this.repository.averageTimeOfTutorialsByAssistantId(assistantId));
		dashboard.setStandardDesviationTimeTutorials(this.repository.deviationTimeOfTutorialsByAssistantId(assistantId, dashboard.getAverageTimeTutorials(), tutorialsByAssistantId));
		dashboard.setMinTimeTutorials(this.repository.minimumTimeOfTutorialsByAssistantId(assistantId));
		dashboard.setMaxTimeTutorials(this.repository.maximumTimeOfTutorialsByAssistantId(assistantId));

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalTutorials", "averageTimeTutorialSessions", "standardDesviationTimeTutorialSessions", "minTimeTutorialSessions", "maxTimeTutorialSessions", "averageTimeTutorials", "standardDesviationTimeTutorials",
			"minTimeTutorials", "maxTimeTutorials");

		super.getResponse().setData(tuple);
	}

}
