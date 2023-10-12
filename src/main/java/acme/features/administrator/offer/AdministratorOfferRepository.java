
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("SELECT o FROM Offer o WHERE o.id = :id")
	Offer findOfferById(int id);

	@Query("SELECT o FROM Offer o")
	Collection<Offer> findAllOffers();

	@Query("SELECT a FROM Administrator a WHERE a.id = :id")
	Administrator findAdministratorById(int id);

	@Query("SELECT a FROM Configuration a")
	Configuration systemConfiguration();
}
