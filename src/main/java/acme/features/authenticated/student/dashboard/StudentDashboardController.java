
package acme.features.authenticated.student.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.forms.StudentDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

public class StudentDashboardController extends AbstractController<Student, StudentDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashBoardShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
