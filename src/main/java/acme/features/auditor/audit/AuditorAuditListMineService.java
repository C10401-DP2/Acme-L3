
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.entities.auditingRecord.Mark;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListMineService extends AbstractService<Auditor, Audit> {

	// Internal state
	@Autowired
	protected AuditorAuditRepository repository;


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
		Collection<Audit> audits;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		audits = this.repository.findMyAudits(auditorId);
		super.getBuffer().setData(audits);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		int id;
		Mark mode;
		int maxFreq;
		Collection<AuditingRecord> auditingRecords;
		final Map<Mark, Integer> freq = new HashMap<Mark, Integer>();

		id = object.getId();
		auditingRecords = this.repository.findAuditingRecordsByAuditId(id);
		mode = null;
		maxFreq = 0;

		for (final AuditingRecord ar : auditingRecords)
			freq.put(ar.getMark(), freq.getOrDefault(ar.getMark(), 0) + 1);

		for (final Map.Entry<Mark, Integer> entry : freq.entrySet())
			if (entry.getValue() > maxFreq) {
				mode = entry.getKey();
				maxFreq = entry.getValue();
			}

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		tuple.put("mode", mode);

		super.getResponse().setData(tuple);
	}

}
