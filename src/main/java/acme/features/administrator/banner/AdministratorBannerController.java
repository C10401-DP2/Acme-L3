
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerListServices	listService;

	@Autowired
	protected AdministratorBannerShowServices	showService;

	@Autowired
	protected AdministratorBannerCreateServices	createService;

	@Autowired
	protected AdministratorBannerUpdateServices	updateService;

	@Autowired
	protected AdministratorBannerDeleteServices	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
