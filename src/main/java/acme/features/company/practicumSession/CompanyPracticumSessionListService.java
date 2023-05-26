
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Practicum practicum;
		masterId = super.getRequest().getData("practicumId", int.class);

		practicum = this.repository.findOnePracticumById(masterId);
		status = practicum != null && //
			(!practicum.getDraftMode() || super.getRequest().getPrincipal().hasRole(practicum.getCompany()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		int practicumId;

		practicumId = super.getRequest().getData("practicumId", int.class);
		objects = this.repository.findManyPracticumSessionsByPracticumId(practicumId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;
		String addendumState;

		if (object.getAddendum())
			addendumState = "x";
		else
			addendumState = "";

		tuple = super.unbind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
		tuple.put("addendumState", addendumState);

		super.getResponse().setData(tuple);
	}
	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		int masterId;
		Practicum practicum;
		boolean existingAddendum;
		final boolean showCreate;
		boolean showAddendumCreate;

		masterId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		showCreate = practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		existingAddendum = this.repository.findOneAddendumSessionByPracticumId(masterId) != null ? false : true;
		showAddendumCreate = !practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && existingAddendum;

		super.getResponse().setGlobal("practicumId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("showAddendumCreate", showAddendumCreate);
	}

}
