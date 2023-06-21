
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListMineServices extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().getActiveRole().equals(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Principal principal;
		final Collection<Lecture> objects;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyLectureByLecturer(principal.getActiveRoleId());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "anAbstract", "learningTime", "body", "activityType", "link", "draftMode");

		super.getResponse().setData(tuple);
	}
}
