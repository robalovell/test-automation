package org.test.automation.interpriters;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.test.automation.annotations.AutomatedTestConstructer;
import org.test.automation.exceptions.ValidationException;


abstract class AbstractInterpriter {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractInterpriter.class);
	
	protected static Constructor<?> getConstructor(Class<?> clazz)
	{
		LOGGER.info("getting the correct constructor from class "+clazz.getName()); 
		Constructor<?> constructor = null;
		if(clazz.getConstructors().length>1)
		{
			for (Constructor<?> possibleConstructor : clazz.getConstructors())
			{
				if(AnnotationFinder.hasAnnotation(possibleConstructor, AutomatedTestConstructer.class))
				{
					constructor = possibleConstructor;
					break;
				}
			}
			if(constructor==null)
			{
				throw new ValidationException("The class "+clazz.getName()+" has more than one constructer of which none are annotated with "+AutomatedTestConstructer.class.getName());
			}
		}else
		{
			constructor = clazz.getConstructors()[0];
		}
		LOGGER.info("found constructor for class "+clazz.getName()); 
		return constructor;
	}
}
