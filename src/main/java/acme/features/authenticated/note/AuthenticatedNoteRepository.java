
package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.note.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("SELECT b FROM Note b where b.instMoment > :limit")
	Collection<Note> listing(Date limit);

	@Query("SELECT b FROM Note b")
	Collection<Note> findAllBulletins();

	@Query("SELECT b FROM Note b WHERE b.id = :id")
	Note findNoteById(int id);
}
