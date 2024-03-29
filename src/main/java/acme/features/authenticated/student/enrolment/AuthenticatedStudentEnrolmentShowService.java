
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
public class AuthenticatedStudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		boolean status;
		Enrolment object;
		Student student;
		int id;
		int id1;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);
		id1 = super.getRequest().getPrincipal().getAccountId();

		student = this.repository.findStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		status = super.getRequest().getPrincipal().hasRole(Student.class) && object.getStudent().equals(student) && object.getStudent().getUserAccount().getId() == id1;

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
	public void unbind(final Enrolment object) {
		assert object != null;
		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.findCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple = null;

		tuple = super.unbind(object, "code", "motivation", "goals", "creditCardNumber", "holder");
		final Integer totalTime = object.totalTime(this.repository.findAllActivitiesOfEnrolment(object.getId()));
		tuple.put("totalTime", totalTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("draftMode", object.getDraftMode());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
