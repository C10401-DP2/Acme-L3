
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state
	@Autowired
	protected AuditorAuditingRecordRepository repository;


	// Interface
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(masterId);
		status = audit != null && (!audit.getDraftMode() || super.getRequest().getPrincipal().hasRole(audit.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findMyAuditingRecords(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		int period;

		period = (int) (MomentHelper.computeDuration(object.getInitialDate(), object.getFinalDate()).getSeconds() / 3600.);

		tuple = super.unbind(object, "subject", "assessment", "initialDate", "finalDate", "mark", "correction", "link");
		tuple.put("period", period);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> objects) {
		assert objects != null;

		int masterId;
		Audit audit;
		final boolean showCreate;
		final boolean showCorrection;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(masterId);
		showCreate = audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		showCorrection = !audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("showCorrection", showCorrection);
	}

}
