
package acme.forms;

import java.util.Map;

import acme.entities.activities.Activities;
import acme.framework.data.AbstractForm;

public class StudentDashboard extends AbstractForm {

	// Serialisation identifier --------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Atributes -----------------------------------------------------

	Integer						totalActivities;
	Map<Activities, Double>		averageActivities;
	Map<Activities, Double>		deviationActivities;
	Map<Activities, Double>		minPeriodActivities;
	Map<Activities, Double>		maxPeriodActivities;
	Double						averageLearningTime;
	Double						deviationLearningTime;
	Double						minimumLearningTime;
	Double						maximumLearningTime;

}
