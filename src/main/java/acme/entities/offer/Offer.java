
package acme.entities.offer;

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

import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				moment;

	@NotBlank
	@Length(max = 76)
	protected String			heading;

	@NotBlank
	@Length(max = 101)
	protected String			summary;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				finalDate;

	private Money				price;

	@URL
	protected String			link;

	// Relationships -----------------------------------------------------------

	@ManyToOne
	protected Administrator		administrator;

}
