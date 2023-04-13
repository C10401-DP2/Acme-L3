
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
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

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		super.getResponse().setData(tuple);
	}

}
