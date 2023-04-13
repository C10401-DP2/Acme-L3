
package acme.features.authenticated.student.activities;

import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentActivityListService extends AbstractService<Student, Activity> {

}
