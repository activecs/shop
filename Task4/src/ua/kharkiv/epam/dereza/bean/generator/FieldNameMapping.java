package ua.kharkiv.epam.dereza.bean.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It is used as key for getting 
 * user-friendly field name from resource bundler
 * 
 * @author Eduard_Dereza
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FieldNameMapping {
	String fieldName();
}
