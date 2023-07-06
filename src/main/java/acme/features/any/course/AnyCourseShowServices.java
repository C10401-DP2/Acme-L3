
package acme.features.any.course;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Any;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.helper.ExchangeMoneyHelper;

@Service
public class AnyCourseShowServices extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);
		super.getResponse().setAuthorised(!object.getDraftMode());
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final Configuration config = this.repository.findConfiguration();
		final Money defaultCurrency = ExchangeMoneyHelper.computeMoneyExchange(object.getRetailPrice(), config.getCurrency()).getTarget();
		final Date dateExchange = ExchangeMoneyHelper.computeMoneyExchange(object.getRetailPrice(), config.getCurrency()).getDate();
		final Tuple tuple = super.unbind(object, "code", "title", "anAbstract", "retailPrice", "link");
		final List<Lecture> lectures = this.repository.findLecturesByCourse(object.getId()).stream().collect(Collectors.toList());
		tuple.put("courseType", object.courseType(lectures));
		tuple.put("default", defaultCurrency);
		tuple.put("date", dateExchange);

		super.getResponse().setData(tuple);
	}
}
