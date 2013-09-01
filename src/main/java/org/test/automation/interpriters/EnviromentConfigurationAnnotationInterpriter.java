package org.test.automation.interpriters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.test.automation.core.EnviromentConfigurationMethod;


public abstract class EnviromentConfigurationAnnotationInterpriter extends AbstractInterpriter {

	private final static Logger LOGGER = Logger.getLogger(AfterTestCaseAnnotationInterpriter.class);
 
    protected static  Set<EnviromentConfigurationMethod> getMethods(Class<? extends Annotation> annotationClass)
    {
    	final Set<EnviromentConfigurationMethod> calls = new HashSet<EnviromentConfigurationMethod>();
    	
        final Set<Class<?>> classes = AnnotationFinder.getClassesForAnnotation(annotationClass);
    	
        for (Class<?> clazz : classes) {
        	
        	for (Method method : clazz.getMethods()) 
        	{
				if(AnnotationFinder.hasAnnotation(method, annotationClass))
				{
        			try
					{
        				Constructor<?> constructor = getConstructor(clazz);
        				calls.add(EnviromentConfigurationMethod.buildFromAnnpotatedMethod(method, constructor));
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
        return calls;
    }
	
	
}
