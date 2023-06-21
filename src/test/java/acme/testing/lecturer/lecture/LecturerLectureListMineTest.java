
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String anAbstract, final String learningTime, final String body, final String activityType, final String link) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, learningTime);
		super.checkColumnHasValue(recordIndex, 2, activityType);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/lecture/list-mine");
		super.checkPanicExists();
		super.signOut();
	}
}
