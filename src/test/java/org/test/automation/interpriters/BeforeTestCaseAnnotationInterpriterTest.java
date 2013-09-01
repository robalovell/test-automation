package org.test.automation.interpriters;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.test.automation.core.EnviromentConfigurationMethod;
import org.test.automation.tests.classes.TestBeforeAfter;

public class BeforeTestCaseAnnotationInterpriterTest {

	
	@After
	public void tearDown()
	{
		TestBeforeAfter.setUpCalls = 0;
	}
	
	@Test
	public void testGetAnnotatedMethods() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Set<EnviromentConfigurationMethod> callMethods = BeforeTestCaseAnnotationInterpriter.getCalls();
		Assert.assertThat(callMethods.size(), CoreMatchers.is(CoreMatchers.equalTo(1)));
		BeforeTestCaseAnnotationInterpriter.runAllMethods();
		Assert.assertThat(TestBeforeAfter.setUpCalls, CoreMatchers.is(CoreMatchers.equalTo(1)));
	}

}
