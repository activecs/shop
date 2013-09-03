package ua.kharkiv.epam.dereza.bean.generator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.proxy.RouterProxyFactory;
import ua.kharkiv.epam.dereza.service.Main;
import ua.kharkiv.epam.dereza.util.Utility;

/**
 * Appropriate name of the class is manual network equipment generator via reflection.
 * 
 * @author Eduard_Dereza
 *
 */
public class ReflectionManualGenerator extends EquipmentGenerator{
	
	private Map<String, Class> classes;
	private Locale locale;
	
	public ReflectionManualGenerator(Map<String, Class> classes, Locale locale) {
		this.classes = classes;
		this.locale = locale;
	}
	
	@Override
	public NetworkEquipment generate(String name) {
		Class clazz = classes.get(name);
		NetworkEquipment realObject = null;
		
		if(clazz != null){
			ResourceBundle bundle = ResourceBundle.getBundle(Main.basename, locale);
			Scanner scanner = new Scanner(System.in);
			try {
				realObject = (NetworkEquipment) clazz.newInstance();
				reflectBeanInit(realObject, clazz, scanner, bundle);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return realObject;
	}
	
	/**
	 * Allow you to initialize bean using reflection API.
	 * Works only with fields marked with @FieldNameMapping
	 * 
	 * @param obj
	 * @param clazz
	 * @param scanner
	 * @param bundle
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private <T> void reflectBeanInit(T obj, Class<T> clazz, Scanner scanner,
			ResourceBundle bundle) throws InvocationTargetException {
		System.out.println(bundle.getString("welcome.message"));

		for (Method method : clazz.getMethods()) {
			FieldNameMapping mapping = method
					.getAnnotation(FieldNameMapping.class);
			if (mapping != null) {
				if (method.getName().startsWith(
						RouterProxyFactory.METHOD_PREFIX_SET)) {
					String fieldName = mapping.fieldName();
					System.out.println(bundle.getString(fieldName) + " :");

					Class paramType = method.getParameterTypes()[0];
					Object readObj = Utility.readByType(paramType, scanner);
					try {
						method.invoke(obj, readObj);
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}

				}
			}
		}
	}
	
}
