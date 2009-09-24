/**
 * 
 */
package com.cooper.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	
	private static SeleniumDefaultProperties instance = null;
	
	public static SeleniumDefaultProperties getClassInstance() {
		if(instance == null)
			instance = new SeleniumDefaultProperties();
		return instance;
	}

	public static String getResourceAsStream(String defaultProp) {
		String defaultPropValue = null;
		//String keys = null;
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
	
	/**
	 * If the code is getting executed on actual server it will user the localhost
	 * since localhost is open and defaults to yukon application but during developement 
	 * developer should set the url in seleniumdefault.properties file.
	 * @return url url string of the host.
	 */
	public String getBaseHREF() {
		String host = getResourceAsStream("default.server.localhost");
		String url = "";
		if(host.equalsIgnoreCase("true"))
			url = "http://localhost:8080/";
		else 
			url = getResourceAsStream("default.auth.url");
		return url;
	}
	
	public static void main (String[] args) {
		//TODO: should take out this method after debug.
		log.info(getResourceAsStream("default.browser.command"));
	}
}
