
package acme.features.lecturer.dashboardLecturer;

import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	//	@Query("select (select l.activityType, count(l) from Lecture l where l.lecturer.id = lr.id group by l.activityType) from Lecturer lr")
	//	Map<ActivityType, Integer> totalTheoryAndHandson();
	//
	//	@Query("select avg(l.learningTime from lecture l where l.lecturer.id = lr.id) from Lecturer lr")
	//	Double averageLearningTimeLectures();
	//
	//	@Query("select stv(l.learningTime from lecture l where l.lecturer.id = lr.id) from Lecturer lr")
	//	Double desviationLearningTimeLectures();
	//
	//	@Query("select min(l.learningTime from lecture l where l.lecturer.id = lr.id) from Lecturer lr")
	//	Double minLearningTimeLectures();
	//
	//	@Query("select max(l.learningTime from lecture l where l.lecturer.id = lr.id) from Lecturer lr")
	//	Double maxLearningTimeLectures();
	//
	//	@Query("select avg(select l.learningTime from Lecture l where l.id = cl.lecture.id) from CourseLecture cl ")
	//	Double averageLearningTimeCourses();
	//
	//	Double desviationLearningTimeCourses();
	//
	//	Double minLearningTimeCourses();
	//
	//	Double maxLearningTimeCourses();
}
