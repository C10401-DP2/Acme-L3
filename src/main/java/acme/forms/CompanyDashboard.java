
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//Atributes ---------------------------------------------------------------
	Map<String, Integer>		totalHandsOnPracticumByMonth;
    Map<String, Integer>		totalPracticePracticumByMonth;
	Map<String, Double>			averagePracticumSessions;
	Map<String, Double>			standardDesviationPracticumSessions;
	Map<String, Double>			minTimePracticumSessions;
	Map<String, Double>			maxTimePracticumSessions;
	Map<String, Double>			standardDesviationBudgetCurrency;
	Double						averagePracticums;
	Double						standardDesviationPracticums;
	Double						minTimePracticums;
	Double						maxTimePracticums;

}
