
package acme.forms;

import java.util.Map;

import acme.datatypes.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Map<ActivityType, Integer>	totalTheoryAndHandson;
	Double						averageLearningTimeLectures;
	Double						desviationLearningTimeLectures;
	Double						minLearningTimeLectures;
	Double						maxLearningTimeLectures;
	Double						averageLearningTimeCourses;
	Double						desviationLearningTimeCourses;
	Double						minLearningTimeCourses;
	Double						maxLearningTimeCourses;

}
