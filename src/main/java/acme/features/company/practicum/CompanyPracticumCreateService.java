
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int userAccountId;
		Company company;

		userAccountId = super.getRequest().getPrincipal().getActiveRoleId();
		company = this.repository.findCompanyById(userAccountId);

		object = new Practicum();
		object.setTitle("");
		object.setAnAbstract("");
		object.setGoals("");
		object.setCode("");
		object.setDraftMode(true);
		object.setCompany(company);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		super.bind(object, "code", "title", "anAbstract", "goals", "draftMode");
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		object.setCourse(course);

	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;

			existing = this.repository.findPracticumByCode(object.getCode());
			super.state(existing == null, "code", "company.practicum.error.code.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("course")) {
			Course course;

			course = this.repository.findCourseByIdPublished(object.getCourse().getId());
			super.state(course == null, "course", "company.practicum.error.course.unpublished");
		}

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		Double estimatedTime;
		Collection<PracticumSession> sessions;
		choices = SelectChoices.from(this.repository.findAllCourses(), "title", object.getCourse());

		sessions = this.repository.findManyPracticumSessionsByPracticumId(object.getId());
		estimatedTime = 0.;
		if (!sessions.isEmpty())
			estimatedTime = object.estimatedTime(sessions);

		tuple = super.unbind(object, "code", "title", "anAbstract", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTime", estimatedTime);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
