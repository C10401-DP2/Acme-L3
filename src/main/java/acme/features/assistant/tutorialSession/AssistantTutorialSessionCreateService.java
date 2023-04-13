/*
 * EmployerTutorialSessionCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialsession.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && tutorial.getDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);

		object = new TutorialSession();
		object.setTitle("");
		object.setAnAbstract("");
		object.setType(ActivityType.THEORY);
		object.setInitialDate(MomentHelper.getCurrentMoment());
		object.setFinalDate(MomentHelper.getCurrentMoment());
		object.setLink("");
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		super.bind(object, "title", "anAbstract", "type", "initialDate", "finalDate", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
			boolean inititalDateError;

			inititalDateError = MomentHelper.isAfter(object.getInitialDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.DAYS));

			super.state(inititalDateError, "initialDate", "assistant.tutorial-session.form.error.at-least-one-day-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateErrorDuration;

			finalDateErrorDuration = MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), 1l, ChronoUnit.HOURS);

			if (finalDateErrorDuration)
				finalDateErrorDuration = !MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), (long) 5 * 3600 + 1, ChronoUnit.SECONDS);

			super.state(finalDateErrorDuration, "finalDate", "assistant.tutorial-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateError;

			finalDateError = MomentHelper.isBefore(object.getInitialDate(), object.getFinalDate());

			super.state(finalDateError, "finalDate", "assistant.tutorial-session.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("type"))
			super.state(!object.getType().equals(ActivityType.BALANCED), "type", "assistant.tutorial-session.form.error.type-balanced");

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		int masterId;
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(ActivityType.class, object.getType());

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "anAbstract", "type", "initialDate", "finalDate", "link");
		tuple.put("masterId", masterId);
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

}
