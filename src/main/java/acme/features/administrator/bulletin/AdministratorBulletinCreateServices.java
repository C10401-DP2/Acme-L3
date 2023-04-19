
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateServices extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Bulletin object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Bulletin();
		object.setMoment(moment);
		object.setTitle("");
		object.setMessage("");
		object.setIsCritical(false);
		object.setLink("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;

		super.bind(object, "title", "message", "isCritical", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
		boolean confirmation;
		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		final Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "message", "isCritical", "link");
		tuple.put("confirmation", true);
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
