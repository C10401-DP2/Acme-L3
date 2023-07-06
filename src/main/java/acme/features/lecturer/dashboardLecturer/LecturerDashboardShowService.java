
package acme.features.lecturer.dashboardLecturer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	@Autowired
	protected LecturerDashboardRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final LecturerDashboard dashBoard = new LecturerDashboard();
		int id;
		Integer totalTheoryLectures = 0;
		Integer totalHandsonLectures = 0;
		final Map<String, Double> statsLearningTimeLectures = new HashMap<>();
		final Map<String, Double> statsLearningTimeCourses = new HashMap<>();

		id = super.getRequest().getPrincipal().getActiveRoleId();
		totalTheoryLectures = this.repository.getTotalTheoryLecturesByLecturerId(id);
		totalHandsonLectures = this.repository.getTotalHandsonLecturesByLecturerId(id);

		statsLearningTimeLectures.put("averageLecture", this.repository.getAverageLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("deviationLecture", this.repository.getDeviationLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("minLecture", this.repository.getMinLectureLearningTimeByLecturerId(id));
		statsLearningTimeLectures.put("maxLecture", this.repository.getMaxLectureLearningTimeByLecturerId(id));

		statsLearningTimeCourses.put("averageCourse", this.repository.getAverageCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("deviationCourse", this.repository.getDeviationCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("minCourse", this.repository.getMinCourseLearningTimeByLecturerId(id));
		statsLearningTimeCourses.put("maxCourse", this.repository.getMaxCourseLearningTimeByLecturerId(id));

		dashBoard.setTotalTheoryLectures(totalTheoryLectures);
		dashBoard.setTotalHandsonLectures(totalHandsonLectures);
		dashBoard.setStatsLearningTimeLectures(statsLearningTimeLectures);
		dashBoard.setStatsLearningTimeCourses(statsLearningTimeCourses);

		super.getBuffer().setData(dashBoard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		final Tuple tuple;

		tuple = super.unbind(object, "totalTheoryLectures", "totalHandsonLectures", "statsLearningTimeLectures", "statsLearningTimeCourses");

		super.getResponse().setData(tuple);

	}
}
