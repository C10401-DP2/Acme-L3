
package acme.features.authenticated.student.activities;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int userAccountId;

		Collection<Activity> objects;
		userAccountId = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findAllActivitiesOfStudent(userAccountId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstrat", "aType", "initialDate", "finalDate");
		super.getResponse().setData(tuple);
	}

}
