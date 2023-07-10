
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
public class CompanyPracticumSessionCreateAddendumService extends AbstractService<Company, PracticumSession> {

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
		PracticumSession addendumSession;
		practicumId = super.getRequest().getData("practicumId", int.class);
		addendumSession = this.repository.findOneAddendumSessionByPracticumId(practicumId);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = addendumSession == null && practicum != null && !practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
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
		object.setTitle("");
		object.setAnAbstract("");
		object.setInitialDate(MomentHelper.getCurrentMoment());
		object.setInitialDate(MomentHelper.getCurrentMoment());
		object.setLink("");
		object.setAddendum(true);
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

		Date date;

		if (!super.getBuffer().getErrors().hasErrors("initialDate") && !super.getBuffer().getErrors().hasErrors("finalDate")) {

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
		tuple.put("confirmation", true);
		super.getResponse().setData(tuple);
	}

	public static Date plusOneWeek(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

}
