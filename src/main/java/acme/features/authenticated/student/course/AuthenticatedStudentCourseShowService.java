
package acme.features.authenticated.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentCourseShowService extends AbstractService<Student, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuhtenticatedStudentCourseRepository repository;

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

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple = null;

		tuple = super.unbind(object, "course");
		tuple.put("title", object.getLecture().getTitle());
		tuple.put("learningTime", object.getLecture().getLearningTime());
		tuple.put("body", object.getLecture().getBody());
		tuple.put("lecturerUsername", object.getLecture().getLecturer().getUserAccount().getUsername());
		tuple.put("almaMater", object.getLecture().getLecturer().getAlmaMater());
		tuple.put("resume", object.getLecture().getLecturer().getResume());
		tuple.put("qualifications", object.getLecture().getLecturer().getQualifications());
		tuple.put("link", object.getLecture().getLecturer().getLink());

		super.getResponse().setData(tuple);
	}

}
