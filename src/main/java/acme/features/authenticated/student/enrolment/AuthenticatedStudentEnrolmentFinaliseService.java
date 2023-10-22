
package acme.features.authenticated.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activity.Activity;
import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

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
		final boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;
		int id1;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		final Collection<Activity> a = this.repository.findAllActivitiesOfEnrolment(enrolmentId);
		id1 = super.getRequest().getPrincipal().getAccountId();

		student = enrolment.getStudent();
		status = enrolment != null && enrolment.getDraftMode() && super.getRequest().getPrincipal().hasRole(student) && !a.isEmpty() && enrolment.getStudent().getUserAccount().getId() == id1;

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
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("creditCardNumber")) {
			String creditCardNumber;
			creditCardNumber = object.getCreditCardNumber();

			super.state(creditCardNumber.length() == 16, "creditCardNumber", "student.enrolment.error.lowerNibble.notValidNumber");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);

		final String creditCardNumber = super.getRequest().getData("creditCardNumber", String.class);
		object.setCreditCardNumber(creditCardNumber.substring(12, 16));

		final String holder = super.getRequest().getData("holder", String.class);
		object.setHolder(holder);

		this.repository.save(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "motivation", "goal", "holder", "creditCardNumber");
		object.setCourse(course);

	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.findCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "holder", "creditCardNumber", "draftMode");
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
