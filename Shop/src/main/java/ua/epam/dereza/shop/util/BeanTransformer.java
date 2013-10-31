package ua.epam.dereza.shop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.RegistrationForm;
import ua.epam.dereza.shop.bean.User;

/**
 * Transformer from bean to dto
 * 
 * @author Eduard_Dereza
 *
 */
public class BeanTransformer {

	private final static Logger log = Logger.getLogger(BeanTransformer.class);

	/**
	 * Transforms RegistrationForm to UserDTO
	 * 
	 * @param formBean
	 * @return UserDTO
	 */
	public static User transform(RegistrationForm formBean){
		User newUser = new User();

		newUser.setFirstName(formBean.getFirstName());
		newUser.setLastName(formBean.getLastName());
		newUser.setEmail(formBean.getEmail());
		newUser.setPassword(formBean.getPassword());
		newUser.setCompany(formBean.getCompany());
		newUser.setAddress1(formBean.getAddress1());
		newUser.setAddress2(formBean.getAddress2());
		newUser.setCity(formBean.getCity());
		newUser.setPostCode(new Integer(formBean.getPostcode()));
		newUser.setAdditionalInfo(formBean.getAdditionalInfo());
		newUser.setPhone(formBean.getPhone());
		newUser.setAvatar(formBean.getAvatar());
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd-mm-yyyy");
		try {
			newUser.setBirthDate(simpleDate.parse(formBean.getBirthDate()));
		} catch (ParseException e) {
			log.error("Something wrong in validator's code", e);
		}

		return newUser;
	}
}
