
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.CourseType;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishServices extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int courseId;
		Course course;
		Lecturer lecturer;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		lecturer = course == null ? null : course.getLecturer();
		final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(courseId);
		status = course != null && course.getDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer) && !lectures.isEmpty();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "anAbstract", "retailPrice", "courseType", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCodeAndDistinctId(object.getCode(), object.getId());

			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("courseType")) {
			final Collection<Lecture> lectures = this.repository.findManyLecturesByCourseId(object.getId());
			super.state(object.courseType(lectures).equals(CourseType.THEORY), "courseType", "lecturer.course.form.error.courseType");
		}
		if (!super.getBuffer().getErrors().hasErrors("price")) {
			Configuration config;
			config = this.repository.findConfiguration();

			super.state(config.getAcceptedCurrency().contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.course.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getRetailPrice().getAmount() >= 0., "retailPrice", "lecturer.course.negative-price");

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getRetailPrice().getAmount() <= 1000000, "reatilPrice", "lecturer.course.too-much-price");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "anAbstract", "retailPrice", "courseType", "link", "draftMode");
		super.getResponse().setData(tuple);
	}
}
