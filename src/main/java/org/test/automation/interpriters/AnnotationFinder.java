package org.test.automation.interpriters;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.DefaultResourceLoader;


final class AnnotationFinder {

	private final static Logger LOGGER = Logger.getLogger(AnnotationFinder.class);

	
	static final  Set<Class<?>> getClassesForAnnotation(Class<? extends Annotation> annotation)
	{
		String topPackage = System.getProperty("test.annotation.package", "org.test.automation");
        LOGGER.info("search package org.test.automation."+topPackage);
    	final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
		        false);

		scanner.setResourceLoader(new DefaultResourceLoader(TextCallAnnotationsInterpriter.class.getClassLoader()));
		scanner.addIncludeFilter(new AnnotationIncludeFilter(annotation));
		final Set<BeanDefinition> findCandidateComponents = scanner.findCandidateComponents(topPackage);

		final Set<Class<?>> requestModelClasses = convertBeanDefinitions(findCandidateComponents);

        LOGGER.info(requestModelClasses.size()+ " classes with the TextCall annotation");
		return requestModelClasses;
	}
	
	static final boolean hasAnnotation(AnnotatedElement annotatedElement,Class<? extends Annotation> annotation)
	{
		return annotatedElement.getAnnotation(annotation)!=null;
	}
	

    private static Set<Class<?>> convertBeanDefinitions(final Set<BeanDefinition> findCandidateComponents)
	{
		final Set<Class<?>> classes = new HashSet<Class<?>>(findCandidateComponents.size());

		for (final BeanDefinition beanDefinition : findCandidateComponents)
		{
			try
			{
				classes.add(Class.forName(beanDefinition.getBeanClassName()));
			}
			catch (final ClassNotFoundException e)
			{
			}
		}

		return classes;
	}
}
