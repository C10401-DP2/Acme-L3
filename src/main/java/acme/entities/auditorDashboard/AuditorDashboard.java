
package acme.entities.auditorDashboard;

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

	Integer						averageAuditingRecordsPerAudit;
	Integer						deviationAuditingRecordsPerAudit;
	Integer						minimumAuditingRecordsPerAudit;
	Integer						maximumAuditingRecordsPerAudit;

	Double						averagePeriodLengthPerAuditingRecord;
	Double						deviationPeriodLengthPerAuditingRecord;
	Double						minimumPeriodLengthPerAuditingRecord;
	Double						maximumPeriodLengthPerAuditingRecord;
}
