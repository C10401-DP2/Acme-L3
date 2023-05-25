
package acme.features.company.practicumSession;

import java.util.Calendar;
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
		int practicumId;
		Practicum practicum;
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final PracticumSession object;
		int practicumId;
		Practicum practicum;
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		object = new PracticumSession();
		object.setPracticum(practicum);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;
		super.bind(object, "title", "anAbstract", "initialDate", "finalDate", "link", "addendum");
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
		if (!super.getBuffer().getErrors().hasErrors("addendum")) {
			final Boolean addendum = object.getAddendum();
			final Integer practicumId = object.getPracticum().getId();
			super.state(addendum && this.repository.findManyPracticumSessionsByPracticumIdAddendum(practicumId).size() == 1, "addendum", "company.sessionPracticum.form.error.justOneAddendum");
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
		tuple = super.unbind(object, "title", "anAbstract", "initialDate", "finalDate", "link", "addendum");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}

	public static Date plusOneWeek(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

}
