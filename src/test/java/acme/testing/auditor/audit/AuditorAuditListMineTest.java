
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String average, final String conclusion) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		//super.checkColumnHasValue(recordIndex, 1, average);
		super.checkColumnHasValue(recordIndex, 2, conclusion);
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
