
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.peep.Peep;
import acme.framework.repositories.AbstractRepository;

public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p where j.id = :id")
	Peep findOnePeepById(int id);

	@Query("select p from Peep p")
	Collection<Peep> findAllPeeps();

}
