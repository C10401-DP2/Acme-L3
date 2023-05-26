
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	// Internal state
	@Autowired
	protected AuditorAuditRepository repository;


	// Interface
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Audit audit;
		Auditor auditor;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);
		auditor = audit == null ? null : audit.getAuditor();
		status = audit != null && audit.getDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit audit;
		int id;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;

			existing = this.repository.findAuditByCode(object.getCode());
			super.state(existing != null, "code", "auditor.audit.form.error.not-allowed");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;

			existing = this.repository.findAuditByCode(object.getCode());
			super.state(existing.getId() == object.getId(), "code", "auditor.audit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(!(object.getCourse() == null), "course", "auditor.audit.form.error.course-not-null");
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
