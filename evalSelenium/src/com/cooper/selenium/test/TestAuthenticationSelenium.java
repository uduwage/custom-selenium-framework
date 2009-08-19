/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.PopupMenuSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;
import com.cooper.selenium.stars.StarsGeneralSolvent;

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
		
		PopupMenuSolvent popupMenuSolvent = new PopupMenuSolvent();
		popupMenuSolvent.selectNewLocation("Metering");
		
		YukonTopMenuSolvent menuSolvent = new YukonTopMenuSolvent();
		menuSolvent.selectALocation("Reporting");
		menuSolvent.clickHome();
		
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
				loginLogoutSolvent.cannonLogin(users[i], passwords[i]);
				generalSolvent.clickGeneral();
				generalSolvent.clickStarsLeftMenuLink("Opt Out");
				loginLogoutSolvent.yukonLogout();
				
			}
		}
	}


}
