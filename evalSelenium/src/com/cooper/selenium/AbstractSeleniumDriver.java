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

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.Wait;

/**
 * @author A83E1
 *
 */
public class AbstractSeleniumDriver {

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
	 * @return
	 */
	public AbstractSeleniumDriver yukonLogout() {
		selenium.click("//td[@class='leftMenuHeader']//a[text()='Logout']");
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
}
