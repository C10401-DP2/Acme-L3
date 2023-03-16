
package acme.entities.tutorialsession;

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
import acme.entities.tutorial.Tutorial;
import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TutorialSession extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			anAbstract;

	protected ActivityType		type;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				finalDate;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorial;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Assistant			assistant;

}
