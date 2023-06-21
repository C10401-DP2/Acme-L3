
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String code) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, "");
		super.checkColumnHasValue(recordIndex, 2, conclusion);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);

		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String code) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();
	}

}
