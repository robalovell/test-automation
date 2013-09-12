package org.test.automation.core.util;

import org.apache.commons.lang.StringUtils;


/**<p>Helper class that wraps useful configuration properties allowing you to use the test automation framework 
 * with you helper classes.</p>
 * 
 * <p>These can all be set using -D when you start the jvm each set method has the full property name listed in
 *  this doc</p>
 * 
 * @author roblovell
 *
 */
public final class TestAutomationProperties {
	
	private TestAutomationProperties()
	{}
	
	private static final String CONTEXT_PROPERTY = "test.annotation.context";
	
	private static final String TEST_HELPER_PACKAGE = "test.annotation.package";
	
	private static final String TAGS = "test.automation.tags";

	private static final String DATE_FORMAT = "test.automation.date.format";
	
	/**<p>This property allows you to change the regex used to find the application context 
	 * used for params injection.</p>
	 * 
	 * <p>The property that is set is <font color='blue'>test.annotation.context</font>.</p>
	 * 
	 * @param path the new regex path of the context file to be used 
	 */
	public static void setAutoInjectionContextPath(String path)
	{
		System.setProperty(CONTEXT_PROPERTY, path);
	}
	
	/**This returns the value of the property <font color='blue'>test.annotation.context</font> when 
	 * this has not been set it defaults to "**{@literal /}*Context.xml".
	 * 
	 * @return the value of <font color='blue'>test.annotation.context</font> or "**{@literal /}*Context.xml".
	 */
	public static String getAutoInjectionContextPath()
	{
		return System.getProperty(CONTEXT_PROPERTY, "**/*Context.xml");
	}
	
	/**<p>This property allows you to change the base package that will be inspected for the helper classes.</p>
	 * 
	 * <p>The property that is set is <font color='blue'>test.annotation.package</font>.</p>
	 * 
	 * <p>This package needs to be the full package i.e. "org.apache"</p>
	 * 
	 * @param packageName the name of the base package 
	 */
	public static void setTestHelperClassBasePackage(String packageName)
	{
		System.setProperty(TEST_HELPER_PACKAGE, packageName);
	}
	
	/**This returns the value of the property <font color='blue'>test.annotation.package</font> when 
	 * this has not been set it defaults to "org.test.automation".
	 * 
	 * @return the value of <font color='blue'>test.annotation.package</font> or "org.test.automation".
	 */
	public static String getTestHelperClassBasePackage()
	{
		return System.getProperty(TEST_HELPER_PACKAGE, "org.test.automation");
	}
	
	/**<p>This property allows you to change list of filter tags that are being used to find test scenarios.</p>
	 * 
	 * <p>The property that is set is <font color='blue'>test.automation.tags</font>.</p>
	 * 
	 * <p>the tags should be comma separated i.e. "first,last"
	 * 
	 * @param tags a comma separated lists of tags
	 */
	public static void setTagToRun(String tags)
	{
		System.setProperty(TAGS, tags);
	}

	/**<p>This allows you to add a single tag to a list that has already been set in {@link #setTagToRun(String)}.
	 *   If no list has been set it will start one for you.</p>
	 * 
	 * @param tag a single tag to be added to the list
	 */
	public static void addTagToRun(String tag)
	{
		String tags = getTagToRun();
		if(StringUtils.isBlank(tags))
		{
			tags=tag;
		}else
		{
			StringBuilder builder = new StringBuilder(tags);
			builder.append(",");
			builder.append(tag);
			tags = builder.toString();
		}
		System.setProperty(TAGS, tags);
	}
	
	/**This returns the value of the property <font color='blue'>test.automation.package</font>.
	 * 
	 * @return the value of <font color='blue'>test.automation.tags</font> .
	 */
	public static String getTagToRun()
	{
		return System.getProperty(TAGS);
	}
	
	/**<p>This property allows you to change the format of a date passed in as a param.</p>
	 * 
	 * <p>The property that is set is <font color='blue'>test.automation.date.format</font>.</p>
	 * 
	 * <p>This must be a valid date format see {@link java.text.SimpleDateFormat}"</p>
	 * 
	 * @param format
	 */
	public static void setDateFormat(String format)
	{
		System.setProperty(DATE_FORMAT, format);
	}
	
	/**This returns the value of the property <font color='blue'>test.automation.date.format</font> when 
	 * this has not been set it defaults to "HH:mm:ss dd/MM/yyyy".
	 * 
	 * @return the value of <font color='blue'>test.automation.date.format</font> or "HH:mm:ss dd/MM/yyyy".
	 */
	public static String getDateFormat()
	{
		return System.getProperty(DATE_FORMAT, "HH:mm:ss dd/MM/yyyy");
	}

}
