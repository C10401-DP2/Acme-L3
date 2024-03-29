
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state
	@Autowired
	protected AdministratorOfferRepository repository;


	// Interface
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "moment", "heading", "summary", "initialDate", "finalDate", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateError;

			if (object.getInitialDate() == null || object.getFinalDate() == null)
				finalDateError = true;
			else
				finalDateError = MomentHelper.isBefore(object.getInitialDate(), object.getFinalDate());

			super.state(finalDateError, "finalDate", "administrator.offer.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateErrorDuration;

			if (object.getInitialDate() == null || object.getFinalDate() == null)
				finalDateErrorDuration = true;
			else
				finalDateErrorDuration = MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), 1L, ChronoUnit.WEEKS);

			super.state(finalDateErrorDuration, "finalDate", "administrator.offer.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
			boolean initialDateError;

			if (object.getInitialDate() == null)
				initialDateError = true;
			else
				initialDateError = MomentHelper.isLongEnough(object.getMoment(), object.getInitialDate(), 1L, ChronoUnit.DAYS);

			super.state(initialDateError, "initialDate", "administrator.offer.form.error.one-day-after");
		}
		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() >= 0, "price", "administrator.offer.form.error.price-positive-or-zero");

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(this.repository.systemConfiguration().getAcceptedCurrency().contains(object.getPrice().getCurrency()), "price", "administrator.offer.form.error.incorrect-currency");
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "moment", "heading", "summary", "initialDate", "finalDate", "price", "link");
		super.getResponse().setData(tuple);
	}
}
