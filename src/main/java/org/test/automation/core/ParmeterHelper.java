package org.test.automation.core;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.test.automation.core.util.TestAutomationProperties;


final class ParmeterHelper {
	
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(TestAutomationProperties.getAutoInjectionContextPath());
	
	static Object[] getParamsForMethod(Class<?>[] classes)
	{
		Object[] params = new Object[classes.length];
		
		int count = 0;
		for (Class<?> clazz : classes)
		{
			Map<String, Object> map = context.getBeansOfType(clazz);
			params[count++] = map.get(map.keySet().iterator().next());
		}
		
		return params;
		
	}

}
