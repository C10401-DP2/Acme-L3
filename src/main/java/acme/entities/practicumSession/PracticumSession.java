package acme.entities.practicumSession;

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
import javax.persistence.FetchType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import acme.entities.practicum.Practicum;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long	serialVersionUID	= 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
	@Length(max = 76)
	protected String			title;

    @NotBlank
	@Length(max = 101)
	protected String			anAbstract;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                initialDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                finalDate;

    @URL
	protected String			link;

    // Relationships ----------------------------------------------------------

    @NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Practicum			practicum;

}