
package acme.features.authenticated.student;

import org.springframework.beans.factory.annotation.Autowired;

import acme.features.authenticated.provider.AuthenticatedProviderRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Provider;
import acme.roles.Student;

public class AuthenticatedStudentUpdateService extends AbstractService<Authenticated, Student> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedProviderRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Provider object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneProviderByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Student object) {
		assert object != null;

		super.bind(object, "statement", "sFeat", "wFeat", "link");
	}

	@Override
	public void validate(final Student object) {
		assert object != null;
	}

	@Override
	public void perform(final Student object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Student object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "statement", "sFeat", "wFeat", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
