
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	Integer						totalAudits;

	Double						averageAuditingRecords;
	Double						deviationAuditingRecords;
	Double						minimumAuditingRecords;
	Double						maximumAuditingRecords;

	Double						averagePeriodLengthPerAuditingRecord;
	Double						deviationPeriodLengthPerAuditingRecord;
	Double						minimumPeriodLengthPerAuditingRecord;
	Double						maximumPeriodLengthPerAuditingRecord;
}
