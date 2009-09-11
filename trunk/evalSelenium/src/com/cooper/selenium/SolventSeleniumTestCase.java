/**
 * 
 */
package com.cooper.selenium;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.cooper.selenium.input.DataFileFinder;
import com.cooper.selenium.input.InvalidDataException;
import com.cooper.selenium.input.MissingDataFileException;
import com.cooper.selenium.input.XMLDataFileDigester;

/**
 * All the junit test should extend this abstract class to get an handle to the selenium
 * server.
 * 
 * @author anuradha.uduwage
 *
 */
public abstract class SolventSeleniumTestCase {
	
	private static final Logger log = Logger.getLogger(SolventSeleniumTestCase.class.getName());
	
	private boolean dataFileFound = false;
	
	//this arraylist holds the selenium sessions, at end of the day close them down.
	private final ArrayList<SolventSelenium> sessions = new ArrayList<SolventSelenium>();
	
	//hold input params because all the data file digesters return hashmaps.
	private HashMap<String, Object> inputParams;
	
	/**
	 * Load input data from input file for the test. if an input file is not found or not 
	 * given by the test method will assumed that there is no input required for the given test.
	 */
	@Before
	public void setUp() {
		try {
			inputParams = new HashMap<String, Object>();
			
			//read input parameters if input file is found.
			InputStream in = DataFileFinder.getDataInputFileAsStream(this);
			if (in != null) {
				dataFileFound = true;
				log.info("Found test input file....doing some data digesting... hang on..");
				XMLDataFileDigester digester = new XMLDataFileDigester(in);
				digester.parseInput(inputParams);
			}
		}catch (Exception e) {
			log.error("Something went wrong while parsing input file", e);
		}
	}
	
	/**
	 * Validate the the dataFileFound flag. if it is set to false, then exception will be thrown.
     * This method is used to validate that an Input file was actually parsed, before trying to retrieve parameters.
     * This avoids confusion if the user is trying to retrieve parameters from a file that doesn't exist,
     * or is in the wrong location, or has the wrong name.
	 */
	protected void validateInputData() {
		if(!dataFileFound) {
			log.error("Unable to retrieve requested input parameter, no input files found for the test");
			throw new MissingDataFileException("Unable to retrieve input parameter. no input files" + 
					" found for the test");
		}
	}
	
	/**
	 * If there is a value defined for single-value parameter the method will 
	 * retrieve that value. If matching parameter not found, or if the file doesn't 
	 * exists, the method will return null.
	 * @param name name of the single-value parameter that to be retrieve.
	 * @return Value of the parameter, null if the parameter was not found.
	 */
	protected String getParamString(String name) {
		validateInputData();
		if(inputParams.get(name) == null) {
			throw new InvalidDataException("Input parameter \"" + name + "\" not found)");
		}
		return (String)inputParams.get(name);
	}
	
	/**
	 * If there are values defined for a multi-value parameter the method will 
	 * retrieve those values. If matching parameters not found, or if its a single-value 
	 * method will inform that.
	 * @param name The name of the multi-value parameter.
	 * @return Method returns an array of values defined for the specified parameter, or null, 
	 * or if its a single-value.
	 */
	protected String[] getParamStrings(String name) {
		validateInputData();
		if(inputParams.get(name) == null) {
			throw new InvalidDataException("Input parameter \"" + name + "\" not found)");
		}
		String[] values = null;
		try {
			values = (String[])inputParams.get(name);
		}catch(ClassCastException ex) {
			throw new InvalidDataException("Input parameter \"" + name + "\" is not a multi-value parameter. " +
					"Use the method that accept single parameter getParamString())", ex);
		}
		return values;
	}
	
	/**
	 * Call this method when test are completed to force all the Selenium session to be shutdown. 
	 * This prevents hanging browser windows cause by failures during a test.
	 */
	@After
	public void tearDown() {
		boolean autoClose = Boolean.parseBoolean(SeleniumDefaultProperties.getResourceAsStream("default.autoclose"));
		if(autoClose) {
			for (SolventSelenium session:sessions) {
				//since we really don't know what sessions are all ready close we should catch the exception
				try {
					session.stop();
				}catch(Throwable t) {
					//not the best way since brute force, logs will help
					log.debug("Error thrown attempt of closing session ('not a big deal ignore') : \n" + t.getMessage());
				}
			}
		}
	}
	
	/**
	 * Returns a Selenium session after an authentication action using the default values in .properties 
	 * file for browser.command, username, and password.
	 * @return
	 */
	protected SolventSelenium getAuthenticatedSeleniumSession() {
		return this.getAuthenticatedSeleniumSession(this.getBrowserDefaultCommand(), null, null);
	}
	
