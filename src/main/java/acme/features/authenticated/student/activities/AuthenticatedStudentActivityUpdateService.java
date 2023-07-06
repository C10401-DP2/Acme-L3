
package acme.features.authenticated.student.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActType;
import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentActivityUpdateService extends AbstractService<Student, Activity> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentActivitiesRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final Enrolment enrolment;
		int id1;
		int id;

		Activity object;
		id1 = super.getRequest().getPrincipal().getAccountId();

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);
		enrolment = object.getEnrolment();

		status = enrolment.getStudent().getUserAccount().getId() == id1 && enrolment.getDraftMode() == true;
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstrat", "aType", "link", "initialDate", "finalDate");

	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
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

		SelectChoices choices1;

		choices1 = SelectChoices.from(ActType.class, object.getAType());

		tuple = super.unbind(object, "title", "abstrat", "aType", "link", "initialDate", "finalDate");
		tuple.put("draftMode", object.getEnrolment().getDraftMode());
		tuple.put("activities", choices1);

		super.getResponse().setData(tuple);
	}

}
