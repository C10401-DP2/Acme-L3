
package acme.features.authenticated.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantShowService extends AbstractService<Authenticated, Assistant> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAssistantRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("username", String.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Assistant object;
		String username;

		username = super.getRequest().getData("username", String.class);
		object = this.repository.findOneAssistantByUsername(username);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Assistant object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "supervisor", "expertises", "resume", "link");

		super.getResponse().setData(tuple);
	}

}