	/**
	 * Returns an authenticated selenium session using the browser command which is defined in the 
	 * seleniumdefaults.properties file. based on given username and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	protected SolventSelenium getAuthenticatedSeleniumSession(String username, String password) {
		return this.getAuthenticatedSeleniumSession(this.getBrowserDefaultCommand(), username, password);
	}
	
	protected SolventSelenium getAuthenticatedSeleniumSession(String browserCmd, String username, String password) {
		SolventSelenium selenium = getConfiguredSeleniumSession(browserCmd);
		
		//TODO: bring in login methods in AbstractSeleniumDriver class since the framework has change 
		if(username == null) 
			username = "yukon";
		if(password == null)
			password = "yukon";
		
		String url =  SeleniumDefaultProperties.getResourceAsStream("default.auth.url");
		
		selenium.openAuthentication(username, password, url);
		sessions.add(selenium);
		return selenium;
		
	}
	
	public SolventSelenium getConfiguredSeleniumSession() {
		return getConfiguredSeleniumSession(getBrowserDefaultCommand());
	}
	
	public SolventSelenium getConfiguredSeleniumSession(String browserCommand) {
		//these values should be defined in .properties file. but incase if its not define
		String host = SeleniumDefaultProperties.getResourceAsStream("default.selenium.host");
		if(host.equals(""))
			host = "localhost";
		
		String port = SeleniumDefaultProperties.getResourceAsStream("default.selenium.port"); 
		if(port.equals("")) 
			port = "4444";
		
		String testHostURL = SeleniumDefaultProperties.getResourceAsStream("default.auth.url");
		if(testHostURL.equals(""))
			testHostURL = SeleniumDefaultProperties.getClassInstance().getBaseHREF();
		
		SolventHttpCommandProcessor commandProcessor = 
			new SolventHttpCommandProcessor(host, Integer.parseInt(port), browserCommand, testHostURL);
		SolventSelenium selenium = new SolventSelenium(commandProcessor);
		
		//drivers start your engines.
		selenium.start();
		return selenium;
	}
	
	/**
	 * Method to start a new selenium session. use this when you don't want to start with the test 
	 * with any specifict Solvent and do not need to override any of the defualt session configurations.
	 * @see #start(AbstractSolvent)
	 * @return SolventSelenium = the Selenium session that was freshly created.
	 */
	public SolventSelenium start() {
		start(getAuthenticatedSeleniumSession());
		return SeleniumSession.get();
	}
	/**
	 * This method uses defualt configuration values to start a new session and accepts a 
	 * solvent to start the test with.
	 * @param <X>
	 * @param sol The solvent to start the test with.
	 * @return Solvent that was passed to start with.
	 */
	public <X extends AbstractSolvent> X start(X sol) {
		return start(sol);
	}
	/**
	 * Method that accepts the selenium session object. This allows the user to create the 
	 * start session to their preference, without using the default defined properties.
	 * @param selenium The Selenium session to use for this test.
	 */
	public void start(SolventSelenium selenium) {
		SeleniumSession.set(selenium);
	}
	/**
	 * Starts the selenium test. Calling this method will cause the browser to open and the test 
	 * to begin starting with the Solvent that is passed in as the solventSelenium parameter.
	 * @param <X>
	 * @param solventSelenium An authenticated selenium session
	 * @param sol The solvent that will be used to begin the test.
	 * @return solvent.
	 */
	public <X extends AbstractSolvent> X start(SolventSelenium solventSelenium, X sol) {
		sol.setSeleniumDriver(solventSelenium);
		sol.prepare();
		return sol;
	}

	/**
	 * Retrieves configuration browser command where selenium test should run.
	 * @throws SolventConfigException if not found
	 * @return the value of the selenium.browser.startCommand
	 */
	public String getBrowserDefaultCommand() {
		String bcmd = SeleniumDefaultProperties.getResourceAsStream("default.browser.command");
		if(bcmd.equals(""))
			handleMissingConfigProperties(SeleniumDefaultProperties.getResourceAsStream("default.browser.command"));
		return bcmd;
	}
	
	/**
	 * Method that handle missing Run Configuration properties.
	 * @param propname name of the property that ism missing
	 * @throws SolventConfigException
	 */
	public void handleMissingConfigProperties(String propname) {
		String msg = "Missing requried configuration property \"" + propname + 
			"\". check the properties file and try again.";
		log.error(msg);
		throw new SolventConfigException(msg);
	}
}
