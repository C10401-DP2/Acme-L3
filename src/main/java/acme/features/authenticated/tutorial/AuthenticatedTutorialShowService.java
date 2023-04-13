
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

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
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && //
			!tutorial.getDraftMode() && //
			super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		final Collection<Course> courses = this.repository.findAllCourses();

		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "anAbstract", "goals", "draftMode", "assistant.supervisor", "assistant.expertiseFields", "assistant.resume", "assistant.link");

		tuple.put("course", choices.getSelected().getKey());
		tuple.put("choices", choices);

		super.getResponse().setData(tuple);
	}

}
