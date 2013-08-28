package interpriters;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import tests.classes.TestClass;
import core.TextCallMethod;

public class TextCallAnnotationsTest {

	@Test
	public void testGetAnnotatedMethods()
	{
		Set<TextCallMethod> callMethods = TextCallAnnotations.getCalls();
		Assert.assertThat(callMethods.size(), CoreMatchers.is(CoreMatchers.equalTo(2)));
		boolean foundMatch = false;
		for (TextCallMethod textCallMethod : callMethods) {
			foundMatch =  textCallMethod.matchMethod("click \"here\"");
			if(foundMatch)
			{
				break;
			}
		}
		Assert.assertThat(foundMatch, CoreMatchers.is(CoreMatchers.equalTo(true)));
	}
	
	@Test
	public void testGetAnnotatedMethodsFoundWithSpeachmarksInItString()
	{
		Set<TextCallMethod> callMethods = TextCallAnnotations.getCalls();
		boolean foundMatch = false;
		for (TextCallMethod textCallMethod : callMethods) {
			foundMatch =  textCallMethod.matchMethod("click \"he\" \"re\"");
			if(foundMatch)
			{
				break;
			}
		}
		Assert.assertThat(foundMatch, CoreMatchers.is(CoreMatchers.equalTo(true)));
	}
	

	@Test
	public void testGetAnnotatedMethodWithWordAfterFreeText()
	{

		Set<TextCallMethod> callMethods = TextCallAnnotations.getCalls();
		boolean foundMatch = false;
		for (TextCallMethod textCallMethod : callMethods) {
			foundMatch =  textCallMethod.matchMethod("click \"he\" \"re\" now");
			if(foundMatch)
			{
				break;
			}
		}
		Assert.assertThat(foundMatch, CoreMatchers.is(CoreMatchers.equalTo(true)));
	}

	@Test
	public void testGetAnnotatedMethodsNotFoundForIncorrectString()
	{
		Set<TextCallMethod> callMethods = TextCallAnnotations.getCalls();
		boolean foundMatch = false;
		for (TextCallMethod textCallMethod : callMethods) {
			foundMatch =  textCallMethod.matchMethod("click \"he\" \"re\" fail");
			if(foundMatch)
			{
				break;
			}
		}
		Assert.assertThat(foundMatch, CoreMatchers.is(CoreMatchers.equalTo(false)));
	}

	
	@Test
	public void testCallMethod() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, ParseException, SecurityException, NoSuchMethodException
	{
		Set<TextCallMethod> callMethods = TextCallAnnotations.getCalls();
		for (TextCallMethod textCallMethod : callMethods) {
			if(textCallMethod.matchMethod("click \"here\""))
			{
				textCallMethod.callMethod("click \"here\"");
			}
		}
		Assert.assertThat(TestClass.callCount, CoreMatchers.is(CoreMatchers.equalTo(1)));
		TestClass.callCount=0;
	}

}
