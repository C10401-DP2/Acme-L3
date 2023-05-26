
package acme.features.authenticated.student.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActType;
import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentActivityShowService extends AbstractService<Student, Activity> {
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

		status = super.getRequest().getPrincipal().hasRole(Student.class) && enrolment.getStudent().getUserAccount().getId() == id1;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		enrolment = object.getEnrolment();
		super.getResponse().setGlobal("draftMode", enrolment.getDraftMode());
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices1;

		Tuple tuple = null;

		choices1 = SelectChoices.from(ActType.class, object.getAType());

		tuple = super.unbind(object, "title", "abstrat", "aType", "link", "initialDate", "finalDate");
		tuple.put("activities", choices1);
		tuple.put("draftMode", object.getEnrolment().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
