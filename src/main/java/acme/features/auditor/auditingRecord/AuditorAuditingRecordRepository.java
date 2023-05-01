
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.auditingRecord.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Collection<AuditingRecord> findMyAuditingRecords(int id);

	@Query("SELECT ar.audit FROM AuditingRecord ar WHERE ar.id = :id")
	Audit findAuditByAuditingRecordId(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.id = :id")
	AuditingRecord findAuditingRecordById(int id);

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	Audit findAuditById(int id);

}
