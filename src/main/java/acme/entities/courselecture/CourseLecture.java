
package acme.entities.courselecture;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLecture extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	protected Lecture			lecture;
}
