
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.entities.practicumSession.PracticumSession;
import acme.testing.TestHarness;

public class CompanyAddendumSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepositor repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-addendum-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String anAbstract, final String initialDate,
		final String finalDate, final String link, final String accept) {
		// HINT: this test attempts to create an addendum session for a published practicum without any addendum using wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("List of sessions");

		super.clickOnButton("Create addendum session");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("anAbstract", anAbstract);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("accept", accept);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-addendum-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int practicumRecordIndex, final String code, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String anAbstract, final String initialDate,
		final String finalDate, final String link, final String accept) {
		// HINT: this test authenticates as a company, list his or her practica, navigates
		// HINT+ to a published practicum without any addendum and lists its sessions. Then creates a new one addendum session, and check that it's 
		// HINT+ been created properly.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("List of sessions");

		super.clickOnButton("Create addendum session");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("anAbstract", anAbstract);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("accept", accept);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, practicumSessionTitle);
		super.checkColumnHasValue(practicumSessionRecordIndex, 1, initialDate);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, finalDate);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("title", practicumSessionTitle);
		super.checkInputBoxHasValue("anAbstract", anAbstract);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create an addendum Session for a practicum as a principal without
		// HINT: the "Company" role.
		// HINT+ or using a company who is not the owner.

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create an addendum session for a published practicum with one addendum session created by
		// HINT+ the principal.

		Collection<Practicum> practica;
		Collection<PracticumSession> practicumSessions;

		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			practicumSessions = this.repository.findManyPracticumSessionsByPracticumId(practicum.getId());
			if (!practicum.getDraftMode() && practicumSessions.stream().anyMatch(ps -> ps.getAddendum())) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/practicum-session/create-addendum", param);
				super.checkPanicExists();
			}
		}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create an addendum session for an unpublished practicum created by
		// HINT+ the principal.

		Collection<Practicum> practica;

		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.getDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/practicum-session/create-addendum", param);
				super.checkPanicExists();
			}
	}

}
