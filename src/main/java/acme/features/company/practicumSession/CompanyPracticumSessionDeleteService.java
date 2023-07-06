
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		PracticumSession ps;

		ps = this.repository.findOnePracticumSessionById(super.getRequest().getData("id", int.class));

		status = ps != null && super.getRequest().getPrincipal().hasRole(ps.getPracticum().getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
	}
	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
		super.getResponse().setData(tuple);
	}
}
