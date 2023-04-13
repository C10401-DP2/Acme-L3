
package acme.features.authenticated.student.activities;

import org.springframework.stereotype.Controller;

import acme.entities.activities.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class AuthenticatedStudentActivitiesController extends AbstractController<Student, Activity> {

}
