/**
 * 
 */
package com.cooper.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * @author a83E1
 *
 */
public class SeleniumSession {

	private static final org.apache.log4j.Logger log = Logger.getLogger(SolventSelenium.class.getName());
	
	private static ThreadLocal<HashMap<String, SolventSelenium>> cache = 
		new ThreadLocal<HashMap<String,SolventSelenium>>() {
		protected synchronized HashMap<String, SolventSelenium> intialValue() {
			return new HashMap<String, SolventSelenium>();
		}
	};
	
	private static ThreadLocal<SolventSelenium> selenium = new ThreadLocal<SolventSelenium>();
	
	/**
	 * Retrieve the current Selenium Session
	 * @return
	 */
	public static SolventSelenium get() {
		return selenium.get();
	}
	
	/**
	 * Set the current Selenium Session
	 * @param instance
	 */
	public static void set(SolventSelenium instance) {
		selenium.set(instance);
	}
	
	public static void end() {
		

	}
	
}
