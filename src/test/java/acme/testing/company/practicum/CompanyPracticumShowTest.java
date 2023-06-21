
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String anAbstract, final String goals, final String course) {
		// HINT: this test tries to show all practica created by the principal.

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "My practicums");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("anAbstract", anAbstract);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this show

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished practicum by someone who is not the principal.

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.getDraftMode() == true) {
				param = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
