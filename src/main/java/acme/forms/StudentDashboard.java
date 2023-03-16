
package acme.forms;

import java.util.Map;

import acme.entities.activities.Activity;
import acme.framework.data.AbstractForm;

public class StudentDashboard extends AbstractForm {

	// Serialisation identifier --------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Atributes -----------------------------------------------------

	Integer						totalActivities;
	Map<Activity, Double>		averagePeriodActivities;
	Map<Activity, Double>		deviationPeriodActivities;
	Map<Activity, Double>		minPeriodActivities;
	Map<Activity, Double>		maxPeriodActivities;
	Double						averageLearningTime;
	Double						deviationLearningTime;
	Double						minimumLearningTime;
	Double						maximumLearningTime;

}
