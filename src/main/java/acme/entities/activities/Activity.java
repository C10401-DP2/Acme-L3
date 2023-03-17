
package acme.entities.activities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.ActivityType;
import acme.entities.enrolment.Enrolment;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstrat;

	@NotNull
	protected ActivityType		aType;

	@URL
	protected String			link;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				finalDate;

	// Relationships

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Enrolment			enrolment;
}
