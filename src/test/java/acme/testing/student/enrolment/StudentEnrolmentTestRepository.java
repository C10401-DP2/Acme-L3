
package acme.testing.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

public interface StudentEnrolmentTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Enrolment a WHERE a.student.userAccount.username = :username")
	Collection<Enrolment> findAllEnrolmentOfStudentByUsername(String username);

}
