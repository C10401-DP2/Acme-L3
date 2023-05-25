
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordListTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;

	// Test methods


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final String code, final int auditingRecordRecordIndex, final String title, final String workLoad) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(auditRecordIndex, 0, code);
		super.clickOnListingRecord(auditRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("Auditing Records");

		super.checkListingExists();
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, title);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, workLoad);
		super.clickOnListingRecord(auditingRecordRecordIndex);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				param = String.format("masterId=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
