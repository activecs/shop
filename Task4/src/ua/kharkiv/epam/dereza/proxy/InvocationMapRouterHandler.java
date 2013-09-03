package ua.kharkiv.epam.dereza.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Proxy Handler implementation
 * 
 * @author Eduard_Dereza
 *
 */
public class InvocationMapRouterHandler implements InvocationHandler{
	
	private Map<String, Object> mapObj;
	
	public InvocationMapRouterHandler(Map<String, Object> mapObj) {
		this.mapObj = mapObj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		if(methodName.startsWith(RouterProxyFactory.METHOD_PREFIX_SET)){
			methodName = methodName.replace(RouterProxyFactory.METHOD_PREFIX_SET, "");
			mapObj.put(methodName, args[0]);
			return null;
		}
		if(methodName.startsWith(RouterProxyFactory.METHOD_PREFIX_GET)){
			methodName = methodName.replace(RouterProxyFactory.METHOD_PREFIX_GET, "");
			return mapObj.get(methodName);
		}
		
		Method objMethod = mapObj.getClass().getMethod(method.getName(),
				method.getParameterTypes());
		return objMethod.invoke(mapObj, args);
	}

}
