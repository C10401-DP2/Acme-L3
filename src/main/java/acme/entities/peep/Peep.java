
package acme.entities.peep;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peep extends AbstractEntity {

	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				moment;

	@NotBlank
	@Length(max = 76)
	protected String			nick;

	@NotBlank
	@Length(max = 101)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

}
