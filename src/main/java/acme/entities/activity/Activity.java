
package acme.entities.activity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.ActType;
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
	protected ActType			aType;

	@URL
	protected String			link;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initialDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalDate;


	@Transient
	public Integer totalTime() {
		long total;
		if (this.getInitialDate() == null || this.getFinalDate() == null)
			return null;
		else {
			total = this.getFinalDate().getTime() - this.getInitialDate().getTime();

			return (int) total / 3600000;
		}
	}

	// Relationships


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Enrolment enrolment;
}
