
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	// Internal state
	@Autowired
	protected AuthenticatedBulletinRepository repository;


	// Interface
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
		Collection<Bulletin> bulletins;

		bulletins = this.repository.findAllBulletins();
		super.getBuffer().setData(bulletins);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "message", "isCritical", "link");
		super.getResponse().setData(tuple);
	}

}
