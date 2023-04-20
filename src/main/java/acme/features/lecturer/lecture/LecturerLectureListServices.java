
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service

public class LecturerLectureListServices extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Lecturer object;

		courseId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findOneLecturerByCourseId(courseId);
		status = super.getRequest().getPrincipal().getUsername().equals(object.getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Collection<Lecture> objects;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyLecturesByCourseId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int id;
		final Tuple tuple;
		Course course;

		id = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(id);
		tuple = super.unbind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");
		tuple.put("lectureId", object.getId());
		tuple.put("course", course);
		tuple.put("courseDraftMode", course.getDraftMode());
		tuple.put("lecturer", object.getLecturer().getUserAccount().getUsername());
		super.getResponse().setData(tuple);
	}
}
