package org.test.automation.exceptions;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1;

	public ValidationException(String msg)
	{
		super(msg);
	}
}
