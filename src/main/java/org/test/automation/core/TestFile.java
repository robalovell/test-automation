package org.test.automation.core;

import java.util.Collections;
import java.util.List;

import org.test.automation.interpriters.AfterTestCaseAnnotationInterpriter;
import org.test.automation.interpriters.BeforeTestCaseAnnotationInterpriter;


public class TestFile {

	private final List<String> tags;
	private final String senario;
	
	private final List<TestCase> testCases;

	TestFile(List<String> tags, String senario, List<TestCase> testCases)
	{
		this.tags = tags;
		this.senario = senario;
		this.testCases =  testCases;
	}

	public List<String> getTags()
	{
		return tags;
	}

	public String getSenario()
	{
		return senario;
	}
	
	
	/**
	 * @return the testCases
	 */
	public List<TestCase> getTestCases()
	{
		return Collections.unmodifiableList(testCases);
	}

	public void runTests()
	{
		for (TestCase testCase : testCases)
		{
			BeforeTestCaseAnnotationInterpriter.runAllMethods();
			testCase.runTestCase();
			AfterTestCaseAnnotationInterpriter.runAllMethods();
		}
	}
	
	public int hashCode()
	{
		return senario.hashCode();
	}
	
	public boolean equals(Object obj)
	{
		boolean equal = false;
		if(obj instanceof TestFile)
		{
			TestFile otherFile = (TestFile) obj;
			equal = otherFile.senario.equals(this.senario);
		}
		return equal;
	}

}