package org.test.automation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



/**This Annotation allows you to pass variables that are property keys 
 * instead of the actual value entered it will lookup  variables from the property 
 * list and return the value enter if no property is found.
 * @author roblovell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface VariablesAsProperties {
	
	
	/**The property file that you want to use for the method
	 * @return full classpath to property file
	 */
	String properties();

}
