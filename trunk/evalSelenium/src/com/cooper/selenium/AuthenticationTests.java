/**
 * 
 */
package com.cooper.selenium;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author A83E1
 *
 */
public class AuthenticationTests extends AbstractSeleniumDriver {

	@Before
	public void setUp() {
		selenium.setSpeed("2000");
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
				//cannonLogin(fromCSV.getUsername(), fromCSV.getPassword());
				cannonLogin("41000000", "41000000").clickGeneral()
				.clickLeftMenuLink("Contacts")
				.clickLeftMenuLink("Opt Out")
				.yukonLogout();				
			}
		}
	}
	/*
	@Test
	public void testUserMultiLogin() throws InterruptedException, TimeoutException, FileNotFoundException {
		ReadFromCSV fromCSV = new ReadFromCSV();
		
		String submit = "//table[@class='loginTable']//input[@name='login']";
		selenium.open("/");
		
		boolean submitButton = selenium.isElementPresent(submit);
		if (submitButton) {
			String currentTitle = selenium.getTitle();
			String exceptedTitle = "Energy Services Operations Center";
			if (currentTitle.equalsIgnoreCase(exceptedTitle)) {
				multipleUserLogin("Short_UserPassword.csv");
			}
		}		
	}
	*/
	@Test
	public void testEditFirstName() {
		cannonLogin("41000000", "41000000").clickGeneral()
		.clickLeftMenuLink("Contacts");
		new ContactInfoPageSolvent().inputFirstName("Test")
			.selectPhoneNumberType("Fax Number")
			.clickUpdateContact().clickInfoPageButton("Create New Contact")
			.waitForElement("//input[@type='submit' and @value='Add notification']");
	}
	
}
