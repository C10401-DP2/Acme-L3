
package acme.helper;

import org.springframework.web.client.RestTemplate;

import acme.components.ExchangeMoney;
import acme.forms.ExchangeMoneyForm;
import acme.framework.components.datatypes.Money;

public class ExchangeMoneyHelper {

	public static ExchangeMoneyForm computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert targetCurrency.matches("^[A-Z]{3}$");

		ExchangeMoneyForm result;
		RestTemplate api;
		ExchangeMoney record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money target;

		try {
			api = new RestTemplate();

			sourceCurrency = source.getCurrency();
			sourceAmount = source.getAmount();

			record = api.getForObject( //
				"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
				ExchangeMoney.class, //
				sourceCurrency, //
				targetCurrency //
			);

			assert record != null;
			rate = record.getRates().get(targetCurrency);
			targetAmount = rate * sourceAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			result = new ExchangeMoneyForm();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(record.getDate());
			result.setTarget(target);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}
}
