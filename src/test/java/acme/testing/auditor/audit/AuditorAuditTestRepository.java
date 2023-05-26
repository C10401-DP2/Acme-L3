
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findAuditsByAuditorUsername(String username);

	@Query("select ar from AuditingRecord ar where ar.audit.auditor.userAccount.username = :username")
	Collection<Audit> findAuditingRecordsByAuditorUsername(String username);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

}
