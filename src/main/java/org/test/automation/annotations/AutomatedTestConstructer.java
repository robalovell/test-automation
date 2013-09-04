package org.test.automation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**<p>
 * The AutomatedTestConstructer annotation is to be used when your test helper class has more than one constructor that is public.
 * It allows you to define which one the framework should use if there is only one public constructor it will 
 * default to this and there is no need to use this annotation.</p>
 * <p>
 * The constructor can be a default i.e. (no parameters) or take in a list of parameters these are then 
 * injected using spring context files based on the class type the 
 * context file can have any name complying with the standard *Context.xml but can be changed using 
 * the property helper TestAutmationProperties.setAutoInjectionContextPath().
 * </p>
 * <p>
 * This annotation should only be added once to any class if not the first method found will be used.</p>
 * 
 * 
 * @author roblovell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.CONSTRUCTOR})
public @interface AutomatedTestConstructer {

	
}
