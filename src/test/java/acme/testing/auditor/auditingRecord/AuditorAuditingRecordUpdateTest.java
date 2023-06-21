
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.auditingRecord.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialDate, final String finalDate, final String mark, final String link) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);
		super.checkColumnHasValue(auditingRecordRecordIndex, 2, "1");
		super.checkColumnHasValue(auditingRecordRecordIndex, 3, mark);

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

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialDate, final String finalDate, final String mark, final String link) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().getDraftMode()) {
				param = String.format("id=%d", auditingRecord.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
