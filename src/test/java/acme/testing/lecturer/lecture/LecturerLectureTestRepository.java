
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerLectureTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);

	@Query("select c from Course c where c.lecturer.userAccount.username = :username")
	Collection<Lecture> findManyLecturesByLecturerUsername(String username);

	@Query("select l.id from Lecture l where l.title = :title")
	int findIdByTitle(String title);
}
