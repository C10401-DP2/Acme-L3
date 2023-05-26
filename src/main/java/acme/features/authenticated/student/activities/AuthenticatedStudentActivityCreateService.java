
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
public class AuthenticatedStudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentActivitiesRepository repository;

	// AbstractService<Student, Activity> ---------------------------


	@Override
	public void authorise() {

		//		boolean status;
		//
		//		status = super.getRequest().getPrincipal().hasRole(Student.class);
		//		super.getResponse().setAuthorised(status);
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {

		//		boolean status;
		//
		//		status = super.getRequest().hasData("enrolmentId", int.class);
		//		System.out.println(status);
		//		super.getResponse().setChecked(status);
		super.getResponse().setChecked(true);

	}

	@Override
	public void load() {
		Activity object;

		object = new Activity();

		final Integer enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(enrolmentId);
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstrat", "link", "initialDate", "finalDate");
		final ActType aType;
		aType = super.getRequest().getData("aType", ActType.class);

		object.setAType(aType);

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

		tuple = super.unbind(object, "title", "abstrat", "link", "initialDate", "finalDate");
		tuple.put("aType", choices1.getSelected().getKey());
		tuple.put("activities", choices1);
		tuple.put("enrolmentId", super.getRequest().getData("enrolmentId", int.class));

		super.getResponse().setData(tuple);
	}

}
