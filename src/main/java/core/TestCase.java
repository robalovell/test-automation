package core;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;



public class TestCase {
	
	private final static Logger LOGGER = Logger.getLogger(TestCase.class);
	
	private final String name;
	
	private final List<TestCall> methods;


	TestCase(final String name, final List<TestCall> methods)
	{
		this.name = name;
		this.methods = Collections.unmodifiableList(methods);
	}

	public String getName()
	{
		return name;
	}
	
	public List<TestCall> getMethods()
	{
		return Collections.unmodifiableList(methods);
	}

	public void runTestCase()
	{
		LOGGER.trace("ENTER:runTestCase()");
		for (TestCall method : methods)
		{
			Throwable error = null;
			try
			{
				method.runCommand();
			}catch(Throwable e)
			{
				LOGGER.error("ERROR:runTestCase() "+e.getMessage(), e);
				error = e;
			}
			TestCallResult result = new TestCallResult(error);
			
			method.setResult(result);
			if(!result.isSuccesfull())
			{
				break;
			}

		}
		LOGGER.trace("EXIT:runTestCase()");
	}

}
