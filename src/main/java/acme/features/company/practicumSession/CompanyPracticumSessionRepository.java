
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("SELECT s FROM Practicum s WHERE s.id= :id")
	Practicum findOnePracticumById(int id);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :id")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumId(int id);

	@Query("SELECT s FROM PracticumSession s WHERE s.id= :id")
	PracticumSession findOnePracticumSessionById(int id);

	@Query("select s.practicum from PracticumSession s where s.id = :id")
	Practicum findOnePracticumBySessionPracticumId(int id);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :id AND s.addendum = true")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumIdAddendum(int id);

}
