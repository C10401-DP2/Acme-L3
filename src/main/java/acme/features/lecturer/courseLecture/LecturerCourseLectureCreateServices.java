
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreateServices extends AbstractService<Lecturer, CourseLecture> {

	@Autowired
	protected LecturerCourseLectureRepository repository;


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
		final int lecturerId, courseId;
		final Lecturer lecturer;
		CourseLecture object;
		final Course course;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findOneLecturerById(lecturerId);
		courseId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(courseId);

		object = new CourseLecture();
		object.setLecture(null);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;

		int lecturerId, courseId, lectureId;
		Lecturer lecturer;
		Course course;
		final Lecture lecture;

		super.bind(object, "courseLecture");

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findOneLecturerById(lecturerId);
		courseId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object.setLecture(lecture);
	}

	@Override
	public void validate(final CourseLecture object) {
		assert object != null;
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		int lecturerId;
		Collection<Lecture> lectures;
		final SelectChoices choices;
		final Tuple tuple;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lectures = this.repository.findAllLecturesOfLecturerIdAvailables(lecturerId);
		choices = SelectChoices.from(lectures, "title", object.getLecture());

		tuple = super.unbind(object, "course");
		tuple.put("masterId", object.getCourse().getId());
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		super.getResponse().setData(tuple);
	}
}
