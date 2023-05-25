
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String moment, final String nick, final String message, final String email, final String link) {
		// HINT: this test lists all peeps, creates a new one 
		// HINT: and checks that it's been created properly.

		super.clickOnMenu("Peeps", "Peeps list");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("moment", moment);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Peeps", "Peeps list");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, moment);
		super.checkColumnHasValue(recordIndex, 2, nick);
		super.checkColumnHasValue(recordIndex, 3, message);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("moment", moment);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String moment, final String nick, final String message, final String email, final String link) {
		// HINT: this test attempts to create peeps with incorrect data.

		super.clickOnMenu("Peeps", "Peeps list");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("moment", moment);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {
		// HINT: there aren't any hacking tests for this feature since it's one that
		// HINT+ everyone can access, that is, any principals.
	}
}
