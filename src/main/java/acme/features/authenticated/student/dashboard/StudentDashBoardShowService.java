
package acme.features.authenticated.student.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashBoardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

}
