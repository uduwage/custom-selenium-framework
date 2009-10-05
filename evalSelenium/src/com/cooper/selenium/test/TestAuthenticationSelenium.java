/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.CommonSolvent;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;
import com.cooper.selenium.stars.StarsGeneralSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * This is to test the thread local implementation.
 * @author anuradha.uduwage
 *
 */

public class TestAuthenticationSelenium extends SolventSeleniumTestCase {
	
	private void init() {
		start();
	}
	
	@Test
	public void testProductNav() {
		LoginLogoutSolvent operationPage = this.start(this.getAuthenticatedSeleniumSession(), new LoginLogoutSolvent());
			operationPage.cannonLogin(getParamString("username"), getParamString("password"))
			.navigateTo(new OperationsPageSolvent()).clickLinkItem("Cap Control")
			.navigateTo(new YukonTopMenuSolvent()).clickHome();
		
		YukonTopMenuSolvent menuSolvent = new YukonTopMenuSolvent();
		menuSolvent.selectALocation("Bulk Importer");
		
		CommonSolvent commonSolvent = new CommonSolvent();
		Assert.assertEquals("Bulk Importer", commonSolvent.getPageTitle());
		Assert.assertEquals("Bulk Importer", commonSolvent.getYukonText("Bulk Importer"));
		
		menuSolvent.clickBreadcrumb("Operations Home");
		menuSolvent.clickHome();
		menuSolvent.selectALocation("Reporting");
		menuSolvent.clickHome();
		
		menuSolvent.selectALocation("Metering");
		Assert.assertEquals("Meter Home Page", commonSolvent.getPageTitle());
		menuSolvent.clickHome();
		
		menuSolvent.selectALocation("Bulk Operations");
		//test if we are in Bulk Operations page
		Assert.assertEquals("Bulk Operations", commonSolvent.getPageTitle());
		Assert.assertEquals("Bulk Operations", commonSolvent.getYukonText("Bulk Operations"));
		
		operationPage.yukonLogout().end();
	}
	
	@Test
	public void multipleLogin() throws FileNotFoundException, InterruptedException {
		init();
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		StarsGeneralSolvent generalSolvent = new StarsGeneralSolvent();
		//loginLogoutSolvent.multipleUserLogin("Short_UserPassword.csv");
		String[] users = getParamStrings("users");
		String[] passwords = getParamStrings("passwords");
		for(int i=0; i < users.length; i++) {
			if((passwords.length != 0) && (passwords.length == users.length)) {
				if(users[i] != null) {
					loginLogoutSolvent.cannonLogin(users[i], passwords[i]);
					generalSolvent.clickGeneral();
					generalSolvent.clickStarsLeftMenuLink("Contact Us");
					generalSolvent.clickStarsLeftMenuLink(users[i]);
					generalSolvent.clickStarsLeftMenuLink("Schedule");
					generalSolvent.clickStarsLeftMenuLink("Control History");
					//generalSolvent.clickStarsLeftMenuLink("Opt Out");
					loginLogoutSolvent.yukonLogout();
				}
				else 
					throw new SeleniumException("Something worng with the users");
				
			}
		}
		loginLogoutSolvent.end();
	}
	
	@Test
	public void testUserAccesstoSetupPage() {
		init();
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		String[] users = getParamStrings("users");
		String[] passwords = getParamStrings("passwords");
		String http500 = "Apache Tomcat/5.5.27 - Error report";
		CommonSolvent commonSolvent = new CommonSolvent();
		for(int i=0; i < users.length; i++) {
			if((passwords.length != 0) && (passwords.length == users.length)) {
				if(users[i] != null) {
					loginLogoutSolvent.cannonLogin(users[i], passwords[i]);
					commonSolvent.openURL("http://pspl-qa008:8080/setup.jsp");
					commonSolvent.getYukonText("HTTP Status");
					Assert.assertEquals(http500, commonSolvent.getPageTitle());
					commonSolvent.openURL("http://pspl-qa008:8080/");
					loginLogoutSolvent.yukonLogout();
				}
				else 
					throw new SeleniumException("Users Didn't get Extracted properly from XML");
				
			}
		}
		
		loginLogoutSolvent.cannonLogin(getParamString("username"), getParamString("password"));
		commonSolvent.openURL("http://pspl-qa008:8080/setup.jsp");
		Assert.assertFalse(commonSolvent.isPageTitle(http500));		
		commonSolvent.end();
		
	}
	
	@Test
	public void testNegativeLogin() {
		init();
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		loginLogoutSolvent.cannonLogin(getParamString("invaliduser"), getParamString("password"));
		loginLogoutSolvent.navigateTo(new CommonSolvent()).getYukonText("Invalid username");
		loginLogoutSolvent.end();
	}
	@Test
	public void testOperationsNav() {
		TestOperationsNavSelenium operSel = new TestOperationsNavSelenium();
		operSel.testNavigate();
	}
	@Test
	public void testMeterDeviceTable() {
		TestYukonTableSolventSelenium table = new TestYukonTableSolventSelenium();
		table.testTable();
	}
}
