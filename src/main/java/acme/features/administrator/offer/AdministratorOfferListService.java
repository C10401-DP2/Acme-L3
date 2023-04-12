
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferListService extends AbstractService<Administrator, Offer> {

	// Internal state
	@Autowired
	protected AdministratorOfferRepository repository;


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
		Collection<Offer> offers;

		offers = this.repository.findAllOffers();
		super.getBuffer().setData(offers);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "moment", "heading", "summary", "price");
		super.getResponse().setData(tuple);
	}
}
