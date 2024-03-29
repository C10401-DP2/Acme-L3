
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

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
		final boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Practicum> objects;
		int companyId;
		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyPracticumsByCompanyId(companyId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		final Collection<PracticumSession> sessions;

		sessions = this.repository.findManyPracticumSessionsByPracticumId(object.getId());
		final Double estimatedTime = object.estimatedTime(sessions);
		tuple = super.unbind(object, "code", "title", "anAbstract", "goals");
		tuple.put("estimatedTime", estimatedTime);

		super.getResponse().setData(tuple);
	}

}
