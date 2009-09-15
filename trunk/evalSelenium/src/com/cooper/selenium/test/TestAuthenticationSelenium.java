/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;

import junit.framework.Assert;
import junit.textui.TestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

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
		menuSolvent.selectALocation("Bulk Importer").clickBreadcrumb("Operations Home");
		menuSolvent.clickHome();
		menuSolvent.selectALocation("Reporting");
		menuSolvent.clickHome();
		menuSolvent.selectALocation("Metering");
		menuSolvent.clickHome();
		menuSolvent.selectALocation("Bulk Operations");
		
		//test if we are in Bulk Operations page
		CommonSolvent commonSolvent = new CommonSolvent();
		Assert.assertEquals("Bulk Operations", commonSolvent.getYukonText("Bulk Operations"));
		
		operationPage.yukonLogout();
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
					generalSolvent.clickStarsLeftMenuLink("Contacts");
					generalSolvent.clickStarsLeftMenuLink(users[i]);
					generalSolvent.clickStarsLeftMenuLink("Schedule");
					generalSolvent.clickStarsLeftMenuLink("Control History");
					generalSolvent.clickStarsLeftMenuLink("Enrollment");
					generalSolvent.clickStarsLeftMenuLink("Opt Out");
					loginLogoutSolvent.yukonLogout();
				}
				else 
					throw new SeleniumException("Something worng with the users");
				
			}
		}
	}
	
	@Test
	public void testUserAccesstoSetupPage() {
		init();
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		String[] users = getParamStrings("users");
		String[] passwords = getParamStrings("passwords");
		CommonSolvent commonSolvent = new CommonSolvent();
		for(int i=0; i < users.length; i++) {
			if((passwords.length != 0) && (passwords.length == users.length)) {
				if(users[i] != null) {
					loginLogoutSolvent.cannonLogin(users[i], passwords[i]);
					commonSolvent.openURL("http://pspl-qa008:8080/setup.jsp");
					commonSolvent.getYukonText("HTTP Status");
					commonSolvent.openURL("http://pspl-qa008:8080/");
					loginLogoutSolvent.yukonLogout();
				}
				else 
					throw new SeleniumException("Users Didn't get Extracted properly from XML");
				
			}
		}		
	}
	
	@Test
	public void testNegativeLogin() {
		init();
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		loginLogoutSolvent.cannonLogin(getParamString("invaliduser"), getParamString("password")).end();
	}
	@Test
	public void testOtherTests() {
		TestOperationsNavSelenium operSel = new TestOperationsNavSelenium();
		operSel.testNavigate();
	}
	
}
