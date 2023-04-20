
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Assistant assistant;

		masterId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && //
			tutorial.getDraftMode() && //
			super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && //
			tutorial.getAssistant().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
	public void bind(final Tutorial object) {
		assert object != null;

		int courseId;
		final int code;
		Tutorial sameTuto;
		Course course;
		int id;

		id = super.getRequest().getData("id", int.class);
		sameTuto = this.repository.findOneTutorialById(id);
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "title", "anAbstract", "goals", "draftMode");
		object.setCourse(course);
		object.setCode(sameTuto.getCode());
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "title", "anAbstract", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
