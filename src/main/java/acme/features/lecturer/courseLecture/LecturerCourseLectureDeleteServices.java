
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
public class LecturerCourseLectureDeleteServices extends AbstractService<Lecturer, CourseLecture> {

	@Autowired
	protected LecturerCourseLectureRepository repository;


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
		Course course;

		courseId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null && course.getDraftMode() && super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
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
		final CourseLecture courseLecture;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findOneLecturerById(lecturerId);
		courseId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findOneCourseById(courseId);
		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object.setCourse(course);
		object.setLecture(lecture);

		super.bind(object, "courseLecture");

	}

	@Override
	public void validate(final CourseLecture object) {
		assert object != null;
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;

		int courseId, lectureId;

		Collection<CourseLecture> courseLectures;

		courseId = super.getRequest().getData("masterId", int.class);
		lectureId = super.getRequest().getData("lecture", int.class);
		courseLectures = this.repository.findCourseLectureByIds(courseId, lectureId);

		this.repository.deleteAll(courseLectures);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		int lecturerId;
		Collection<Lecture> courseLectures;
		final SelectChoices choices;
		final Tuple tuple;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courseLectures = this.repository.findLecturesOfCourseId(object.getCourse().getId());
		choices = SelectChoices.from(courseLectures, "title", object.getLecture());

		tuple = super.unbind(object, "course");
		tuple.put("masterId", object.getCourse().getId());
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		super.getResponse().setData(tuple);
	}
}
