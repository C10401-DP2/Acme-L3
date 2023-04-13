
package acme.features.authenticated.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface AuthenticatedStudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT a FROM Enrolment a WHERE Enrolment.getStudent().id = :id")
	Collection<Enrolment> findAllEnrolmentOfStudent(int id);

	@Query("SELECT a FROM Enrolment a WHERE Enrolmentid = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT a FROM Student a Where a.id = :id")
	Student findStudentById(int id);

	@Query("select a from Course a where a.draftMode = false")
	Collection<Course> findCourses();

	@Query("select a from Course a where a.id = :id")
	Course findCourseById(int id);

}
