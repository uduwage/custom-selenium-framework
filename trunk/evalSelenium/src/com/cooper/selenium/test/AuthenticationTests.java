/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

import com.cooper.selenium.AbstractSeleniumDriver;
import com.cooper.selenium.ReadFromCSV;
import com.cooper.selenium.TableSolvent;
import com.cooper.selenium.stars.ContactInfoPageSolvent;

/**
 * @author anuradha.uduwage
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
	
	@Test
	public void testEditFirstName() {
		cannonLogin("41000000", "41000000").clickGeneral()
		.clickLeftMenuLink("Contacts");
		new ContactInfoPageSolvent().inputFirstName("Test")
			.selectPhoneNumberType("Fax Number")
			.clickUpdateContact().clickInfoPageButton("Create New Contact")
			.waitForElement("//input[@type='submit' and @value='Add notification']");
	}
	
	@Test
	public void eachUserEditContactInfo() {
		selenium.open("/");
		ReadFromCSV fromCSV = new ReadFromCSV();
		HashMap<String, String> userInfo = fromCSV.getParams("Short_UserPassword.csv");
		Iterator<Entry<String, String>> i = userInfo.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<String, String> values = (Map.Entry<String, String>)i.next();
			cannonLogin(values.getKey(), values.getValue()).clickLeftMenuLink("Contacts");
			new ContactInfoPageSolvent().inputFirstName("Operator" + values.getValue())
				.selectPhoneNumberType("Work Phone")
				.clickUpdateContact();
			yukonLogout();
		}		
	}
	
	@Test
	public void checkOperatorPage() {
		selenium.open("/");
		cannonLogin("yukon", "yukon").clickLinkItem("Cap Control");
		new TableSolvent().clickTestCheckBox();
		yukonLogout();
	}
	
}
