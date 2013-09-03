package ua.kharkiv.epam.dereza.bean.generator;

import java.lang.reflect.Method;
import java.util.Map;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.proxy.RouterProxyFactory;
import ua.kharkiv.epam.dereza.util.Utility;

/**
 * Appropriate name of the class is random network equipment generator via reflection.
 * 
 * @author Eduard_Dereza
 *
 */
public class ReflectionRandomGenerator extends EquipmentGenerator{
	
	private Map<String, Class> classes;
	
	public ReflectionRandomGenerator(Map<String, Class> classes) {
		this.classes = classes;
	}
	
	@Override
	public NetworkEquipment generate(String name) {
		Class clazz = classes.get(name);
		NetworkEquipment realObject = null;
		
		if(clazz != null){
			try {
				realObject = (NetworkEquipment) clazz.newInstance();
				
				for(Method method : clazz.getMethods()){
					FieldNameMapping mapping = method.getAnnotation(FieldNameMapping.class);
					if(mapping != null){
						if(method.getName().startsWith(RouterProxyFactory.METHOD_PREFIX_SET)){
							Class paramType = method.getParameterTypes()[0];
							Object genObj = Utility.generateRandObject(paramType);
							method.invoke(realObject, genObj);
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return realObject;
	}
	
}