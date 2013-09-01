package org.test.automation.interpriters;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.test.automation.core.EnviromentConfigurationMethod;
import org.test.automation.tests.classes.TestBeforeAfter;

public class AfterTestCaseAnnotationInterpriterTest {

	@After
	public void tearDown()
	{
		TestBeforeAfter.tearDownCalls = 0;
	}
	
	@Test
	public void testGetAnnotatedMethods() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Set<EnviromentConfigurationMethod> callMethods = AfterTestCaseAnnotationInterpriter.getCalls();
		Assert.assertThat(callMethods.size(), CoreMatchers.is(CoreMatchers.equalTo(1)));
		AfterTestCaseAnnotationInterpriter.runAllMethods();
		Assert.assertThat(TestBeforeAfter.tearDownCalls, CoreMatchers.is(CoreMatchers.equalTo(1)));
	}
}
