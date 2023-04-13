
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorialsession.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	Collection<Tutorial> findManyTutorialsByAssistantId(int id);

	@Query("SELECT a FROM Assistant a WHERE a.id = :id")
	Assistant findOneAssistantById(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT s FROM TutorialSession s WHERE s.tutorial.id = :id")
	Collection<TutorialSession> findManySessionsByTutorialId(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.code = :code")
	Tutorial findOneTutorialByCode(String code);
}
