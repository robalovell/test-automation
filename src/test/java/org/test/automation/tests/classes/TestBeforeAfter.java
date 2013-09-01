package org.test.automation.tests.classes;

import org.test.automation.annotations.AfterTestCase;
import org.test.automation.annotations.BeforeTestCase;


public class TestBeforeAfter {
	
	public static int setUpCalls = 0;
	
	public static int tearDownCalls = 0;
	
	@BeforeTestCase
	public void setup()
	{
		setUpCalls++;
	}
	
	@AfterTestCase
	public void tearDown(String text)
	{
		tearDownCalls++;
	}

}
