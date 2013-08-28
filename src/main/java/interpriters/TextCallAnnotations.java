package interpriters;

import interpriters.util.ConstructorParmeterHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.DefaultResourceLoader;

import annotations.AutomatedTestConstructer;
import annotations.TextCall;
import core.TextCallMethod;
import exceptions.ValidationException;
 
public class TextCallAnnotations {
	
	private static final Set<TextCallMethod> calls = new HashSet<TextCallMethod>();

	private final static Logger LOGGER = Logger.getLogger(TextCallAnnotations.class);
 
    static 
    {
        String topPackage = System.getProperty("test.annotation.package", "tests");
        LOGGER.info("search package "+topPackage);
    	final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
		        false);

		scanner.setResourceLoader(new DefaultResourceLoader(TextCallAnnotations.class.getClassLoader()));
		scanner.addIncludeFilter(new AnnotationIncludeFilter(TextCall.class));
		final Set<BeanDefinition> findCandidateComponents = scanner.findCandidateComponents(topPackage);

		final Set<Class<?>> requestModelClasses = convertBeanDefinitions(findCandidateComponents);

        LOGGER.info(requestModelClasses.size()+ " classes with the TextCall annotation");
    	
        for (Class<?> clazz : requestModelClasses) {
        	
        	for (Method method : clazz.getMethods()) 
        	{
				if(method.getAnnotation(TextCall.class)!=null)
				{
					TextCall textCall = method.getAnnotation(TextCall.class);
		        	String patteren = textCall.value();


        			LOGGER.info("found method "+method.getName()+" with pattern "+patteren);
        			
        			Class<?>[] constructerParamTypes;
        			
        			Constructor<?> constructor = null;
        			
        			
        			if(clazz.getConstructors().length>1)
        			{
        				for (Constructor<?> constructor1 : clazz.getConstructors())
						{
        					if(constructor1.getAnnotation(AutomatedTestConstructer.class)!=null)
        					{
        						constructor = constructor1;
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
        			
        			constructerParamTypes = constructor.getParameterTypes();
        			try
					{
						
        			Object object = constructor.newInstance(ConstructorParmeterHelper.getParamsForConstructer(constructerParamTypes));
        			
		        	calls.add(new TextCallMethod(patteren, object, method, textCall.createNewOnEachCall(), constructerParamTypes));
					} catch (InstantiationException e)
					{
						e.printStackTrace();
					} catch (IllegalAccessException e)
					{
						e.printStackTrace();
					} catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					} catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
			}
        }      
    }
    
    static Set<TextCallMethod> getCalls()
    {
    	Set<TextCallMethod> calls =  new HashSet<TextCallMethod>();
    	calls.addAll(TextCallAnnotations.calls);
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
    
    private static Set<Class<?>> convertBeanDefinitions(final Set<BeanDefinition> findCandidateComponents)
	{
		final Set<Class<?>> requestModelClasses = new HashSet<Class<?>>(findCandidateComponents.size());

		for (final BeanDefinition beanDefinition : findCandidateComponents)
		{
			try
			{
				requestModelClasses.add(Class.forName(beanDefinition.getBeanClassName()));
			}
			catch (final ClassNotFoundException e)
			{
			}
		}

		return requestModelClasses;
	}
}