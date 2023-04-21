
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lecture.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureListServices		listService;

	@Autowired
	protected LecturerLectureShowServices		showService;

	@Autowired
	protected LecturerLectureCreateServices		createService;

	@Autowired
	protected LecturerLectureUpdateServices		updateService;

	@Autowired
	protected LecturerLectureDeleteServices		deleteService;

	@Autowired
	protected LecturerLecturePublishServices	publishService;

	@Autowired
	protected LecturerLectureListMineServices	listMineService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
