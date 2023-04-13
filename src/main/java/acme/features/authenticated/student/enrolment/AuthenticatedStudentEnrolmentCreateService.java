
package acme.features.authenticated.student.enrolment;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentEnrolmentRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Enrolment object;
		Student student;

		object = new Enrolment();
		student = this.repository.findStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setStudent(student);
		object.setDraftMode(true);
		object.setMotivation("");
		object.setGoals("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "initialDate", "finalDate");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
			boolean inititalDateError;

			inititalDateError = MomentHelper.isBefore(object.getInitialDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.DAYS));

			super.state(inititalDateError, "initialDate", "assistant.tutorial-session.form.error.at-least-one-day-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateErrorDuration;

			finalDateErrorDuration = !MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), 1l, ChronoUnit.HOURS);

			if (!finalDateErrorDuration)
				finalDateErrorDuration = MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), (long) 5 * 3600 + 1, ChronoUnit.SECONDS);

			super.state(finalDateErrorDuration, "finalDate", "assistant.tutorial-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateError;

			finalDateError = MomentHelper.isBefore(object.getInitialDate(), object.getFinalDate());

			super.state(finalDateError, "finalDate", "assistant.tutorial-session.form.error.end-before-start");
		}
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
