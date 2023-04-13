
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :id")
	Collection<Audit> findMyAudits(int id);

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	Audit findAuditById(int id);

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findAuditorById(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

}
