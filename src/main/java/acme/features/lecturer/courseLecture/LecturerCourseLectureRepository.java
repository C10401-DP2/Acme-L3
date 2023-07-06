
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.courselecture.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select lr from Lecturer lr where lr.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findAllLecturesOfLecturerId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id and l.draftMode = false")
	Collection<Lecture> findAllLecturesOfLecturerIdAvailables(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	Collection<Lecture> findLecturesOfCourseId(int courseId);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :lectureId")
	CourseLecture findOneCourseLectureByLectureId(int lectureId);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId")
	CourseLecture findOneCourseLectureByCourseId(int courseId);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	Collection<CourseLecture> findManyCourseLectureByCourseId(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId and cl.lecture.id = :lectureId")
	Collection<CourseLecture> findCourseLectureByIds(int courseId, int lectureId);
}
