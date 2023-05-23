
package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state
	@Autowired
	protected AuditorDashboardRepository repository;


	// Interface
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		dashboard = new AuditorDashboard();
		dashboard.setTotalAudits(this.repository.countAuditsByAuditorId(auditorId));

		dashboard.setAverageAuditingRecords(this.repository.averageAuditingRecordsByAuditorId(auditorId));
		dashboard.setDeviationAuditingRecords(this.repository.deviationAuditingRecordsByAuditorId(auditorId, dashboard.getAverageAuditingRecords(), this.repository.countAuditingRecordsByAuditorId(auditorId)));
		dashboard.setMinimumAuditingRecords(this.repository.minimumAuditingRecordsByAuditorId(auditorId));
		dashboard.setMaximumAuditingRecords(this.repository.maximumAuditingRecordsByAuditorId(auditorId));

		dashboard.setAveragePeriodLengthPerAuditingRecord(this.repository.averagePeriodLengthOfAuditingRecordsByAuditorId(auditorId));
		dashboard.setDeviationPeriodLengthPerAuditingRecord(this.repository.deviationPeriodLengthOfAuditingRecordsByAuditorId(auditorId, dashboard.getAveragePeriodLengthPerAuditingRecord(), this.repository.countAuditingRecordsByAuditorId(auditorId)));
		dashboard.setMinimumPeriodLengthPerAuditingRecord(this.repository.minimumPeriodLengthOfAuditingRecordsByAuditorId(auditorId));
		dashboard.setMaximumPeriodLengthPerAuditingRecord(this.repository.maximumPeriodLengthOfAuditingRecordsByAuditorId(auditorId));

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "totalAudits", "averageAuditingRecords", "deviationAuditingRecords", "minimumAuditingRecords", "maximumAuditingRecords", "averagePeriodLengthPerAuditingRecord", "deviationPeriodLengthPerAuditingRecord",
			"minimumPeriodLengthPerAuditingRecord", "maximumPeriodLengthPerAuditingRecord");

		super.getResponse().setData(tuple);
	}

}
