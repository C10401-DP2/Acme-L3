
package acme.entities.course;

import java.util.List;

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

import acme.datatypes.ActivityType;
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
	public ActivityType activityType(final List<Lecture> lectures) {
		ActivityType activityType = ActivityType.BALANCED;
		if (!lectures.isEmpty()) {
			int theory = 0;
			int handsOn = 0;
			for (final Lecture l : lectures)
				if (l.getActivityType().equals(ActivityType.THEORY))
					theory++;
				else if (l.getActivityType().equals(ActivityType.HANDSON))
					handsOn++;
			if (theory > handsOn)
				activityType = ActivityType.THEORY;
			else if (handsOn > theory)
				activityType = ActivityType.HANDSON;
			else
				activityType = ActivityType.BALANCED;
		}
		return activityType;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer lecturer;
}
