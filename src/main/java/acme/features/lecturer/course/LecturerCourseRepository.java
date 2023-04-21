
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.ActivityType;
import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findAllAvailableCourses();

	@Query("select c from Configuration c")
	Configuration findConfiguration();

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.lecturer.userAccount.id = :id")
	Collection<Course> findCourseByLecturerId(int id);

	@Query("select count(cl) from CourseLecture cl where cl.course.id = :courseId and cl.lecture.activityType = :activityType")
	Integer findCountLecturesByCourseIdAndActivityType(int courseId, ActivityType activityType);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select c from Course c where c.code = :code and c.id != :id")
	Course findOneCourseByCodeAndDistinctId(String code, int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId")
	Collection<CourseLecture> findManyCourseLecturesByCourseId(int courseId);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);
}
