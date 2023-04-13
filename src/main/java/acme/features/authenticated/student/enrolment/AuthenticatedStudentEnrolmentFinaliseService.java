
package acme.features.authenticated.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		student = enrolment.getStudent();
		status = enrolment != null && enrolment.getDraftMode() && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

}
