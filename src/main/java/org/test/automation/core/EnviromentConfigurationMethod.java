package org.test.automation.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.test.automation.annotations.AfterTestCase;
import org.test.automation.annotations.BeforeTestCase;
import org.test.automation.annotations.TextCall;
import org.test.automation.exceptions.EnviromentException;
import org.test.automation.exceptions.ValidationException;


/**<p>This is a wrapper class used to hold methods that have been annotated 
 * with either the {@link org.test.automation.annotations.BeforeTestCase} or the 
 * {@link org.test.automation.annotations.AfterTestCase} annotations.
 * 
 * </p>
 * 
 * @author roblovell
 *
 */
public class EnviromentConfigurationMethod implements ProxyMethodCall{

	private final static Logger LOGGER = Logger.getLogger(EnviromentConfigurationMethod.class);

	private final Object object;

	private final Method method;

	private EnviromentConfigurationMethod(Object object, Method method)
	{
		this.object = object;
		this.method = method;
	}

	/**<p>This builds a new instance of EnviromentConfigurationMethod from a method and constructor that represent 
	 * the way to build the class that holds the method and the method to call.</p>
	 *  
	 *  <p>Will throw a {@link org.test.automation.exceptions.ValidationException} if the method and constructor don't
	 *  belong to the same class or the method is not annotated with either {@link org.test.automation.annotations.BeforeTestCase} or 
	 *  {@link org.test.automation.annotations.AfterTestCase}.
	 * @param method the method that should be run when you invoke callMethod
	 * @param constructor the constructor to use in order to create the object containing the method
	 * @return the new EnviromentConfigurationMethod instance
	 * @throws IllegalArgumentException if not all params can be found for constructor
	 * @throws InstantiationException if unable to initiate object
	 * @throws IllegalAccessException if unable to initiate object
	 * @throws InvocationTargetException if unable to initiate object
	 */
	public static EnviromentConfigurationMethod buildFromAnnotatedMethod(Method method, Constructor<?> constructor) throws IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if(method.getDeclaringClass() != constructor.getDeclaringClass())
		{
			throw new ValidationException("method "+method.getName()+" passed to "+EnviromentConfigurationMethod.class.getSimpleName()+" does not belong to the class "+constructor.getDeclaringClass().getSimpleName());
		}
		if( method.getAnnotation(AfterTestCase.class) == null &&  method.getAnnotation(BeforeTestCase.class) == null)
		{
			throw new ValidationException("method "+method.getName()+" passed to "+EnviromentConfigurationMethod.class.getSimpleName()+" is not annotated with either "+BeforeTestCase.class.getName()+ " or "+AfterTestCase.class.getName());
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
