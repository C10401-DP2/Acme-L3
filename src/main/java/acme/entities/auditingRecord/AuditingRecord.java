
package acme.entities.auditingRecord;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.audit.Audit;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	//Attributes

	@NotBlank
	@Length(max = 76)
	protected String			subject;

	@NotBlank
	@Length(max = 101)
	protected String			assessment;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initialDate;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalDate;

	@NotNull
	protected Mark				mark;

	@NotNull
	protected Boolean			isCorrection;

	@URL
	protected String			link;

	// Derived attributes

	// Relationships

	@ManyToOne
	protected Audit				audit;

}
