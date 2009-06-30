/**
 * 
 */
package com.cannon.selenium;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

/**
 * @author a83E1
 *
 */
public class SolventTestCase {

	private static final org.apache.log4j.Logger log = Logger.getLogger(SolventTestCase.class.getName());
	
	//Flag set to true when an input file is found and parse
	private boolean inputFileFound = false;
	
	//used to hold all selenium session opened by the test so that we can close them 
	//all down during exit.
	private final ArrayList<SolventSelenium> session = new ArrayList<SolventSelenium>();
	
	private HashMap<String, Object> inputParams;
	
	/**
	 * Default Constructor
	 */
	public SolventTestCase() {	
	}
	
	@Before
	public void setUp() {
		
	}
	
	/**
	 * Retrieves the value defined for a single-value parameter. if no matching 
	 * parameter is found, or is no file exists, this method return null.
	 * @param name Name of the single-value parameter to retrieve
	 * @return The value defined for the specified parameter, or null if no 
	 * matching parameter can be found.
	 */
	protected String getParameter(String name) {
		//TODO: need to incoperate input file validation to this.
		if(inputParams.get(name) == null) {
			throw new InvalidParameterException("Input parameter \"" + name + "\" not found");
		}
		return (String)inputParams.get(name);
	}
	
	protected String[] getParameters(String name) {
		//TODO: need to incooperate input file validation with this.
		if(inputParams.get(name) == null) {
			throw new InvalidParameterException("Input parameter \"" + name + "\" not found");
		}
		String[] values = null;
		try {
			values = (String[])inputParams.get(name);
		}catch (ClassCastException e) {
			throw new InvalidParameterException("Input parameter \"" + name + "\" does not appear to be a " +
    				"multi-value parameter.  Use the getParameter() method for single-value parameters" + e);
		}
		return values;
	}
	
	@After
	public void tearDown() {
		//boolean autoClose = Boolean.parseBoolean()
	}
}
