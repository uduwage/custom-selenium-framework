/**
 * 
 */
package com.cooper.selenium.common;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.cooper.selenium.AbstractSeleniumDriver;
import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SolventSelenium;
import com.cooper.selenium.input.CSVDataFileDigester;

/**
 * @author anuradha.uduwage
 *
 */
public class LoginLogoutSolvent extends AbstractSolvent {

	/**
	 * @param params
	 */
	public LoginLogoutSolvent(String... params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see com.cooper.selenium.AbstractSolvent#prepare()
	 */
	@Override
	public void prepare() {
		getSeleniumDriver().waitForPageToLoad();
	}
	
	/**
	 * API level method that accept any username and password as a string. 
	 * @param userName String value of the username.
	 * @param password String value of the password.
	 * @return returns an instance of {@link LoginLogoutSolvent}
	 */
	public LoginLogoutSolvent cannonLogin(String userName, String password) {
		String userLocator = "//table[@class='loginTable']//input[@id='USERNAME']";
		String passLocator = "//table[@class='loginTable']//input[@name='PASSWORD']";
		String submit = "//table[@class='loginTable']//input[@name='login']";
		selenium.type(userLocator, userName);
		selenium.type(passLocator, password);
		selenium.click(submit);
		return this;	
	}	

	/**
	 * Multiple user login using user credentials from CSV file.
	 * @param filename name of the file that has Usernames and Passwords.
	 * @return Returns an instance of {@link LoginLogoutSolvent}
	 * @throws FileNotFoundException Throws an exception if file not found.
	 * @throws InterruptedException 
	 */
	public LoginLogoutSolvent multipleUserLogin(String filename) throws FileNotFoundException, InterruptedException {
		CSVDataFileDigester readFromCSV = new CSVDataFileDigester();
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
	 * @return return instance of {@link LoginLogoutSolvent}
	 */
	public LoginLogoutSolvent yukonLogout() {
		String logout = "//a[normalize-space(text())='Logout']";
		selenium.waitForElement(logout);
		if(selenium.isElementPresent(logout))
			selenium.click(logout);
		return this;
	}	
}
