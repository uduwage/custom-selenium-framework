/**
 * 
 */
package com.cannon.selenium;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * @author A83E1
 *
 */
public class AuthenticationTests extends AbstractSeleniumDriver {

	@Before
	public void setUp() {
		selenium.setTimeout("90000");
	}
	
	@Test
	public void testUserLogin() throws InterruptedException, TimeoutException, FileNotFoundException {
		
		ReadFromCSV fromCSV = new ReadFromCSV();
		fromCSV.parse("UserName_Password.csv");	
		
		String submit = "//table[@class='loginTable']//input[@name='login']";
		selenium.open("/");

		boolean submitButton = selenium.isElementPresent(submit);
		if (submitButton) {
			String currentTitle = selenium.getTitle();
			String exceptedTitle = "Energy Services Operations Center";
			if (currentTitle.equalsIgnoreCase(exceptedTitle)) {
				cannonLogin(fromCSV.getUsername(), fromCSV.getPassword());
				selenium.wait(10000);
			}
		}
	}
	
	@Test
	public void testUserMultiLogin() throws InterruptedException, TimeoutException {
		ReadFromCSV fromCSV = new ReadFromCSV();
		
		String submit = "//table[@class='loginTable']//input[@name='login']";
		//selenium.open("/");
		//multiUserLogin("UserName_Password.csv");
		/*
		boolean submitButton = selenium.isElementPresent(submit);
		if (submitButton) {
			String currentTitle = selenium.getTitle();
			String exceptedTitle = "Energy Services Operations Center";
			if (currentTitle.equalsIgnoreCase(exceptedTitle)) {
				multiUserLogin("UserName_Password.csv");
			}
		}
		*/		
	}
}
