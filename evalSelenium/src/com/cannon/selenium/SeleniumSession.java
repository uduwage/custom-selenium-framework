/**
 * 
 */
package com.cannon.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * @author a83E1
 *
 */
public class SeleniumSession {

	private static final org.apache.log4j.Logger log = Logger.getLogger(AbstractSeleniumDriver.class.getName());
	
	private static ThreadLocal<HashMap<String, AbstractSeleniumDriver>> cache = 
		new ThreadLocal<HashMap<String,AbstractSeleniumDriver>>() {
		protected synchronized HashMap<String, AbstractSeleniumDriver> intialValue() {
			return new HashMap<String, AbstractSeleniumDriver>();
		}
	};
	
	private static ThreadLocal<AbstractSeleniumDriver> selenium = new ThreadLocal<AbstractSeleniumDriver>();
	
	/**
	 * Retrieve the current Selenium Session
	 * @return
	 */
	public static AbstractSeleniumDriver get() {
		return selenium.get();
	}
	
	/**
	 * Set the current Selenium Session
	 * @param instance
	 */
	public static void set(AbstractSeleniumDriver instance) {
		selenium.set(instance);
	}
	
	public static void end() {

	}
	
}
