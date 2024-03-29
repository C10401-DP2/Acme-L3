
package acme.features.company.practicumSession;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

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
		int id;
		Practicum practicum;

		id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumBySessionPracticumId(id);
		status = practicum != null && practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

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
		boolean confirmation;
		Date date;

		confirmation = object.getPracticum().getDraftMode() ? true : super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "company.sessionPracticum.form.error.confirmation");

		if (!super.getBuffer().getErrors().hasErrors("finalDate"))
			super.state(object.getInitialDate().before(object.getFinalDate()), "finalDate", "company.sessionPracticum.form.error.endAfterStart");

		if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
			date = CompanyPracticumSessionCreateService.plusOneWeek(MomentHelper.getCurrentMoment());
			super.state(object.getInitialDate().equals(date) || object.getInitialDate().after(date), "initialDate", "company.sessionPracticum.form.error.oneWeekAhead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			date = CompanyPracticumSessionCreateService.plusOneWeek(object.getInitialDate());
			super.state(object.getInitialDate().equals(date) || object.getFinalDate().after(date), "finalDate", "company.sessionPracticum.form.error.oneWeekLong");
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

		super.getResponse().setData(tuple);
	}

}
