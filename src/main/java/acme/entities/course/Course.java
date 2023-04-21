
package acme.entities.course;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.CourseType;
import acme.entities.lecture.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			anAbstract;

	@NotNull
	protected Money				retailPrice;

	@URL
	protected String			link;

	@NotNull
	protected Boolean			draftMode;


	@Transient
	public CourseType courseType(final Collection<Lecture> lectures) {
		CourseType courseType = CourseType.NONE;
		if (!lectures.isEmpty()) {
			int theory = 0;
			int handsOn = 0;
			for (final Lecture l : lectures)
				if (l.getActivityType().toString().equals(CourseType.THEORY.toString()))
					theory++;
				else if (l.getActivityType().toString().equals(CourseType.HANDSON.toString()))
					handsOn++;
			if (theory > handsOn)
				courseType = CourseType.THEORY;
			else if (handsOn > theory)
				courseType = CourseType.HANDSON;
			else
				courseType = CourseType.BALANCED;
		}
		return courseType;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer lecturer;
}
