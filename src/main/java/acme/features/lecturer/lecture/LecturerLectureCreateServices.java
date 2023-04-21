
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityTypeLecture;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateServices extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecturer lecturer;
		Lecture object;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Lecture();
		object.setLecturer(lecturer);
		object.setActivityType(ActivityTypeLecture.HANDSON);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");
		object.setLecturer(lecturer);
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("learningTime"))
			super.state(object.getLearningTime() > 0, "learningTime", "lecturer.lecture.form.error.negative-learningTime");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		Lecturer lecturer;
		SelectChoices activityTypes;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		activityTypes = SelectChoices.from(ActivityTypeLecture.class, object.getActivityType());
		tuple = super.unbind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");
		tuple.put("lecturer", lecturer);
		tuple.put("activityTypes", activityTypes);

		super.getResponse().setData(tuple);

	}
}
