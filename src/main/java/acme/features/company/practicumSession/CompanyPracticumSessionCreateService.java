
package acme.features.company.practicumSession;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

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
		final Practicum practicum = this.repository.findOnePracticumById(super.getRequest().getData("practicumId", int.class));
		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.getDraftMode();

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		PracticumSession object;

		object = new PracticumSession();
		super.getBuffer().setData(object);

	}
	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		final int practicumId = super.getRequest().getData("practicumId", int.class);
		final Practicum practicum = this.repository.findOnePracticumById(practicumId);
		super.bind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
		object.setPracticum(practicum);
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!(super.getBuffer().getErrors().hasErrors("initialDate") || super.getBuffer().getErrors().hasErrors("finalDate"))) {
			final boolean finishDateIsAfter = object.getFinalDate().after(object.getInitialDate());
			final Date currentDate = MomentHelper.getCurrentMoment();
			final Long durationSinceCurrentDate = MomentHelper.computeDuration(currentDate, object.getInitialDate()).toDays();
			final boolean startDateIsOneDayAhead = durationSinceCurrentDate.doubleValue() >= 1.;
			super.state(finishDateIsAfter, "finalDate", "company.session.form.error.finishDate");

		}

	}
	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "anAbstract", "initialDate", "finalDate", "link");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());

		super.getResponse().setData(tuple);
	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
