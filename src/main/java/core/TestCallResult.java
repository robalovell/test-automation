package core;

import org.apache.commons.lang.exception.ExceptionUtils;



public class TestCallResult {	
	private final Throwable error;
	
	TestCallResult(Throwable error)
	{
		this.error = error;
	}
	
	public  boolean isSuccesfull()
	{
		return error == null;
	}
	
	public String getStackTrace()
	{
		return ExceptionUtils.getFullStackTrace(error);
	}

}
