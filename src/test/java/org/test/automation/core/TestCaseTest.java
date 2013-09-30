package org.test.automation.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.automation.tests.classes.TestBeforeAfter;
import org.test.automation.tests.classes.TestClass;


public class TestCaseTest {

	public PrintStream ps; 
	
	private int count;
	
	@Before
	public void setup()
	{
		ps = System.out;
		count = TestClass.createdCount;
	}
	
	@After
	public void tearDown()
	{
		System.setOut(ps);
		TestClass.createdCount = count;
		TestClass.callCount=0;
		TestBeforeAfter.setUpCalls = 0;
		TestBeforeAfter.tearDownCalls = 0;
	}
	
	@Test
	public void testTestCase_runMethods() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos); 
		TextCallMethod callMethod =  TextCallMethod.buildFromAnnotatedMethod(TestClass.class.getMethod("click", String.class), TestClass.class.getConstructors()[0]);
		System.setOut(ps);
		TestCall testCall = new TestCall("click \"testing\"",callMethod);
		TestCall testCall2 = new TestCall("click \"ing\"", callMethod);
		TestCase testCase = new TestCase("test", Arrays.asList(testCall, testCall2));
		
		testCase.runTestCase();
		
		String output = new String(baos.toByteArray());
		
		Assert.assertThat(output, CoreMatchers.is(CoreMatchers.equalTo("click test123\nclick ing\n")));
		
	}
	
}
