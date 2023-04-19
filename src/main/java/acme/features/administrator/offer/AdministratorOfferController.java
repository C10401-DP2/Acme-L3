
package acme.features.administrator.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorOfferController extends AbstractController<Administrator, Offer> {

	// Internal state

	@Autowired
	AdministratorOfferListService	listService;

	@Autowired
	AdministratorOfferShowService	showService;

	// Constructors


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
