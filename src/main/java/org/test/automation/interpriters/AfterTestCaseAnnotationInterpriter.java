package org.test.automation.interpriters;


import java.util.HashSet;
import java.util.Set;

import org.test.automation.annotations.AfterTestCase;
import org.test.automation.core.EnviromentConfigurationMethod;
 
public class AfterTestCaseAnnotationInterpriter extends EnviromentConfigurationAnnotationInterpriter {
	
	private static final Set<EnviromentConfigurationMethod> calls = new HashSet<EnviromentConfigurationMethod>();

    static 
    {
        calls.addAll(getMethods(AfterTestCase.class));
    }
    
    static Set<EnviromentConfigurationMethod> getCalls()
    {
    	Set<EnviromentConfigurationMethod> calls =  new HashSet<EnviromentConfigurationMethod>();
    	calls.addAll(AfterTestCaseAnnotationInterpriter.calls);
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