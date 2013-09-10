package org.test.automation.core;

import org.apache.commons.lang.exception.ExceptionUtils;

/**The result from running a test call
 * @author roblovell
 * 
 */
public final class TestCallResult {

	private final Throwable error;

	TestCallResult(Throwable error)
	{
		this.error = error;
	}

	/**
	 * boolean flag to represent if a command run had no errors
	 * 
	 * @return returns true only if on creation no error was set;
	 */
	public boolean isSuccesfull()
	{
		return error == null;
	}

	/**
	 * The full stack trace of the error if any was set
	 * 
	 * @return the stack trace of the error, if no error set an empty string is returned
	 */
	public String getStackTrace()
	{
		String trace = "";
		if (error != null)
		{
			trace = ExceptionUtils.getFullStackTrace(error);
		}
		return trace;
	}

}
