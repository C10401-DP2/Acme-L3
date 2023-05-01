
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseListServices extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		Collection<Course> objects;

		objects = this.repository.findCourseByLecturerId(userAccountId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "retailPrice");

		super.getResponse().setData(tuple);
	}
}
