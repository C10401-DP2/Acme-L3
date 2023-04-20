
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.entities.auditingRecord.Correction;
import acme.entities.auditingRecord.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

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
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "initialDate", "finalDate", "mark", "correction", "link");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateError;

			finalDateError = MomentHelper.isBefore(object.getInitialDate(), object.getFinalDate());

			super.state(finalDateError, "finalDate", "auditor.auditing-record.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalDate")) {
			boolean finalDateErrorDuration;

			finalDateErrorDuration = MomentHelper.isLongEnough(object.getInitialDate(), object.getFinalDate(), 1L, ChronoUnit.HOURS);

			super.state(finalDateErrorDuration, "finalDate", "auditor.auditing-record.form.error.duration");
		}

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices1;
		SelectChoices choices2;

		choices1 = SelectChoices.from(Mark.class, object.getMark());
		choices2 = SelectChoices.from(Correction.class, object.getCorrection());

		tuple = super.unbind(object, "subject", "assessment", "initialDate", "finalDate", "mark", "correction", "link");
		tuple.put("id", super.getRequest().getData("id", int.class));
		tuple.put("draftMode", object.getAudit().getDraftMode());
		tuple.put("marks", choices1);
		tuple.put("corrections", choices2);

		super.getResponse().setData(tuple);
	}
}
