package org.test.automation.interpriters;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.test.automation.annotations.TextCall;
import org.test.automation.core.TextCallMethod;
 
public class TextCallAnnotationsInterpriter extends AbstractInterpriter{
	
	private static final Set<TextCallMethod> calls = new HashSet<TextCallMethod>();

	private final static Logger LOGGER = Logger.getLogger(TextCallAnnotationsInterpriter.class);
 
    static 
    {
        final Set<Class<?>> classes = AnnotationFinder.getClassesForAnnotation(TextCall.class);
    	
        for (Class<?> clazz : classes) {
        	
        	for (Method method : clazz.getMethods()) 
        	{
				if(AnnotationFinder.hasAnnotation(method, TextCall.class))
				{
        			try
					{
        				Constructor<?> constructor = getConstructor(clazz);
        				calls.add(TextCallMethod.buildFromAnnotatedMethod(method, constructor));
					} catch (InstantiationException e)
					{
						LOGGER.error("Error when trying to create test helper object", e);
					} catch (IllegalAccessException e)
					{
						LOGGER.error("Error when trying to create test helper object", e);
					} catch (IllegalArgumentException e)
					{
						LOGGER.error("Error when trying to create test helper object", e);
					} catch (InvocationTargetException e)
					{
						LOGGER.error("Error when trying to create test helper object", e);
					}
				}
			}
        }      
    }
    
    static Set<TextCallMethod> getCalls()
    {
    	Set<TextCallMethod> calls =  new HashSet<TextCallMethod>();
    	calls.addAll(TextCallAnnotationsInterpriter.calls);
    	return calls;
    }
    
    public static TextCallMethod getCall(String command)
    {
    	for (TextCallMethod method : calls)
		{
    		if(method.matchMethod(command))
    		{
    			return method;
    		}
		}
    	throw new RuntimeException("Unable to find method call for text " +command);
    }
    
}