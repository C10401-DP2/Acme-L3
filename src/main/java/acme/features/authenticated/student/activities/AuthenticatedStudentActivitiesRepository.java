
package acme.features.authenticated.student.activities;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activity.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface AuthenticatedStudentActivitiesRepository extends AbstractRepository {

	@Query("SELECT a FROM Enrolment a Where a.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT a FROM Student a Where a.id = :id")
	Student findStudentById(int id);

	@Query("SELECT a FROM Enrolment a WHERE a.student.id = :id")
	Collection<Enrolment> findAllEnrolmentOfStudent(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	Collection<Activity> findAllActivitiesOfEnrolment(int id);

	@Query("SELECT a FROM acme.entities.activity.Activity a JOIN a.enrolment e JOIN e.student s WHERE s.id = :id")
	Collection<Activity> findAllActivitiesOfStudent(int id);

	@Query("SELECT a FROM Activity a where a.id = :id")
	Activity findActivityById(int id);

}
