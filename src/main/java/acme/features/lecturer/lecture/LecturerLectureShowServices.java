
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowServices extends AbstractService<Lecturer, Lecture> {

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
		int id;
		Lecture lecture;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(id);
		status = lecture != null && super.getRequest().getPrincipal().hasRole(lecture.getLecturer());

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
	public void unbind(final Lecture object) {
		assert object != null;

		final SelectChoices choices;
		final SelectChoices activityTypes;
		Tuple tuple;

		activityTypes = SelectChoices.from(ActivityType.class, object.getActivityType());
		tuple = super.unbind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");
		tuple.put("lectureId", object.getId());
		tuple.put("activityTypes", activityTypes);

		super.getResponse().setData(tuple);
	}

}
