
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String anAbstract, final String goals) {
		// HINT: this test logs in as an assistant, lists his or her tutorials,
		// HINT+ selects one of them, updates it, and then checks that
		// HINT+ the update has actually been performed.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("anAbstract", anAbstract);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("anAbstract", anAbstract);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String anAbstract, final String goals) {
		// HINT: this test attempts to update a tutorial with wrong data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("anAbstract", anAbstract);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a tutorial with a role other than "Assistant",
		// HINT+ or using an assistant who is not the owner.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("id=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test attempts to update a tutorial that has been published.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.getDraftMode()) {
				param = String.format("id=%d", tutorial.getId());

				super.signIn("assistant1", "assistant1");
				super.request("/assistant/tutorial/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
