package org.test.automation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <p>The AfterTestCase annotation is designed to give you the ability run a tear down method after every tests 
 * case in order for your test environment to be cleaned correctly after the running of every test case.</p> 
 * 
 * <p>This method can take in parameters if required that will be auto inject using spring context 
 * files.  The injected parameter is based on there class type.  The context file can have any name complying
 * with the standard *Context.xml but can be changed using the property helper 
 * {@link org.test.automation.core.util.TestAutomationProperties#setAutoInjectionContextPath(String)}.</p>
 * 
 * 
 * @author roblovell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterTestCase {
	

}
