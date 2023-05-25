
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.entities.auditingRecord.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateCorrectionService extends AbstractService<Auditor, AuditingRecord> {

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
		status = audit != null && !audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(masterId);

		object = new AuditingRecord();
		object.setSubject("");
		object.setAssessment("");
		object.setInitialDate(MomentHelper.getCurrentMoment());
		object.setFinalDate(MomentHelper.getCurrentMoment());
		object.setIsCorrection(true);
		object.setLink("");
		object.setAudit(audit);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "initialDate", "finalDate", "mark", "isCorrection", "link");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "auditor.auditing-record.form.error.confirmation");

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
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "initialDate", "finalDate", "isCorrection", "mark", "link");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getAudit().getDraftMode());
		tuple.put("marks", choices);
		tuple.put("confirmation", true);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
