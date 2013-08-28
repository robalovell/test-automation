package core;

import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;


public class AutomatedTestCaseRunnerTest {

	@Test
	public void testGetAllFiles()
	{
		AutomatedTestCaseRunner runner = new AutomatedTestCaseRunner();
		Set<TestFile> files = runner.fileAllFilesToExcute();
		Assert.assertThat(files.size(), CoreMatchers.is(CoreMatchers.equalTo(2)));
	}
}
