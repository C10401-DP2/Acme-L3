
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("SELECT o FROM Offer o WHERE o.id = :id")
	Offer findOfferById(int id);

	@Query("SELECT o FROM Offer o")
	Collection<Offer> findAllOffers();
}
