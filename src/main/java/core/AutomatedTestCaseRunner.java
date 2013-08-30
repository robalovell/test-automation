package core;

import interpriters.TextCallAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class AutomatedTestCaseRunner {

	private static final String TAG_KEY_WORD = "Tag:";

	private static final String SENARIO_KEY_WORD = "Scenario:";

	private static final String TEST_CASE_KEY_WORD = "TestCase:";
	
	private static final Pattern TAG_LINE = Pattern.compile("^"+TAG_KEY_WORD+".*$");

	private static final Pattern SENARIO_LINE = Pattern.compile("^"+SENARIO_KEY_WORD+".*$");

	private static final Pattern TEST_CASE = Pattern.compile("^"+TEST_CASE_KEY_WORD+".*$");
	
	private final Set<String> searchTags;
	
	
	public AutomatedTestCaseRunner()
	{
		Set<String> searchTags = new HashSet<String>();
		String tags = System.getProperty("tags", "");
		for (String tag : tags.split(","))
		{
			if(StringUtils.isNotBlank(tag))
			{
				searchTags.add(tag.trim());
			}
		}
		
		this.searchTags = Collections.unmodifiableSet(searchTags);
	}
	
	
	public void runTestCases() throws IOException, TemplateException, URISyntaxException 
	{
		Set<TestFile> testFiles = fileAllFilesToExcute();
		for (TestFile testFile : testFiles)
		{
			testFile.runTests();
		}
		generateReport(testFiles);
	}

	void generateReport(Set<TestFile> testFiles) throws IOException, TemplateException, URISyntaxException
	{
		
		File file = new File(System.getProperty("user.dir")+"/target/test-automation-report");
		file.mkdirs();
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/");
		Template tpl = cfg.getTemplate(("templates/index.ftl"));
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(new File(file.getAbsolutePath()+"/index.html")));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testFiles", testFiles);
		map.put("linkPreFix", "");
		tpl.process(map, output);
		output.close();
		InputStream css = getClass().getClassLoader().getResourceAsStream("templates/report.css");
		File targeCss = new File(file.getPath()+"/report.css");
		FileCopyUtils.copy(FileCopyUtils.copyToByteArray(css), targeCss);
		
		tpl = cfg.getTemplate(("templates/testResults.ftl"));

		map.put("linkPreFix", "../");
		
		for (TestFile testFile : testFiles)
		{
			File file2 = new File(file.getAbsolutePath()+"/"+testFile.getSenario()+"/index.html");
			file2.getParentFile().mkdirs();
			output = new OutputStreamWriter(new FileOutputStream(file2));
			map.put("file", testFile);
			tpl.process(map, output);
			output.close();
		}
		
	
	}

	Set<TestFile> fileAllFilesToExcute()
	{
		Set<TestFile> testFiles = new HashSet<TestFile>();
		PathMatchingResourcePatternResolver patternResolver =  new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		try
		{
			Resource[] atcFiles = patternResolver.getResources("**/**.atc");
			for (Resource atcFile : atcFiles)
			{
				TestFile file = parseFile(atcFile.getFile());
				if(testFiles.contains(file))
				{
					throw new RuntimeException("Two test Files found with the with the senario " + file.getSenario() );
				}
				if(searchTags.isEmpty() || file.getTags().containsAll(searchTags))
				{
					testFiles.add(file);
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return testFiles;
	}
	
	private TestFile parseFile(final File file) throws IOException
	{
		BufferedReader reader =  new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		List<String> tags = new ArrayList<String>();
		List<TestCase> testCases = new ArrayList<TestCase>();
		String senario = null;
		try
		{
			String line;
			
			while((line = reader.readLine()) != null)
			{
				if(TAG_LINE.matcher(line).matches())
				{
					tags.addAll(readTags(line));
					continue;
				}else if(SENARIO_LINE.matcher(line).matches())
				{
					if(StringUtils.isEmpty(senario))
					{
						senario = readSenario(line);
					}else
					{
						throw new RuntimeException("The file "+file.getPath()+" has more that one Senario key word");
					}
					continue;
				}else if(TEST_CASE.matcher(line).matches())
				{
					testCases.add(readTestCase(line, reader));
					continue;
				}
			}
		}finally
		{
			reader.close();
		}
		return new TestFile(tags, senario, testCases);
	}

	private List<String> readTags(final String line)
	{
		List<String> tags = new ArrayList<String>();
		String tagString = line.replace(TAG_KEY_WORD, "");
		for (String tag : tagString.split(","))
		{
			tags.add(tag.trim());
		}
		
		return tags;
	}

	private String readSenario(final String line)
	{
		return line.replace(SENARIO_KEY_WORD, "");
	}

	private TestCase readTestCase(final String line,final  BufferedReader reader) throws IOException
	{
		String testCaseName = line.replace(TEST_CASE_KEY_WORD, "");
		String command = reader.readLine();
		List<TestCall> methods = new ArrayList<TestCall>();
		while(StringUtils.isNotEmpty(command) && !(TEST_CASE.matcher(command).matches()))
		{
			methods.add(new TestCall(command, TextCallAnnotations.getCall(command)));
			command = reader.readLine();
		}
		return new TestCase(testCaseName, methods);
	}
}
