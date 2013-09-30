package org.test.automation.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


class PropertyFileHelper {
	
	private static final Logger LOGGER = Logger.getLogger(PropertyFileHelper.class);
	
	private static Map<String, Properties> loadedFiles = new HashMap<String, Properties>();

	public static void loadProperties(Properties properties, String propertiesFileLocation) throws IOException
	{
		LOGGER.info("getting properties for file "+propertiesFileLocation);
		Properties loadedProperties;
		if(!loadedFiles.containsKey(propertiesFileLocation))
		{
			LOGGER.info("loading "+propertiesFileLocation+" from classpath");
			InputStream in = PropertyFileHelper.class.getResourceAsStream(propertiesFileLocation);
			loadedProperties = new Properties();
			loadedProperties.load(in);	
			loadedFiles.put(propertiesFileLocation, loadedProperties);
		}else
		{
			LOGGER.info("using preloaded properties for file "+propertiesFileLocation);
			loadedProperties = loadedFiles.get(propertiesFileLocation);
		}
		
		properties.putAll(loadedProperties);
	}

}
