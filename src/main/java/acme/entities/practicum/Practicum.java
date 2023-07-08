
package acme.entities.practicum;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.entities.practicumSession.PracticumSession;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			anAbstract;

	@NotBlank
	@Length(max = 76)
	protected String			goals;

	@NotNull
	protected Boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public Double estimatedTime(final Collection<PracticumSession> sessions) {
		double estimatedTime = 0;
		if (sessions.size() > 0)
			for (final PracticumSession session : sessions) {
				final long diffInMilliseconds = session.getFinalDate().getTime() - session.getInitialDate().getTime();
				final double diffInHours = diffInMilliseconds / (1000.0 * 60 * 60);
				estimatedTime = estimatedTime + diffInHours;
			}
		return estimatedTime;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company	company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course	course;

}
