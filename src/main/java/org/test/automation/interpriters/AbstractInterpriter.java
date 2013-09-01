package org.test.automation.interpriters;

import java.lang.reflect.Constructor;

import org.test.automation.annotations.AutomatedTestConstructer;
import org.test.automation.exceptions.ValidationException;


abstract class AbstractInterpriter {


	protected static Constructor<?> getConstructor(Class<?> clazz)
	{
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
		return constructor;
	}
}
