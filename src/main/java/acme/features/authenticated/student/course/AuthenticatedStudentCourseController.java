
package acme.features.authenticated.student.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courselecture.CourseLecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class AuthenticatedStudentCourseController extends AbstractController<Student, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentCourseListService	listService;

	@Autowired
	protected AuthenticatedStudentCourseShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
