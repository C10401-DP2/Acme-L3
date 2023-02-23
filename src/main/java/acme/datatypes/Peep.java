
package acme.datatypes;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Peep extends AbstractDatatype {

	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instMoment;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 76)
	protected String			nick;

	@NotBlank
	@Length(max = 101)
	protected String			message;

	@Length(max = 76)
	protected String			email;

	@Length(max = 101)
	protected String			link;

}
