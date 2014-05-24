package ua.epam.dereza.shop.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.bean.RequisitesBean;

/**
 * Validates RequisitesForm
 * 
 * @author Eduard_Dereza
 *
 */
public class RequisitesValidator implements Validator<RequisitesBean> {

	private static final Logger log = Logger.getLogger(RequisitesValidator.class);

	@Override
	public List<String> validate(RequisitesBean bean){
		if(log.isEnabledFor(Level.DEBUG))
			log.debug("Got bean for validation ->" + bean);
		List<String> errors = new ArrayList<String>();

		if (bean.getAddress() == null || bean.getAddress().length() < 3 || bean.getAddress().length() > 30) {
			errors.add("Check your adress second line (minimal length 3 symbols, max length 30 symbols)");
			bean.setAddress("");
		}
		if (bean.getCity() == null
				|| !bean.getCity().matches("([А-я]|[A-z]){2,20}([- ]([А-я]|[A-z]){2,20}|)")) {
			errors.add("Check your city");
			bean.setCity("");
		}
		if (bean.getPhone() == null || !bean.getPhone().matches("[\\+|\\d][\\d -]{4,17}")) {
			errors.add("Check your phone number (minimal length 5 symbols)");
			bean.setPhone("");
		}
		try{
			String paymentMethod = bean.getPaymentMethod().toUpperCase();
			Order.PaymentMethod.valueOf(paymentMethod);
		}catch(IllegalArgumentException | NullPointerException e){
			errors.add("Check your payment method");
		}

		if(log.isEnabledFor(Level.DEBUG))
			log.debug("Result of validation, were found " + errors.size() + " errors.");
		return errors;
	}
}