
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

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
		int practicumId;
		PracticumSession practicumSession;
		final Practicum practicum;
		Company company;

		practicumId = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findOnePracticumSessionById(practicumId);
		practicum = practicumSession == null ? null : practicumSession.getPracticum();
		company = practicum == null ? null : practicum.getCompany();
		status = practicumSession != null && //
			practicum != null && //
			super.getRequest().getPrincipal().hasRole(company) && //
			practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;
		Practicum practicum;
		boolean draftMode;

		practicum = object.getPracticum();
		draftMode = practicum.getDraftMode();

		tuple = super.unbind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
		tuple.put("practicumId", practicum.getId());
		tuple.put("draftMode", draftMode);
		tuple.put("practicum", practicum);
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}
}
