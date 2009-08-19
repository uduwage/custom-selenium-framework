/**
 * 
 */
package com.cooper.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * TODO: At the moment this class provide the access to the properties file, but 
 * I would like to build a singleton class that access all the properties 
 * from the yukon application. SolventSeleniumTestConfig is start for that.
 * 
 * @author anuradha.uduwage
 *
 */
public class SeleniumDefaultProperties {
	
	private static final Logger log = Logger.getLogger(SeleniumDefaultProperties.class.getName());

	private static final String SELENIUM_PROP_FILE = "seleniumdefault.properties";

	public static String getResourceAsStream(String defaultProp) {
		String defaultPropValue = null;
		String keys = null;
		try {
			InputStream inputStream = SeleniumDefaultProperties.class.getClassLoader().getResourceAsStream(SELENIUM_PROP_FILE);
			Properties properties = new Properties();
			//load the input stream using properties.
			properties.load(inputStream);
			defaultPropValue = properties.getProperty(defaultProp);
			
		}catch (IOException e) {
			log.error("Something wrong with .properties file, check the location.", e);
		}
		return defaultPropValue;
	}
	
	public String getBaseHREF() {
		return "http://pspl-qa008:8080/";
	}
	public static void main (String[] args) {
		log.info(getResourceAsStream("default.browser.command"));
	}
}
