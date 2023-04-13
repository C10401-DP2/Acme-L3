
package acme.features.authenticated.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected AuthenticatedStudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		int StudentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		StudentId = super.getRequest().getPrincipal().getActiveRoleId();

		status = super.getRequest().getPrincipal().hasRole(Student.class) && StudentId == enrolment.getStudent().getId() && enrolment != null && enrolment.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "motivation", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.findCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());

		super.getResponse().setData(tuple);
	}

}
