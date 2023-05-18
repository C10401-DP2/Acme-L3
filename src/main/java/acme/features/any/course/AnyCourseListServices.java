
package acme.features.any.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.course.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.helper.ExchangeMoneyHelper;

@Service
public class AnyCourseListServices extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Course> objects;
		objects = this.repository.findAllCourse();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		final Configuration config = this.repository.findConfiguration();

		Tuple tuple;
		final Money defaultCurrency = ExchangeMoneyHelper.computeMoneyExchange(object.getRetailPrice(), config.getCurrency()).getTarget();

		tuple = super.unbind(object, "code", "title", "retailPrice", "link");
		tuple.put("default", defaultCurrency);

		super.getResponse().setData(tuple);
	}
}
