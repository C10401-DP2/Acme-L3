
package acme.features.authenticated.student.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Enrolment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Repository
public class AuthenticatedStudentEnrolmentController extends AbstractController<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentEnrolmentListService		listService;

	@Autowired
	protected AuthenticatedStudentEnrolmentShowService		showService;

	@Autowired
	protected AuthenticatedStudentEnrolmentUpdateService	updateService;

	@Autowired
	protected AuthenticatedStudentEnrolmentDeleteService	deleteService;

	@Autowired
	protected AuthenticatedStudentEnrolmentCreateService	createService;

	@Autowired
	protected AuthenticatedStudentEnrolmentFinaliseService	finaliseService;

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
