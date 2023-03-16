
package acme.entities.auditor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(of = {
	"firm"
})
public class Auditor extends AbstractRole {

	// Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Length(max = 76)
	protected String			firm;

	@NotBlank
	@Column(unique = true)
	@Length(max = 26)
	protected String			professionalId;

	@NotBlank
	@Length(max = 101)
	protected String			certifications;

	@URL
	protected String			link;
}
