/**
 * 
 */
package com.cooper.selenium;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.cooper.selenium.input.ReadFromCSV;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.Wait;

/**
 * This calss is used for working spcifically Cooper Web Applications. It is setup 
 * where it will do all the communications with the Selenium Server. 
 * TODO: In future this class should extend the DefaultSelenium, and read host, Port etc
 * info from properties file.
 * 
 * @author anuradha.uduwage
 *
 */
public class AbstractSeleniumDriver {

	private static final Logger log = Logger.getLogger(AbstractSeleniumDriver.class.getName());
	protected static Selenium selenium;
	
	protected static String SELENIUM_SERVER_HOST="selenium.server.host";
	protected static String SELENIUM_SERVER_PORT="selenium.server.port";
	protected static String SELENIUM_BROWSER_STARTCOMMAND="selenium.browser.startCommand";
	protected static String SELENIUM_BROWSER_URL = "selenium.browser.url";
	
	@BeforeClass
	public static void setup() {
		selenium = new DefaultSelenium(System.getProperty(SELENIUM_SERVER_HOST),
										Integer.parseInt(System.getProperty(SELENIUM_SERVER_PORT)),
										System.getProperty(SELENIUM_BROWSER_STARTCOMMAND),
										System.getProperty(SELENIUM_BROWSER_URL));
		selenium.start();
	}
	
	@AfterClass
	public static void shutDown() {
		selenium.stop();
	}
	
	public void waitForElement(final String waitingElement, String timeoutMessage) {
		new Wait() {

			@Override
			public boolean until() {
				return selenium.isElementPresent(waitingElement);
			}
		}.wait(timeoutMessage);
	}
	
	/**
	 * API level method that accept any username and password as a string. 
	 * @param userName String value of the username.
	 * @param password String value of the password.
	 * @return returns an instance of {@link AbstractSeleniumDriver}
	 */
	public AbstractSeleniumDriver cannonLogin(String userName, String password) {
		String userLocator = "//table[@class='loginTable']//input[@id='USERNAME']";
		String passLocator = "//table[@class='loginTable']//input[@name='PASSWORD']";
		String submit = "//table[@class='loginTable']//input[@name='login']";
		selenium.type(userLocator, userName);
		selenium.type(passLocator, password);
		selenium.click(submit);
		return this;	
	}
	
	public AbstractSeleniumDriver multiUserLogin(String filename) throws InterruptedException, FileNotFoundException {
		ReadFromCSV fromCSV = new ReadFromCSV();
		Collection<String> collection = new ArrayList<String>(fromCSV.parse(filename));
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			//String string = (String) iterator.next();
			int i;
			for (i = 0; i < fromCSV.userInfo(collection).size(); i++) {
					String username = fromCSV.userInfo(collection).get(i);
					String password = fromCSV.userInfo(collection).get(i+1);
					cannonLogin(username, password);
					yukonLogout();
					System.out.println(username + "-" + password);
					i += 1;
			}			
		}
		return this;
	}
	
	/**
	 * Multiple user login using user credentials from CSV file.
	 * @param filename name of the file that has Usernames and Passwords.
	 * @return Returns an instance of {@link AbstractSeleniumDriver}
	 * @throws FileNotFoundException Throws an exception if file not found.
	 * @throws InterruptedException 
	 */
	public AbstractSeleniumDriver multipleUserLogin(String filename) throws FileNotFoundException, InterruptedException {
		ReadFromCSV readFromCSV = new ReadFromCSV();
		Collection<String> collection = null;
		collection = readFromCSV.parse(filename);
		HashMap<String, String> params = null;
		params = readFromCSV.returnStringParams(collection);
		Iterator<Entry<String, String>> i = params.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<String, String> values = (Map.Entry<String, String>)i.next();
			cannonLogin(values.getKey(), values.getValue());
			yukonLogout();
		}
		return this;
	}
	
	/**
	 * Logout from Yukon webapplication.
	 * @return return instance of {@link AbstractSeleniumDriver}
	 */
	public AbstractSeleniumDriver yukonLogout() {
		String logoutMain = "//td[@class='leftMenuHeader']//a[text()='Logout']";
		String logoutOther = "//a[normalize-space(text())='Logout']";
		if(isElementPresent(logoutMain))
			selenium.click(logoutMain);
		else
			selenium.click(logoutOther);
		return this;
	}
	
	/**
	 * Click General link from Yukon Operator Page.
	 * @return
	 */
	public AbstractSeleniumDriver clickGeneral() {
		selenium.click("//div[@class='menuOption2']//a[text() = 'General']");
		return this;
	}
	
	/**
	 * Click any link on the left hand panel with a given link name.
	 * @param linkName name of the link.
	 * @return return an instance of the AbstractSeleniumDriver.
	 */
	public AbstractSeleniumDriver clickLeftMenuLink(String linkName) {
		String linkLocator = "//div[@class='menuOption2']//a[text() = '" + linkName + "']";
		selenium.click(linkLocator);
		return this;
	}
	
	/**
	 * Given a String value of the link, method will click any hyper link.
	 * @param linkName string value of the link.
	 * @return
	 */
	public AbstractSeleniumDriver clickLinkItem(String linkName) {
		String linkLocator = "//a[normalize-space(text())='" + linkName + "']";
		waitForElement(linkLocator);
		if(!isElementPresent(linkLocator))
			throw new SeleniumException("Unalbe to fine Link " + linkName + "check the link String.");
		selenium.click(linkLocator);
		return this;
	}
	/**
	 * Simple pause method with a try/catch for the InterruptedException;
	 * only here to keep the try/catch block out of the test code.
	 * @param milliseconds
	 */
	public void pause(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}catch(InterruptedException e) {
			//TODO:hook up to log4j
			throw new SeleniumException("pause() interrupted! \n" + e.getMessage(), e);
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
		if(!present)
			throw new SeleniumException("Timeout while waiting for element " + locator + " to appear");
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
		boolean present = selenium.isElementPresent(locator);
		while(!present && wait > 0) {
			pause(500);
			wait -= 500;
			present = selenium.isElementPresent(locator);
		}
		return present;
	}
	
	public boolean isElementPresent(String locator) {
		return isElementPresent(locator, 5000);
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
			return selenium.isVisible(locator);
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
		while(!selenium.isVisible(locator) && wait > 0) {
			pause(500);
			wait -= 500;
		}
		if(wait < 0) //Time out throw exception 
			throw new SeleniumException("Timeout while waiting for element " + locator +  " to become visible");
		return selenium.isVisible(locator);
	}
}
