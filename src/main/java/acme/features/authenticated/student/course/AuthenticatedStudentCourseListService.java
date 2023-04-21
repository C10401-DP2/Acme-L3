
package acme.features.authenticated.student.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentCourseListService extends AbstractService<Student, CourseLecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuhtenticatedStudentCourseRepository repository;

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

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Collection<Course> objects;

		objects = this.repository.findAllCourse();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "course");
		super.getResponse().setData(tuple);
	}

}
