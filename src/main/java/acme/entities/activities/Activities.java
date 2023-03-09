
package acme.entities.activities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activities extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Size(max = 76)
	protected String			title;

	@NotBlank
	@Size(max = 101)
	protected String			abstrat;

	protected ActivityType		atype;
	
	

	@URL
	protected String			link;
}
