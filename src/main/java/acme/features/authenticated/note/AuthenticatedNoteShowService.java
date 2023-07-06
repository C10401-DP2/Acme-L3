
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteShowService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		Collection<Note> objects;
		Date limite;
		Boolean authorised;
		int note1;
		Note note2;
		limite = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);

		objects = this.repository.listing(limite);
		note1 = super.getRequest().getData("id", int.class);
		note2 = this.repository.findNoteById(note1);
		authorised = objects.contains(note2);

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Note object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findNoteById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "instMoment", "message", "author", "link", "email");
		tuple.put("confirmation", false);
		tuple.put("readonly", true);
		super.getResponse().setData(tuple);
	}

}
