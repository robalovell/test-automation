package interpriters.util;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class ConstructorParmeterHelper {
	
	public static Object[] getParamsForConstructer(Class<?>[] classes)
	{
		Object[] params = new Object[classes.length];
		
		ApplicationContext context = new ClassPathXmlApplicationContext("**/*Context.xml");
		int count = 0;
		for (Class<?> clazz : classes)
		{
			Map<String, Object> map = context.getBeansOfType(clazz);
			params[count++] = map.get(map.keySet().iterator().next());
		}
		
		return params;
		
	}

}
