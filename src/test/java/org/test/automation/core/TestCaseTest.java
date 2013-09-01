package org.test.automation.core;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.InOrder;


public class TestCaseTest {

	@Test
	public void testTestCase_runMethods()
	{
		TestCall testCall = mock(TestCall.class);
		TestCall testCall2 = mock(TestCall.class);
		Throwable error = new RuntimeException();
		doThrow(error).when(testCall2).runCommand();
		TestCase testCase = new TestCase("test", Arrays.asList(testCall, testCall2));
		
		testCase.runTestCase();
		InOrder order = inOrder(testCall, testCall2);
		order.verify(testCall).runCommand();
		order.verify(testCall2).runCommand();
		
	}
	
}
