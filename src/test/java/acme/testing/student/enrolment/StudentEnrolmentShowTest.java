
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course, final String totalTime, final String creditCardNumber, final String holder) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("totalTime", totalTime);

		super.checkInputBoxHasValue("creditCardNumber", creditCardNumber);
		super.checkInputBoxHasValue("holder", holder);

		super.checkSubmitExists("Update");
		super.checkSubmitExists("Delete");
		super.checkSubmitExists("Finalise");
		super.checkLinkExists("Activities");

		super.signOut();
	}

	void test200Negative(final int recordIndex, final String code, final String motivation, final String goals, final String course) {

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findAllEnrolmentOfStudentByUsername("student1");
		for (final Enrolment enr : enrolments) {
			param = String.format("id=%d", enr.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
