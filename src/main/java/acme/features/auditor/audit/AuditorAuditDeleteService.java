
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

	// Internal state
	@Autowired
	protected AuditorAuditRepository repository;


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
		audit = this.repository.findAuditById(id);
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit audit;
		int id;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		Collection<AuditingRecord> auditingRecords;

		auditingRecords = this.repository.findAuditingRecordsByAuditId(object.getId());
		this.repository.deleteAll(auditingRecords);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		super.getResponse().setData(tuple);
	}

}
