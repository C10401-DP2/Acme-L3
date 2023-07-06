
package acme.testing.student.activities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.activity.Activity;
import acme.framework.repositories.AbstractRepository;

public interface StudentActivitiesTestRepository extends AbstractRepository {

	@Query("SELECT a FROM acme.entities.activity.Activity a JOIN a.enrolment e JOIN e.student s WHERE s.userAccount.username = :username")
	Collection<Activity> findManyActivitiesByStudentUsername(String username);

}
