
package acme.testing.student.activities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivitiesListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activities/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstrat, final String aType, final String initialDate, final String finalDate) {
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkListingExists();
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, abstrat);
		super.checkColumnHasValue(activityRecordIndex, 2, aType);
		super.checkColumnHasValue(activityRecordIndex, 3, initialDate);
		super.checkColumnHasValue(activityRecordIndex, 4, finalDate);

		super.signOut();

	}
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list all of the jobs using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activities/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
