
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalTheoryLectures;
	Integer						totalHandsonLectures;
	Map<String, Double>			statsLearningTimeLectures;
	Map<String, Double>			statsLearningTimeCourses;

}
