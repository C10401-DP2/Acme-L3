
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courselecture.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteServices extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		status = lecture != null && lecture.getDraftMode() && super.getRequest().getPrincipal().hasRole(lecture.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		Collection<CourseLecture> courseLectures;

		courseLectures = this.repository.findManyCourseLectureByLectureId(object.getId());

		this.repository.deleteAll(courseLectures);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");

		super.getResponse().setData(tuple);
	}
}
