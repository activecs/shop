package ua.kharkiv.epam.dereza.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ua.kharkiv.epam.dereza.bean.Router;

/**
 * Router handler for proxy
 * 
 * @author Eduard_Dereza
 *
 */
public class InvocationRouterHandler implements InvocationHandler{
	
	private Router routerObj;
	
	public InvocationRouterHandler(Router routerObj) {
		this.routerObj = routerObj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//Ensure that your methods match exactly or you'll get NoSuchMethodExceptions here
		Method realObjMethod = routerObj.getClass().getMethod(method.getName(), method.getParameterTypes());
		if (realObjMethod.getName().startsWith(RouterProxyFactory.METHOD_PREFIX_SET)){
			throw new UnsupportedOperationException();
		}
		
		return realObjMethod.invoke(routerObj, args);
	}

}
