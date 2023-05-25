
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateCorrectionTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/create-correction-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialDate, final String finalDate, final String mark, final String link) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnButton("Add correction");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Add correction");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject + " *");
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);
		super.checkColumnHasValue(auditingRecordRecordIndex, 2, "1");
		super.checkColumnHasValue(auditingRecordRecordIndex, 3, mark);

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/create-correction-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialDate, final String finalDate, final String mark, final String link) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnButton("Add correction");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Add correction");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				param = String.format("masterId=%d", audit.getId());
				super.request("/auditor/auditing-record/create-correction", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
		}
	}

}
