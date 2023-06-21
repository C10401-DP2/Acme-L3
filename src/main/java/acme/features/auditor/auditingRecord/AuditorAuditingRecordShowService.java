
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.entities.auditingRecord.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state
	@Autowired
	protected AuditorAuditingRecordRepository repository;


	// Interface
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Audit audit;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditByAuditingRecordId(id);
		status = audit != null && (!audit.getDraftMode() || super.getRequest().getPrincipal().hasRole(audit.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord auditingRecord;
		int id;

		id = super.getRequest().getData("id", int.class);
		auditingRecord = this.repository.findAuditingRecordById(id);

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "initialDate", "finalDate", "mark", "isCorrection", "link");
		tuple.put("id", super.getRequest().getData("id", int.class));
		tuple.put("draftMode", object.getAudit().getDraftMode());
		tuple.put("marks", choices);

		super.getResponse().setData(tuple);
	}
}
