
package acme.forms;

import java.util.Map;

import acme.datatypes.ActivityType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//Atributes ---------------------------------------------------------------
	Map<ActivityType, Integer>	totalTutorials;
	Double						averageTimeTutorialSessions;
	Double						standardDesviationTimeTutorialSessions;
	Double						minTimeTutorialSessions;
	Double						maxTimeTutorialSessions;
	Double						averageTimeTutorials;
	Double						standardDesviationTimeTutorials;
	Double						minTimeTutorials;
	Double						maxTimeTutorials;

}
