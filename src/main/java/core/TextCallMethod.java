package core;

import interpriters.util.ConstructorParmeterHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.Arrays;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import exceptions.ValidationException;

public class TextCallMethod implements Cloneable
{	

	private final static Logger LOGGER = Logger.getLogger(TextCallMethod.class);

	private static final String SPACE = " ";
	
	private static final String SPLIT_CHAR = "\"";
	
	private static final String PARAMETER_MATCHER = "?";
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(System.getProperty("date.format", "HH:mm:ss dd/MM/yyyy"));
	
	private final String regex;
	
	private final Object object;
	
	private final Class[] constructorClasses;
	
	private final Method method;
	
	private final Pattern pattern;
	
	private boolean createNew;
	
	public TextCallMethod(String regex, Object object, Method method, boolean createNew, Class[] constructorClasses) {
		this.regex = regex;
		this.object = object;
		this.method = method;
		this.createNew = createNew;
		this.pattern = Pattern.compile("^"+regex.toLowerCase().replace("?", SPLIT_CHAR+".*"+SPLIT_CHAR)+"$");
		this.constructorClasses = constructorClasses;
		int count = StringUtils.countOccurrencesOf(regex, PARAMETER_MATCHER);
		if(method.getParameterTypes().length != count)
		{
			StringBuilder builder = new StringBuilder()
			.append("the Method ")
			.append(method.getClass().getSimpleName())
			.append(".")
			.append(method.getName())
			.append(" takes in ")
			.append(method.getParameterTypes().length)
			.append(" parameters but the annotation for it take in ")
			.append(count)
			.append(" these must be the same for the test cases to be able to call the method");
			throw new ValidationException(builder.toString());
		}
	}
	
	public boolean matchMethod(String text) {
		return pattern.matcher(text.trim().toLowerCase()).matches();
	}

	public void callMethod(String text) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, ParseException, SecurityException, NoSuchMethodException
	{
		if(matchMethod(text))
		{
			if(!(regex.contains("?")))
			{
				method.invoke(object);
			}else
			{
				String[] params = getMethodParamsFromText(text);

				LOGGER.info("params found "+Arrays.toString(params) + " from text call "+text);
				Object[] convertedParams = convertParamsToCorrectType(params);
				Object objectToCall = object;
				if(createNew)
				{
					if(constructorClasses != null && constructorClasses.length>0)
					{
						objectToCall = object.getClass().getConstructor(constructorClasses).newInstance(ConstructorParmeterHelper.getParamsForConstructer(constructorClasses));
					}else
					{
						objectToCall = object.getClass().newInstance();
					}
				}
				LOGGER.info("calling method "+method.getName() + " with params "+Arrays.toString(convertedParams));
				method.invoke(objectToCall, convertedParams);
			}
		}
		else
		{
			throw new IllegalArgumentException("the string '"+text+"' does not match the regex '"+regex+"'");
		}
	}

	private String[] getMethodParamsFromText(String text)
	{
		String[] regexSlipt = regex.trim().split(SPACE);
		String[] textSlipt = text.split(SPLIT_CHAR);
		String[] params = new String[method.getParameterTypes().length];
		int paramCount = 0;
		int regexCount = 0;
		for (int i = 0; i<textSlipt.length;i++)
		{
			String regexWord  = regexSlipt[regexCount];
			String word  = textSlipt[i];
			while (!regexWord.contains(PARAMETER_MATCHER) && regexCount<regexSlipt.length-1)
			{
				regexCount = Math.min(++regexCount, regexSlipt.length-1);
				regexWord+=" "+regexSlipt[regexCount];
			}
			LOGGER.info("regex '"+regexWord+"' word '"+word+"'");
			if(PARAMETER_MATCHER.equals(regexWord))
			{
				
				while(textSlipt.length-1 > i 
						&& (
								regexSlipt.length == regexCount 
								|| !(textSlipt[i+1].trim().split(SPACE)[0].equalsIgnoreCase(regexSlipt[regexCount+1]))
							)
					)
				{
					i++;
					word+='"'+textSlipt[i];
				}
				regexCount = Math.min(++regexCount, regexSlipt.length-1);
				params[paramCount++] = word;
			}
		}
		return params;
	}
	
	private Object[] convertParamsToCorrectType(String[] params) throws InstantiationException, IllegalAccessException, ParseException
	{
		Object[] convertedParams = new Object[params.length];
		for (int i=0; i<params.length;i++)
		{
			Class<?> paramType = method.getParameterTypes()[i];
			if(paramType == Integer.class || paramType == int.class)
			{
				convertedParams[i] = Integer.parseInt(params[i]);
			}
			else if (paramType == Long.class || paramType == long.class)
			{
				convertedParams[i] = Long.parseLong(params[i]);
			}
			else if (paramType == Double.class || paramType == double.class)
			{
				convertedParams[i] = Double.parseDouble(params[i]);
			}
			else if (paramType == Float.class || paramType == float.class)
			{
				convertedParams[i] = Float.parseFloat(params[i]);
			}
			else if (paramType == Character.class || paramType == char.class)
			{
				convertedParams[i] = params[i].toString().charAt(0);
			}
			else if (paramType == BigDecimal.class)
			{
				convertedParams[i] = new BigDecimal(params[i]);
			}
			else if (paramType == BigInteger.class)
			{
				convertedParams[i] = new BigInteger(params[i]);
			}
			else if (paramType.isEnum())
			{
				Class<Enum> enumClazz = (Class<Enum>)paramType;
				convertedParams[i] =  enumClazz.newInstance().valueOf(enumClazz, params[i]);
			}
			else if(paramType == Calendar.class)
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(SIMPLE_DATE_FORMAT.parse(params[i]));
				convertedParams[i] =  calendar;
			}
			else if(paramType == Date.class)
			{
				convertedParams[i] =  SIMPLE_DATE_FORMAT.parse(params[i]);
			}else
			{
				convertedParams[i]  = params[i];
			}
		}

		return convertedParams;
	}
	
}