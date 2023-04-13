
package acme.features.authenticated.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentEnrolmentListService extends AbstractService<Student, Enrolment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Principal principal;
		int userAccountId;

		final Collection<Enrolment> objects;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();

		objects = this.repository.findAllEnrolmentOfStudent(userAccountId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "initialDate", "finalDate");
		super.getResponse().setData(tuple);
	}

}
