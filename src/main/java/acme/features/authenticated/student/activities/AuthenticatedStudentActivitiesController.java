
package acme.features.authenticated.student.activities;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.activity.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class AuthenticatedStudentActivitiesController extends AbstractController<Student, Activity> {

	@Autowired
	protected AuthenticatedStudentActivityListService	listService;

	@Autowired
	protected AuthenticatedStudentActivityShowService	showService;

	@Autowired
	protected AuthenticatedStudentActivityUpdateService	updateService;

	@Autowired
	protected AuthenticatedStudentActivityDeleteService	deleteService;

	@Autowired
	protected AuthenticatedStudentActivityCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);
		//super.addBasicCommand("finalise", this.finaliseService);

	}

}
