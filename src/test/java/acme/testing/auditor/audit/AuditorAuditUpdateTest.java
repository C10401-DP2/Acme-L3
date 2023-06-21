
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditUpdateTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditTestRepository repository;

	// Test data


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String code) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, "B");
		super.checkColumnHasValue(recordIndex, 2, conclusion);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int recordIndex, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String code) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				param = String.format("id=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/audit/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/auditor/audit/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/auditor/audit/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
