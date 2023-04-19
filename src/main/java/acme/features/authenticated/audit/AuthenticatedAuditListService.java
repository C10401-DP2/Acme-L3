
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListService extends AbstractService<Authenticated, Audit> {

	// Internal state
	@Autowired
	protected AuthenticatedAuditRepository repository;


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
		Collection<Audit> objects;

		objects = this.repository.findAllAudits();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Course course;

		course = object.getCourse();

		tuple = super.unbind(object, "code", "conclusion");
		tuple.put("course", course.getTitle());

		super.getResponse().setData(tuple);
	}

}
