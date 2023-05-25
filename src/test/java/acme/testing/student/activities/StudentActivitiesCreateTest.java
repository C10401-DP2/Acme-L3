
package acme.testing.student.activities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivitiesCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activities/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstrat, final String activityType, final String enrolment, final String initialDate, final String finalDate) {
		// HINT: this test authenticates as an employer and then lists his or her
		// HINT: jobs, creates a new one, and check that it's been created properly.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("aType", activityType);
		super.fillInputBoxIn("abstrat", abstrat);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);

		super.clickOnMenu("Student", "Activities");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, abstrat);
		super.checkColumnHasValue(activityRecordIndex, 2, activityType);
		super.checkColumnHasValue(activityRecordIndex, 3, initialDate);
		super.checkColumnHasValue(activityRecordIndex, 4, finalDate);

		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("aType", activityType);
		super.checkInputBoxHasValue("enrolment", enrolment);
		super.checkInputBoxHasValue("abstrat", abstrat);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activities/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abstrat, final String activityType, final String enrolment, final String initialDate, final String finalDate) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Activities");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("aType", activityType);
		super.fillInputBoxIn("enrolment", enrolment);
		super.fillInputBoxIn("abstrat", abstrat);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/activities/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activities/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/activities/create");
		super.checkPanicExists();
		super.signOut();
	}

}
