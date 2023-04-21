
package acme.entities.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 101)
	protected String			conclusion;

	@NotBlank
	@Length(max = 101)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 101)
	protected String			weakPoints;

	@NotNull
	protected Boolean			draftMode;

	// Derived attributes

	// Relationships

	@ManyToOne
	protected Course			course;

	@ManyToOne
	protected Auditor			auditor;

}
