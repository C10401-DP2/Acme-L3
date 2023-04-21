
package acme.features.auditor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	// General

	@Query("SELECT count(a) FROM Audit a WHERE a.auditor.id = :id")
	Integer countAuditsByAuditorId(int id);

	@Query("SELECT count(ar) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Integer countAuditingRecordsByAuditorId(int id);

	// Auditing records statistics

	@Query("select avg(select count(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double averageAuditingRecordsByAuditorId(int id);

	@Query("select sqrt(sum(((select count(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) - :avgCount) * ((select count(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) - :avgCount))) FROM Audit a WHERE a.auditor.id = :id")
	Double deviationAuditingRecordsSubQuery(int id, double avgCount);

	default Double deviationAuditingRecordsByAuditorId(final int id, final double avgCount, final int sampleSize) {
		return this.deviationAuditingRecordsSubQuery(id, avgCount) / Math.sqrt(sampleSize);
	}

	@Query("select min(select count(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double minimumAuditingRecordsByAuditorId(int id);

	@Query("select max(select count(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double maximumAuditingRecordsByAuditorId(int id);

	// Period lengths statistics

	@Query("SELECT avg(TIME_TO_SEC(TIMEDIFF(ar.finalDate, ar.initialDate)) / 3600.) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double averagePeriodLengthOfAuditingRecordsByAuditorId(int id);

	@Query("SELECT sqrt(sum(((TIME_TO_SEC(TIMEDIFF(ar.finalDate, ar.initialDate)) / 3600.) - :avgTime) * ((TIME_TO_SEC(TIMEDIFF(ar.finalDate, ar.initialDate)) / 3600.) - :avgTime))) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double deviationPeriodLengthOfAuditingRecordsSubQuery(int id, double avgTime);

	default Double deviationPeriodLengthOfAuditingRecordsByAuditorId(final int id, final double avgTime, final int sampleSize) {
		return this.deviationPeriodLengthOfAuditingRecordsSubQuery(id, avgTime) / Math.sqrt(sampleSize);
	}

	@Query("SELECT min(TIME_TO_SEC(TIMEDIFF(ar.finalDate, ar.initialDate)) / 3600.) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double minimumPeriodLengthOfAuditingRecordsByAuditorId(int id);

	@Query("SELECT max(TIME_TO_SEC(TIMEDIFF(ar.finalDate, ar.initialDate)) / 3600.) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double maximumPeriodLengthOfAuditingRecordsByAuditorId(int id);
}
