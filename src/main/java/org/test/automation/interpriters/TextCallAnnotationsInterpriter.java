package org.test.automation.interpriters;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.test.automation.annotations.TextCall;
import org.test.automation.core.TextCallMethod;

public class TextCallAnnotationsInterpriter extends AbstractInterpriter {

	private static final Set<TextCallMethod> calls = new HashSet<TextCallMethod>();

	private final static Logger LOGGER = Logger.getLogger(TextCallAnnotationsInterpriter.class);

	static
	{
		Class<TextCall> annotationClass = TextCall.class ;
		LOGGER.info("get all methods with annotation " + annotationClass.getName());
		final Set<Class<?>> classes = AnnotationFinder.getClassesForAnnotation(annotationClass);

		LOGGER.info("found  " + classes.size() + " classes which hold a methods with annotation " + annotationClass.getName());
		for (Class<?> clazz : classes)
		{

			for (Method method : clazz.getMethods())
			{
				if (AnnotationFinder.hasAnnotation(method, TextCall.class))
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
					} catch (IOException e)
					{
						LOGGER.error("Error when trying to create test helper object", e);
					}
				}
			}
		}
	}

	static Set<TextCallMethod> getCalls()
	{
		Set<TextCallMethod> calls = new HashSet<TextCallMethod>();
		calls.addAll(TextCallAnnotationsInterpriter.calls);
		return calls;
	}

	public static TextCallMethod getCall(String command)
	{
		for (TextCallMethod method : calls)
		{
			if (method.matchMethod(command))
			{
				return method;
			}
		}
		throw new RuntimeException("Unable to find method call for text " + command);
	}

}