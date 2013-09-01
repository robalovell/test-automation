package org.test.automation.interpriters;


import java.util.HashSet;
import java.util.Set;

import org.test.automation.annotations.BeforeTestCase;
import org.test.automation.core.EnviromentConfigurationMethod;
 
public class BeforeTestCaseAnnotationInterpriter extends EnviromentConfigurationAnnotationInterpriter {
	
	private static final Set<EnviromentConfigurationMethod> calls = new HashSet<EnviromentConfigurationMethod>();

    static 
    {
        calls.addAll(getMethods(BeforeTestCase.class));   
    }
    
    static Set<EnviromentConfigurationMethod> getCalls()
    {
    	Set<EnviromentConfigurationMethod> calls =  new HashSet<EnviromentConfigurationMethod>();
    	calls.addAll(BeforeTestCaseAnnotationInterpriter.calls);
    	return calls;
    }
    
    
    public static void runAllMethods()
    {
    	for (EnviromentConfigurationMethod method : calls)
		{
    		method.callMethod();
		}
    }
}