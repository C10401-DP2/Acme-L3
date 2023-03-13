
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorialDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//Atributes ---------------------------------------------------------------
	Integer						totalTutorials;
	Map<String, Double>			averageTutorialSessions;
	Map<String, Double>			standardDesviationTutorialSessions;
	Map<String, Double>			minTimeTutorialSessions;
	Map<String, Double>			maxTimeTutorialSessions;
	Map<String, Double>			standardDesviationBudgetCurrency;
	Double						averageTutorials;
	Double						standardDesviationTutorials;
	Double						minTimeTutorials;
	Double						maxTimeTutorials;

}
