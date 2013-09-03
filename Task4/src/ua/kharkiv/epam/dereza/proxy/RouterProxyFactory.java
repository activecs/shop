package ua.kharkiv.epam.dereza.proxy;

import java.lang.reflect.Proxy;
import java.util.HashMap;

import ua.kharkiv.epam.dereza.bean.Router;

/**
 * Factory method for dynamic proxy
 * 
 * @author Eduard_Dereza
 *
 */
public class RouterProxyFactory {
	
	public final static String METHOD_PREFIX_SET = "set";
	public final static String METHOD_PREFIX_GET = "get";
	
	public IRouter createRouterProxy(Router router){
		IRouter irouter = (IRouter) Proxy.newProxyInstance(IRouter.class.getClassLoader(),
				new Class[]{IRouter.class}, new InvocationRouterHandler(router));
		return irouter;
	}
	
	public IRouter createRouterMapProxy(){
		IRouter irouter = (IRouter) Proxy.newProxyInstance(IRouter.class.getClassLoader(),
				new Class[]{IRouter.class}, new InvocationMapRouterHandler(new HashMap<String,Object>()));
		return irouter;
	}
}
