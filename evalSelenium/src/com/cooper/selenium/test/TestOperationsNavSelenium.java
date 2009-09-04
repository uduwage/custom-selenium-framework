/**
 * 
 */
package com.cooper.selenium.test;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.WidgetSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;

/**
 * Test to check the navigation options in /Operations.jsp page
 * @author anuradha.uduwage
 *
 */

public class TestOperationsNavSelenium extends SolventSeleniumTestCase {

	@Test
	public void testNavigate() {
		//start the session for the test. 
		start();
		//use the LoginSolvent to login
		LoginLogoutSolvent loginLogoutSolvent = new LoginLogoutSolvent();
		loginLogoutSolvent.cannonLogin("yukon", "yukon");
		//Click multiple functions in operations page.
		OperationsPageSolvent operationsPageSolvent = new OperationsPageSolvent();
		operationsPageSolvent.clickLinkItem("All Trends").navigateTo(new YukonTopMenuSolvent()).clickAllTrendsHome();
		operationsPageSolvent.clickLinkItem("Bulk Importer");
		operationsPageSolvent.navigateTo(new YukonTopMenuSolvent()).clickHome();
		operationsPageSolvent.clickLinkItem("Metering")
			.navigateTo(new WidgetSolvent()).expandCollapseWidgetByTitle("Meter Search")
			.navigateTo(new YukonTopMenuSolvent().clickHome());
		
		//showing the versatility
		YukonTopMenuSolvent menuSolvent = new YukonTopMenuSolvent();
		operationsPageSolvent.clickLinkItem("Bulk Operations");
		menuSolvent.clickHome();
		operationsPageSolvent.clickLinkItem("Direct");
		menuSolvent.clickAllTrendsHome();
		operationsPageSolvent.clickLinkItem("3-Tier Direct");
		menuSolvent.clickAllTrendsHome();
		operationsPageSolvent.clickLinkItem("Cap Control");
		menuSolvent.clickHome();
		operationsPageSolvent.clickLinkItem("Reporting");
		menuSolvent.clickHome();
		operationsPageSolvent.clickLinkItem("Commander");
		menuSolvent.clickHome();
		operationsPageSolvent.clickLinkItem("Manage Indexes");
		menuSolvent.clickHome();
		operationsPageSolvent.clickLinkItem("Device Configuration");
		//logout
		loginLogoutSolvent.yukonLogout();
		//kill everything so no leftover hanging sessions.
		tearDown();
		
	}

}
