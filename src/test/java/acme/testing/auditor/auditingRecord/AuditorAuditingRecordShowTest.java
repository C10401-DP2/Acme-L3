
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.auditingRecord.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final String code, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialDate, final String finalDate, final String mark, final String link) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().getDraftMode()) {
				param = String.format("id=%d", auditingRecord.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
