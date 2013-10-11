package ua.epam.dereza.shop.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.RegistrationForm;

/**
 * Utility class for validation
 * 
 * @author Eduard_Dereza
 *
 */
public class BeanValidator {

	private static final Logger log = Logger.getLogger(BeanValidator.class);

	/**
	 * Validates RegistrationForm
	 * 
	 * @param bean for validation
	 * @return list of errors
	 */
	public static List<String> validate(RegistrationForm bean) {
		if(log.isEnabledFor(Level.DEBUG))
			log.debug("Got bean for validation ->" + bean);
		List<String> errors = new ArrayList<String>();

		// validates values
		if (bean.getFirstName() == null || !bean.getFirstName().matches("\\S{2,15}")) {
			errors.add("Check your first name (minimal length 2 symbols, maximal 15)");
			bean.setFirstName("");
		}
		if (bean.getLastName() == null || !bean.getLastName().matches("\\S{2,15}")) {
			errors.add("Check your last name (minimal length 2 symbols, maximal 15)");
			bean.setLastName("");
		}
		if (bean.getEmail() == null || !bean.getEmail().matches("\\S+@\\S+\\.\\S+")) {
			errors.add("Check your email");
			bean.setEmail("");
		}
		if (bean.getPassword() == null || bean.getPassword().length() < 6) {
			errors.add("Check your password (minimal length 6 symbols)");
		}
		if (bean.getBirthDate() == null
				|| !bean.getBirthDate().matches(
						"(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19\\d{2}|20(0[0-9]|10))")) {
			errors.add("Check your birth day");
			bean.setBirthDate("");
		}
		// we allow that company can be empty
		if (bean.getCompany() == null)
			bean.setCompany("");

		if (bean.getAddress1() == null || bean.getAddress1().length() < 3) {
			errors.add("Check your adress first line (minimal length 3 symbols)");
			bean.setAddress1("");
		}
		if (bean.getAddress2() == null || bean.getAddress2().length() < 3) {
			errors.add("Check your adress second line (minimal length 3 symbols)");
			bean.setAddress2("");
		}
		if (bean.getCity() == null
				|| !bean.getCity().matches("([А-я]|[A-z]){2,20}([- ]([А-я]|[A-z]){2,20}|)")) {
			errors.add("Check your city");
			bean.setCity("");
		}
		if (bean.getPostcode() == null
				|| !bean.getPostcode().matches("\\d{4,10}")) {
			errors.add("Check your postcode (minimal length 4 symbols)");
			bean.setPostcode("");
		}
		if (bean.getPhone() == null || !bean.getPhone().matches("[\\+|\\d][\\d -]{4,17}")) {
			errors.add("Check your phone number (minimal length 5 symbols)");
			bean.setPhone("");
		}

		if(log.isEnabledFor(Level.DEBUG))
			log.debug("Result of validation, were found " + errors.size() + " errors.");
		return errors;
	}
}
