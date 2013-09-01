package org.test.automation.tests.classes;

import org.test.automation.annotations.TextCall;


public class TestClass {

	public static int callCount = 0;
	
	public static int createdCount = 0;
	
	public TestClass(String s)
	{
		System.out.println("\n\n\n\n"+s+"\n\n\n\n");
		createdCount++;
	}
	
	@TextCall(value = "click ?")
	public void click(String id){
		callCount++;
		System.out.println("click "+id);
	}

	@TextCall(value = "click ? now",  createNewOnEachCall= true)
	public void clickNow(String id){
		callCount++;
		System.out.println("click "+id+" now");
		throw new RuntimeException("error");
	}
	
}
