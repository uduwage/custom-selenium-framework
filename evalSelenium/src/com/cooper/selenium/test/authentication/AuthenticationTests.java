/**
 * 
 */
package com.cooper.selenium.test.authentication;

import org.junit.Before;
import org.junit.Test;

import com.cooper.selenium.AbstractSeleniumDriver;
import com.cooper.selenium.common.YukonTopMenuSolvent;

/**
 * 
 * @author anuradha.uduwage
 *
 */
@Deprecated
public class AuthenticationTests extends AbstractSeleniumDriver {

	@Before
	public void setUp() {
		selenium.setSpeed("2000");
		selenium.setTimeout("90000");
	}
	/*
	@Test
	public void testUserLogin() throws InterruptedException, TimeoutException, FileNotFoundException {	
		String submit = "//table[@class='loginTable']//input[@name='login']";
		selenium.open("/");

		boolean submitButton = selenium.isElementPresent(submit);
		if (submitButton) {
			String currentTitle = selenium.getTitle();
			System.out.println(currentTitle);
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
		CSVDataFileDigester fromCSV = new CSVDataFileDigester();
		
		String submit = "//table[@class='loginTable']//input[@name='login']";
		//selenium.open("/");
		
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
		selenium.open("/");
		cannonLogin("yukon", "yukon").clickGeneral()
		.clickLeftMenuLink("Contacts");
		new ContactInfoPageSolvent().inputFirstName("Test")
			.selectPhoneNumberType("Fax Number")
			.clickUpdateContact().clickInfoPageButton("Create New Contact")
			.waitForElement("//input[@type='submit' and @value='Add notification']");
	}
	
	@Test
	public void eachUserEditContactInfo() {
		selenium.open("/");
		CSVDataFileDigester fromCSV = new CSVDataFileDigester();
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
	*/
	@Test
	public void checkOperatorPage() {
		selenium.open("/");
		cannonLogin("yukon", "yukon").clickLinkItem("Cap Control");
		new YukonTopMenuSolvent().selectALocation("Metering")
			.selectALocation("Cap Control");
		selenium.click("link=SubArea-01");
		yukonLogout();
	}
	
}
