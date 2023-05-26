
package acme.testing.student.activities;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.activity.Activity;
import acme.testing.TestHarness;

public class StudentActivitiesUpdateTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivitiesTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activities/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstrat, final String activityType, final String initialDate, final String finalDate) {
		// HINT: this test logs in as an employer, lists his or her jobs, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstrat", abstrat);
		super.fillInputBoxIn("aType", activityType);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstrat", abstrat);
		super.checkInputBoxHasValue("aType", activityType);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("finalDate", finalDate);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activities/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstrat, final String activityType, final String initialDate, final String finalDate) {
		// HINT: this test attempts to update a job with wrong data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstrat", abstrat);
		super.fillInputBoxIn("aType", activityType);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("finalDate", finalDate);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a job with a role other than "Employer",
		// HINT+ or using an employer who is not the owner.

		final Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student1");
		for (final Activity act : activities) {
			param = String.format("id=%d", act.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activities/update", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/activities/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activities/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/activities/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
