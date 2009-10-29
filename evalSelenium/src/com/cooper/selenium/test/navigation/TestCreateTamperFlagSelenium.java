/**
 * 
 */
package com.cooper.selenium.test.navigation;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.meetering.MeteringSolvent;

/**
 * @author anuradha.uduwage
 *
 */
public class TestCreateTamperFlagSelenium extends SolventSeleniumTestCase {
	
	@Test
	public void createTamperFlag() {
		LoginLogoutSolvent login = this.start(getAuthenticatedSeleniumSession(), new LoginLogoutSolvent());
		login.cannonLogin("yukon", "yukon")
		.navigateTo(new MeteringSolvent()).clickCreateByWidget("Outage Monitors");
	}
}
