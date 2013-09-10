package org.test.automation.core;



/**This describes a class that is a wrapper calling a method that has been annotated 
 * @author roblovell
 *
 */
public interface ProxyMethodCall {
	
	
	/**
	 * This will call the actual method required passed into the builder
	 */
	void callMethod();

}
