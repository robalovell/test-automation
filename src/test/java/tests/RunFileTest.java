package tests;

import java.io.IOException;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import tests.classes.TestClass;
import core.AutomatedTestCaseRunner;
import freemarker.template.TemplateException;


public class RunFileTest {
	
	@Test
	public void testGetAllFiles() throws IOException, TemplateException, URISyntaxException
	{
		System.setProperty("tags", "CRP-100");
		AutomatedTestCaseRunner runner = new AutomatedTestCaseRunner();
		runner.runTestCases();
		Assert.assertThat(TestClass.callCount, CoreMatchers.is(CoreMatchers.equalTo(2)));
		Assert.assertThat(TestClass.createdCount, CoreMatchers.is(CoreMatchers.equalTo(3)));
		TestClass.callCount=0;
		TestClass.createdCount=0;
	}

}
