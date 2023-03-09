
package acme.entities;

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

@Getter
@Setter
@Entity
public class Note extends AbstractEntity {

	// Serialisation Identifier

	private static final Long	serialVersionUID	= 1L;

	// Attributes
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instMoment;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 76)
	protected String			author;

	@NotBlank
	@Length(max = 101)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

}
