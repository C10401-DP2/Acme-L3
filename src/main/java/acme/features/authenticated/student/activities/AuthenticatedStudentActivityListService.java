
package acme.features.authenticated.student.activities;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentActivityListService extends AbstractService<Student, Activity> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentActivitiesRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("enrolmentId", int.class);

		super.getResponse().setChecked(status);

	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		final int id;
		Enrolment enrolment;
		id = super.getRequest().getPrincipal().getAccountId();
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		status = enrolment != null && enrolment.getStudent().getUserAccount().getId() == id;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Activity> objects;
		final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		objects = this.repository.findAllActivitiesOfEnrolment(enrolmentId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		final boolean prueba = false;
		Tuple tuple;
		final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);

		super.getResponse().setGlobal("draftMode", object.getEnrolment().getDraftMode());

		tuple = super.unbind(object, "title", "abstrat", "aType", "initialDate", "finalDate");
		super.getResponse().setGlobal("enrolmentId", enrolmentId);
		super.getResponse().setGlobal("prueba", prueba);
		super.getResponse().setData(tuple);

	}

}
