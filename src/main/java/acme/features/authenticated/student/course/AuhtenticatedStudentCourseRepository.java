
package acme.features.authenticated.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuhtenticatedStudentCourseRepository extends AbstractRepository {

	@Query("SELECT b from Course b")
	Collection<Course> findAllCourse();

	@Query("SELECT b from Course b WHERE b.id = :id")
	Course findCourseById(int id);

}
