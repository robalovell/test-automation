package org.test.automation.tests;

import java.io.IOException;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.automation.core.AutomatedTestCaseRunner;
import org.test.automation.core.util.TestAutomationProperties;
import org.test.automation.tests.classes.TestBeforeAfter;
import org.test.automation.tests.classes.TestClass;

import freemarker.template.TemplateException;


public class RunFileTest {
	
	@Before
	public void setUp()
	{
		TestClass.callCount=0;
		TestBeforeAfter.setUpCalls = 0;
		TestBeforeAfter.tearDownCalls = 0;
	}
	
	@After
	public void tearDown()
	{
		TestAutomationProperties.setTagToRun("");
		TestClass.callCount=0;
		TestClass.createdCount=0;
		TestBeforeAfter.setUpCalls = 0;
		TestBeforeAfter.tearDownCalls = 0;
	}
	
	@Test
	public void testGetAllFiles() throws IOException, TemplateException, URISyntaxException
	{
		TestAutomationProperties.setTagToRun("CRP-100");
		AutomatedTestCaseRunner runner = new AutomatedTestCaseRunner();
		runner.runTestCases();
		Assert.assertThat(TestClass.callCount, CoreMatchers.is(CoreMatchers.equalTo(2)));
		Assert.assertThat(TestClass.createdCount, CoreMatchers.is(CoreMatchers.equalTo(3)));
		Assert.assertThat(TestBeforeAfter.setUpCalls, CoreMatchers.is(CoreMatchers.equalTo(2)));
		Assert.assertThat(TestBeforeAfter.tearDownCalls, CoreMatchers.is(CoreMatchers.equalTo(2)));
		
	}

}
