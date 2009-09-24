/**
 * 
 */
package com.cooper.selenium.test;


import junit.framework.Assert;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.YukonTableSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;
import com.cooper.selenium.meetering.MeteringSolvent;

/**
 * @author anuradha.uduwage
 *
 */

public class TestYukonTableSolventSelenium extends SolventSeleniumTestCase {
	
	@Test
	public void testTable() {
		LoginLogoutSolvent operationPage = this.start(this.getAuthenticatedSeleniumSession(), new LoginLogoutSolvent());
			operationPage.cannonLogin("yukon", "yukon")
			.navigateTo(new OperationsPageSolvent()).clickLinkItem("Metering")
			.navigateTo(new MeteringSolvent()).clickMeterSearch();
		YukonTableSolvent tableSolvent = new YukonTableSolvent("tableId=deviceTable");	
		tableSolvent.clickRowByIndex(5);
		tableSolvent.navigateTo(new YukonTopMenuSolvent()).clickBreadcrumb("Search");
		String meterName = tableSolvent.getTextInCell(2, 1);
		Assert.assertEquals("MCT410IL_Template", meterName);
		tableSolvent.mouseOver(6);
		tearDown();
	}
}
