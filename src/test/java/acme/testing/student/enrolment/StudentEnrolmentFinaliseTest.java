
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String creditCardNumber, final String holder) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("holder", holder);
		super.clickOnSubmit("Finalise");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("creditCardNumber", creditCardNumber.substring(12, 16));
		super.checkInputBoxHasValue("holder", holder);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String creditCardNumber, final String holder) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("holder", holder);
		super.clickOnSubmit("Finalise");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {
		// HINT: this test tries to update an enrolment with a role other than "Student",
		// HINT+ or using an student who is not the owner.

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findAllEnrolmentOfStudentByUsername("student1");
		for (final Enrolment ern : enrolments)
			if (ern.getDraftMode()) {
				param = String.format("id=%d", ern.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student2", "student2");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();
			} else {
				param = String.format("id=%d", ern.getId());
				super.signIn("student1", "student1");
				super.request("/student/enrolment/finalize", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
