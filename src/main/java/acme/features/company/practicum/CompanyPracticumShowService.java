
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

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
		Practicum practicum;

		practicum = this.repository.findPracticumById(super.getRequest().getData("id", int.class));

		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;
		final Collection<PracticumSession> sessions;
		choices = SelectChoices.from(this.repository.findAllCourses(), "title", object.getCourse());

		sessions = this.repository.findManyPracticumSessionsByPracticumId(object.getId());
		final Double estimatedTime = object.estimatedTime(sessions);

		tuple = super.unbind(object, "code", "title", "anAbstract", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTime", estimatedTime);

		super.getResponse().setData(tuple);
	}

}
