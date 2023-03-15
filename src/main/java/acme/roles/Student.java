
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			Statement;

	@NotBlank
	@Length(max = 101)
	protected String			Sfeat;

	@NotBlank
	@Length(max = 101)
	protected String			Wfeat;

	@URL
	protected String			link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			courses;

}
