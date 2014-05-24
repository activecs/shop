package ua.epam.dereza.shop.util;

import java.util.List;

/**
 * Bean validator
 * 
 * @author Eduard_Dereza
 *
 * @param <E>
 */
public interface Validator<E> {

	/**
	 * Method for validation
	 * 
	 * @param bean for validation
	 * @return list of errors
	 */
	public List<String> validate(E bean);
}
