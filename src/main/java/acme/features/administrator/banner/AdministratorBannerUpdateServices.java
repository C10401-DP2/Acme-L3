
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateServices extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "initialDisplay", "finalDisplay", "image", "slogan", "documentLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initialDisplay")) {
			boolean initialDisplayError;

			if (object.getInitialDisplay() == null || object.getFinalDisplay() == null)
				initialDisplayError = true;
			else {
				final Date initialDisplay = super.getRequest().getData("initialDisplay", Date.class);
				final Date finalDisplay = super.getRequest().getData("finalDisplay", Date.class);
				initialDisplayError = initialDisplay.before(finalDisplay);
			}
			super.state(initialDisplayError, "initialDisplay", "administrator.banner.form.error-dates");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDisplay")) {
			boolean finalDisplayErrorDuration;

			if (object.getInitialDisplay() == null || object.getFinalDisplay() == null)
				finalDisplayErrorDuration = true;
			else
				finalDisplayErrorDuration = MomentHelper.isLongEnough(object.getInitialDisplay(), object.getFinalDisplay(), 1L, ChronoUnit.WEEKS);

			super.state(finalDisplayErrorDuration, "finalDisplay", "administrator.banner.form.error.duration");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "initialDisplay", "finalDisplay", "image", "slogan", "documentLink");

		super.getResponse().setData(tuple);
	}
}
