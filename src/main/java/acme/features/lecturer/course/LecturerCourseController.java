
package acme.features.lecturer.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseController extends AbstractController<Lecturer, Course> {

	@Autowired
	protected LecturerCourseListServices	listService;

	@Autowired
	protected LecturerCourseShowServices	showService;

	@Autowired
	protected LecturerCourseCreateServices	createService;

	@Autowired
	protected LecturerCourseUpdateServices	updateService;

	@Autowired
	protected LecturerCourseDeleteServices	deleteService;

	@Autowired
	protected LecturerCoursePublishServices	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
