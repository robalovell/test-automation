package org.test.automation.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.test.automation.exceptions.EnviromentException;
import org.test.automation.exceptions.ValidationException;

public class EnviromentConfigurationMethod {

	private final static Logger LOGGER = Logger.getLogger(EnviromentConfigurationMethod.class);

	private final Object object;

	private final Method method;

	private EnviromentConfigurationMethod(Object object, Method method)
	{
		this.object = object;
		this.method = method;
	}

	public static EnviromentConfigurationMethod buildFromAnnotatedMethod(Method method, Constructor<?> constructor) throws IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if(method.getDeclaringClass() != constructor.getDeclaringClass())
		{
			throw new ValidationException("method "+method.getName()+" passed to "+EnviromentConfigurationMethod.class.getSimpleName()+" does not belong to the class "+constructor.getDeclaringClass().getSimpleName());
		}
		Class<?>[] constructerParamTypes;

		constructerParamTypes = constructor.getParameterTypes();
		Object object = constructor.newInstance(ParmeterHelper.getParamsForMethod(constructerParamTypes));
		return new EnviromentConfigurationMethod(object, method);

	}

	public void callMethod()
	{
		Object[] convertedParams = ParmeterHelper.getParamsForMethod(method.getParameterTypes());

		LOGGER.info("calling method " + method.getName() + " with params " + Arrays.toString(convertedParams));
		try
		{
			method.invoke(object, convertedParams);
		} catch (IllegalArgumentException e)
		{
			throw new EnviromentException("Unable to configure enviroment due to "+e.getMessage(), e);
		} catch (IllegalAccessException e)
		{
			throw new EnviromentException("Unable to configure enviroment due to "+e.getMessage(), e);
		} catch (InvocationTargetException e)
		{
			throw new EnviromentException("Unable to configure enviroment due to "+e.getMessage(), e);
		}
	}

}
