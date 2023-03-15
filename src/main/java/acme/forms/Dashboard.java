
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//Atributes ---------------------------------------------------------------
	Integer						totalPrincipalsWithRole;
	Double						averagePeepsWithEmailAndLink;
	Map<String, Double>			averageCriticalAndNotCriticalBulletin;
	Map<String, Double>			averageBuddgetCurrency;
	Map<String, Double>			minBudgetCurrency;
	Map<String, Double>			maxBudgetCurrency;
	Map<String, Double>			standardDesviationBudgetCurrency;
	Double						averageNotesLast10Weeks;
	Double						minNotesLast10Weeks;
	Double						maxNotesLast10Weeks;
	Double						standardDesviationNotesLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
