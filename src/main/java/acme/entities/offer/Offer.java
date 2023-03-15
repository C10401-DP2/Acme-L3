
package acme.entities.bulletin;

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
public class Offer extends AbstractEntity {

    protected static final long	serialVersionUID	= 1L;

    @Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date	            moment;

    @NotBlank
	@Length(max = 76)
	protected String            heading;

    @NotBlank
	@Length(max = 101)
	protected String			summary;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                initialDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date                finalDate;

    @Min(0)
    private Double              price;

    @URL
	protected String			link;

}