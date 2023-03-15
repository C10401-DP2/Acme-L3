
package acme.entities.practicum;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Min;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.persistence.Column;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long	serialVersionUID	= 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@Column(unique = true)
	protected String			code;

    @NotBlank
	@Length(max = 76)
	protected String            title;

    @NotBlank
	@Length(max = 101)
	protected String            anAbstract;

    @NotBlank
	@Length(max = 76)
	protected String            goals;

    // Derived attributes -----------------------------------------------------

    @NotNull
	protected Integer			totalTime;

    // Relationships ----------------------------------------------------------

    @NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company			company;

    @NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

}