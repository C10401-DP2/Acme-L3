
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditPublishTest extends TestHarness {

	// Internal state

	@Autowired
	protected AuditorAuditTestRepository repository;

	// Test data


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String params;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.getDraftMode()) {
				params = String.format("id=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/auditor/audit/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		Collection<Audit> audits;
		String params;

		super.signIn("auditor1", "auditor1");
		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (!audit.getDraftMode()) {
				params = String.format("id=%d", audit.getId());
				super.request("/auditor/audit/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		Collection<Audit> audits;
		String params;

		super.signIn("auditor2", "auditor2");
		audits = this.repository.findAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			params = String.format("id=%d", audit.getId());
			super.request("/auditor/audit/publish", params);
		}
		super.signOut();
	}

}
