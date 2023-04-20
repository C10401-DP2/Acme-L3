
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select lr from Lecturer lr where lr.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select lr from Lecturer lr where lr.id in (select c.lecturer.id from Course c where c.id = :courseId)")
	Lecturer findOneLecturerByCourseId(int courseId);

	@Query("select c from Course c")
	Collection<Course> findAllCourse();

	@Query("select cl from CourseLecture cl where cl.id =  :id")
	CourseLecture findOneCourseLectureById(int id);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :id")
	CourseLecture findOneCourseLectureByLectureId(int id);

	@Query("select l from Lecture l where l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id = :courseId)")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findManyCourseByLecturerId(int lecturerId);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :lectureId")
	Collection<CourseLecture> findManyCourseLectureByLectureId(int lectureId);

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findManyLectureByLecturer(int lecturerId);
}
