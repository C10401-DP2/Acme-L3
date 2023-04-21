
package acme.features.authenticated.student.activities;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentActivitiesRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void authorise() {

		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Activity object;
		final Enrolment enrolment;
		Student student;
		final int enrolmentId;

		object = new Activity();
		student = this.repository.findStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setTitle("");
		object.setAbstrat("");
		object.setAType(null);
		object.setInitialDate(null);
		object.setFinalDate(null);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		super.bind(object, "title", "abstrat", "aType", "link", "initialDate", "finalDate");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateErrorDuration;

			finalDateErrorDuration = !MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), 1l, ChronoUnit.HOURS);

			if (!finalDateErrorDuration)
				finalDateErrorDuration = MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), (long) 5 * 3600 + 1, ChronoUnit.SECONDS);

			super.state(finalDateErrorDuration, "finalDate", "assistant.tutorial-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateError;

			finalDateError = MomentHelper.isBefore(object.getInitialDate(), object.getFinalDate());

			super.state(finalDateError, "finalDate", "assistant.tutorial-session.form.error.end-before-start");
		}
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;
		Collection<Enrolment> enrolments;
		SelectChoices choices;
		SelectChoices choices1;

		enrolments = this.repository.findAllEnrolmentOfStudent(super.getRequest().getPrincipal().getActiveRoleId());
		choices = SelectChoices.from(enrolments, "code", object.getEnrolment());
		choices1 = SelectChoices.from(ActivityType.class, object.getAType());

		tuple = super.unbind(object, "title", "abstrat", "aType", "link", "initialDate", "finalDate");
		tuple.put("enrolment", choices.getSelected().getKey());
		tuple.put("enrolments", choices);
		tuple.put("activities", choices1);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
