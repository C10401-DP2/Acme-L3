
package acme.entities.enrolment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			motivation;

	@NotBlank
	@Length(max = 101)
	protected String			goals;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date				finalDate;

	@NotNull
	protected Boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public Integer totalTime() {
		final long tiempoTotal = this.finalDate.getTime() - this.initialDate.getTime();
		return (int) (tiempoTotal / 3600000);

	}

	//Relationships -----------------------------------------------------------


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Student	student;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Course	course;
}
