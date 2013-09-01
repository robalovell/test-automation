package org.test.automation.exceptions;


public class EnviromentException extends RuntimeException{
	
	public EnviromentException(String message, Exception cause)
	{
		super(message, cause);
	}

}
