
package acme.features.authenticated.note;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedNoteController extends AbstractController<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteCreateServices createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}

}
