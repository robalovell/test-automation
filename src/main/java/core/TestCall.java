package core;



public class TestCall {

	
	private final String command;
	
	private final TextCallMethod method;
	
	private TestCallResult result;
	
	TestCall(String command, TextCallMethod method)
	{
		this.command = command;
		this.method = method;
	}
	
	public void runCommand()
	{
		try
		{
			method.callMethod(command);
		} catch (Exception e)
		{
			throw new RuntimeException("unable to process command "+command, e);
		}
	}

	public String getCommand()
	{
		return command;
	}
	
	public boolean hasCommandRun()
	{
		return result!=null;
	}

	public TestCallResult getResult()
	{
		return result;
	}

	public void setResult(TestCallResult result)
	{
		this.result = result;
	}
}
