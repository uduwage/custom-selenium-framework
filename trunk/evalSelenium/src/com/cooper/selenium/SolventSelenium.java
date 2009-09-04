/**
 * 
 */
package com.cooper.selenium;

import org.apache.log4j.Logger;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.HttpCommandProcessor;
import com.thoughtworks.selenium.SeleniumException;

/**
 * SolventSelenium provide access to DefualtSelenium class by extending it.
 * Two constructors to accept instance of SolventHttpCommandProcessor and Default
 * command processor.
 * 
 * @author anuradha.uduwage
 *
 */
public class SolventSelenium extends DefaultSelenium {

	
	private static final Logger log = Logger.getLogger(SolventSelenium.class.getName());
	
	private String baseURL = null;
	/**
	 * Constructor that accept customized instance of the command processor.
	 * @param processor
	 */
	public SolventSelenium(SolventHttpCommandProcessor processor) {
		super(processor);
		baseURL = processor.getBaseUrl();
	}
	
	public SolventSelenium(HttpCommandProcessor processor) {
		super(processor);
	}

	/**
	 * Constructor that sets server startup values. 
	 * 
	 * @param serverHost host name of the server.
	 * @param serverPort address of the selenium port.
	 * @param browserStartCommand default browswer command.
	 * @param browserURL default browser url.
	 */
	public SolventSelenium(String serverHost, int serverPort, 
			String browserStartCommand, String browserURL) {
		super(serverHost, serverPort, browserStartCommand, browserURL);
		baseURL = browserURL;
	}
	
	public void openAuthentication(String username, String password, String location) {
		String url = getURLForAuthentication(username, password, location);
		setTimeout(SeleniumDefaultProperties.getResourceAsStream("default.pageload.timeout"));
		open(url);
		
	}
	/**
	 * TODO: Right now its working because of the hack, really need to clean it up 
	 * and add more robustness to the method.
	 * 
	 * @param user
	 * @param password
	 * @param location
	 * @return
	 */
	public String getURLForAuthentication(String user, String password, String location) {
		String host = baseURL + location;
		if(location.indexOf("/") == 0 && baseURL.endsWith("/"))
			host = baseURL + location.substring(1);
		if(location.indexOf("/") != 0 && !baseURL.endsWith("/"))
			host = baseURL + "/" + location;
		String url = host.replaceFirst("://", "//" + user.trim() + "@");
		if(url.indexOf("?") > 0 && url.indexOf("?") + 1 < url.length()) {
			url += "&forceTrail=true";
		}else {
			url += "forceTrail=true";
		}
		return url = location; // this is a hack.
	}

	/**
	 * Simple pause method with a try/catch for the InterruptedException;
	 * only here to keep the try/catch block out of the test code.
	 * @param milliseconds
	 */
	public void pause(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}catch(InterruptedException ex) {
			log.error("Please wait its interrupted", ex);
			throw new SeleniumException("pause() interrupted! \n" + ex.getMessage(), ex);
		}
	}
	
	/**
	 * Method used to wait for an element to appear on the page before continuing.
	 * @param locator
	 * @param wait
	 */
	public void waitForElement(String locator, int wait) {
		//TODO:Should this be configurable?
		boolean present = isElementPresent(locator, 0);
		while(!present && wait > 0) {
			pause(500);
			wait -= 500;
			present = isElementPresent(locator, 0);
		}
		if(!present) {
			String messageString = "Timeout while waiting for element " + locator + " to appear";
			log.error(messageString);
			throw new SeleniumException(messageString);
		}
	}
	
	public void waitForElement(String locator) {
		waitForElement(locator, 5000);
	}
	
	/**
	 * Given the location method will check the if the locator exists.
	 * @param locator
	 * @param wait
	 * @return
	 */
	public boolean isElementPresent(String locator, int wait) {
		boolean present = super.isElementPresent(locator);
		while(!present && wait > 0) {
			pause(500);
			wait -= 500;
			present = super.isElementPresent(locator);
		}
		return present;
	}
	
	@Override
	public boolean isElementPresent(String locator) {
		return isElementPresent(locator, 5000);
	}
	
	@Override
	public void type(String locator, String value) {
		waitForElement(locator);
		super.type(locator, value);
	}
	
	@Override
	public void click(String locator) {
		waitForElement(locator, Integer.parseInt(
				SeleniumDefaultProperties.getResourceAsStream("default.click.timeout")));
		super.click(locator);
	}
	/**
	 * isElementPresent just verifies the element exists in the DOM
	 * this API will also verify the element is not hidden.
	 * @param locator
	 * @param wait
	 * @return
	 */
	public boolean isElementPresentAndVisible(String locator, int wait) {
		if(isElementPresent(locator, wait)) {
			return super.isVisible(locator);
		}
		return false;
	}
	
	/**
	 * Waits for the element to become visible (i.e: not hidden)
	 * @param locator - The elements locator
	 * @param wait - Amoutn of time in ms to wait.
	 * @return
	 */
	public boolean waitForElementToBecomeVisible(String locator, int wait) {
		while(!isVisible(locator) && wait > 0) {
			pause(500);
			wait -= 500;
		}
		if(wait < 0) //Time out throw exception 
			throw new SeleniumException("Timeout while waiting for element " + locator +  " to become visible");
		return isVisible(locator);
	}
	
	/**
	 * Overrides the waitForPageToLoad in DefualtSelenium, method will check for errors 
	 * after page load.
	 * @param timeout Time to wait in milliseconds
	 */
	@Override
	public void waitForPageToLoad(String timeout) {
		super.waitForPageToLoad(timeout);
		//checkForServerConnection();
	}
	
	/**
	 * Another wrapper method, Get the time out properties from 
	 * the properties file.
	 */
	public void waitForPageToLoad() {
		waitForPageToLoad(
				SeleniumDefaultProperties.getResourceAsStream("default.pageload.timeout"));
	}
	
	/**
	 * wrapper method to DefualtSelenium class's method coz this method 
	 * accpet Integer instead of time out as a string value.
	 * @param timeout Time to wait in milliseconds
	 */
	public void waitForPageToLoad(int timeout) {
		waitForPageToLoad(Integer.toString(timeout));
	}
	
	/**
	 * Forces browser to break out into firebug in firefox.
	 */
	public void breakOut() {
		getEval("debugger;");
		pause(5000); //giving time to load.
	}
	
	/**
	 * Check for the server connection error Yukon specific error.
	 */
	public void checkForServerConnection() {
		try {
			if(super.isTextPresent("Connection to server has been lost") || 
					super.isElementPresent("//*[contains(text(), 'Connection to server has been lost')]")) {
				throw new SeleniumException("Cant Find Connection to Yukon Server");
			}
		}catch(SeleniumException ex){
			log.error(ex);
		}
	}
	
	/**
	 * wait for the given popup to load and open and switch the 
	 * focus to the popup window.
	 * @param windowName name of the window, can be found from firebug.
	 */
	public void waitForPopup(String windowName) {
		super.waitForPopUp(windowName, SeleniumDefaultProperties.getResourceAsStream("default.pageload.timeout"));
		super.selectWindow(windowName);
	}
	
}
