
package acme.features.lecturer.dashboardLecturer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select lr from Lecturer lr where lr.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select count(l) from Lecture l where l.activityType = 'THEORY' AND l.lecturer.id = :id")
	Integer getTotalTheoryLecturesByLecturerId(int id);

	@Query("select count(l) from Lecture l where l.activityType = 'HANDSON' AND l.lecturer.id = :id")
	Integer getTotalHandsonLecturesByLecturerId(int id);

	@Query("select avg(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double getAverageLectureLearningTimeByLecturerId(int id);

	@Query("select stddev(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double getDeviationLectureLearningTimeByLecturerId(int id);

	@Query("select min(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double getMinLectureLearningTimeByLecturerId(int id);

	@Query("select max(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double getMaxLectureLearningTimeByLecturerId(int id);

	@Query("select avg(l.learningTime) from Lecture l where " //
		+ "l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id in "//
		+ "(select c.id from Course c where c.lecturer.id = :id))")
	Double getAverageCourseLearningTimeByLecturerId(int id);

	@Query("select stddev(l.learningTime) from Lecture l where " //
		+ "l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id in "//
		+ "(select c.id from Course c where c.lecturer.id = :id))")
	Double getDeviationCourseLearningTimeByLecturerId(int id);

	@Query("select min(l.learningTime) from Lecture l where " //
		+ "l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id in "//
		+ "(select c.id from Course c where c.lecturer.id = :id))")
	Double getMinCourseLearningTimeByLecturerId(int id);

	@Query("select max(l.learningTime) from Lecture l where " //
		+ "l.id in (select cl.lecture.id from CourseLecture cl where cl.course.id in "//
		+ "(select c.id from Course c where c.lecturer.id = :id))")
	Double getMaxCourseLearningTimeByLecturerId(int id);
}
