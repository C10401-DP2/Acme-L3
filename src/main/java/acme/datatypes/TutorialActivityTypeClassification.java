
package acme.datatypes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.tutorial.Tutorial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorialActivityTypeClassification {

	@Valid
	@NotNull
	protected Tutorial		tutorial;

	@NotNull
	protected ActivityType	type;


	public TutorialActivityTypeClassification(@Valid @NotNull final Tutorial tutorial, @NotNull final int type) {
		this.tutorial = tutorial;
		switch (type) {
		case 0:
			this.type = ActivityType.THEORY;
			break;
		case 1:
			this.type = ActivityType.HANDSON;
			break;
		default:
			this.type = ActivityType.BALANCED;
		}
	}

	public ActivityType getType() {
		return this.type;
	}

}
