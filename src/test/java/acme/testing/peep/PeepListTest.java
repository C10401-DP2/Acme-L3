
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String moment, final String title, final String nick, final String message) {
		// HINT: this test lists all peeps and then checks that the listing has the expected data.

		super.clickOnMenu("Peeps", "Peeps list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, moment);
		super.checkColumnHasValue(recordIndex, 2, nick);
		super.checkColumnHasValue(recordIndex, 3, message);
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: there aren't any hacking tests for this feature since it's one that
		// HINT+ everyone can access, that is, any principals.
	}

}
