
package acme.features.authenticated.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activity.Activity;
import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface AuthenticatedStudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT a FROM Enrolment a WHERE a.student.id = :id")
	Collection<Enrolment> findAllEnrolmentOfStudent(int id);

	@Query("SELECT a FROM Enrolment a WHERE a.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT a FROM Student a WHERE a.id = :id")
	Student findStudentById(int id);

	@Query("SELECT a from Course a WHERE a.draftMode = false")
	Collection<Course> findCourses();

	@Query("SELECT a from Course a WHERE a.id = :id")
	Course findCourseById(int id);

	@Query("SELECT SUM(EXTRACT(MINUTE FROM TIMEDIFF(a.finalDate, a.initialDate))) FROM Activity a WHERE a.enrolment IS NOT NULL AND a.enrolment.id = :id")
	Integer sumTotalTime(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	Collection<Activity> findAllActivitiesOfEnrolment(int id);

}
