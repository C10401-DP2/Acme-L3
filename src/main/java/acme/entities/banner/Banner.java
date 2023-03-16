package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long	serialVersionUID	= 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
	@Length(max = 76)
	protected String            title;

    @Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date	            moment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                initialDisplay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                finalDisplay;

    @URL
	protected String			image;

    @NotBlank
	@Length(max = 76)
	protected String            slogan;

    @URL
	protected String			documentLink;

}