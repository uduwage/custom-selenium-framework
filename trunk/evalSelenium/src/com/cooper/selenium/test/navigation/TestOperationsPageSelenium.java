/**
 * 
 */
package com.cooper.selenium.test.navigation;

import org.junit.Test;
import org.testng.Assert;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.CommonSolvent;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;

/**
 * @author anuradha.uduwage
 *
 */
public class TestOperationsPageSelenium extends SolventSeleniumTestCase {

	public void init() {
		start();
	}
	@Test
	public void navigateFromOperationsPage() {
		start();
		LoginLogoutSolvent login = new LoginLogoutSolvent();
		login.cannonLogin("yukon", "yukon")
		.navigateTo(new OperationsPageSolvent()).clickLinkItem("Metering");
		Assert.assertEquals(login.navigateTo(new CommonSolvent()).getPageTitle(), "Meter Home Page");
		login.navigateTo(new YukonTopMenuSolvent()).clickHome();
		login.end();
	}
	
	@Test
	public void anotherNavigate() {
		init();
		LoginLogoutSolvent login = new LoginLogoutSolvent();
		CommonSolvent common = new CommonSolvent();
		login.cannonLogin("yukon", "yukon");
		OperationsPageSolvent op = new OperationsPageSolvent();
		op.clickLinkItem("All Trends");
		Assert.assertEquals(common.getPageTitle(), "Metering");
		op.navigateTo(new YukonTopMenuSolvent()).clickHome();
		op.clickLinkItem("Bulk Operations");
		op.navigateTo(new YukonTopMenuSolvent()).clickHome();
		op.clickLinkItem("Metering");
		
		
	}
}
