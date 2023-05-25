
package acme.entities.practicumSession;

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

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			anAbstract;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				finalDate;

	protected Boolean			addendum;

	@URL
	protected String			link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Practicum			practicum;

}
