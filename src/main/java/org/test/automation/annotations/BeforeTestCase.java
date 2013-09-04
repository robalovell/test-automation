package org.test.automation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <p>The BeforeTestCase annotation is designed to give you the ability run a setup method before every tests 
 * case in order to set your test environment up correctly before the running of every test case.</p> 
 * 
 * <p>This method can take in parameters if required that will be auto inject using spring context 
 * files.  The injected parameter is based on there class type.  The context file can have any name complying
 * with the standard *Context.xml but can be changed using the property helper 
 * TestAutmationProperties.setAutoInjectionContextPath().</p>
 * 
 * 
 * @author roblovell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeTestCase {
	

}
