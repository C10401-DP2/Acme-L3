
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a")
	Collection<Audit> findAllAudits();

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	Audit findAuditById(int id);

	@Query("SELECT a.auditor FROM Audit a WHERE a.id = :id")
	Auditor findAuditorByAuditId(int id);

	@Query("SELECT a.course FROM Audit a WHERE a.id = :id")
	Course findCourseByAuditId(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

}
