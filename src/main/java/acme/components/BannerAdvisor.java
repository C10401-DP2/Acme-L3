
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;
import acme.features.administrator.banner.AdministratorBannerRepository;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected AdministratorBannerRepository repo;


	@ModelAttribute("banner")
	public Banner getRandomBanner() {
		return this.repo.getActiveRandomBanner();
	}

}
