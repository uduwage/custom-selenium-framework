/**
 * 
 */
package com.cooper.selenium.test;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.capcontrol.CapControlTableSolvent;
import com.cooper.selenium.common.CommonSolvent;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.PopupMenuSolvent;
import com.cooper.selenium.common.YukonTopMenuSolvent;
import com.cooper.selenium.common.YukonTreeSolvent;
import com.cooper.selenium.meetering.BillingSolvent;

/**
 * @author A83E1
 *
 */
public class TestCapControlSelenium extends SolventSeleniumTestCase {

	@Test
	public void testCapControl() {
		this.start(getAuthenticatedSeleniumSession(), new LoginLogoutSolvent())
			.cannonLogin("yukon", "yukon")
			.navigateTo(new OperationsPageSolvent())
			.clickLinkItem("Metering")
			.navigateTo(new YukonTopMenuSolvent()).clickTopMenuItem("Billing")
			.navigateTo(new BillingSolvent()).changeBillingSettings("File Format:", "OPU")
			.changeBillingSettings("Energy Days Previous:", "23")
			.changeBillingSettings("Demand Days Previous:", "100")
			.navigateTo(new YukonTreeSolvent()).expandByDoubleClick("Meters")
			.expandByDoubleClick("Billing").testCollapseIcon().end(); /*
			.navigateTo(new OperationsPageSolvent()).clickLinkItem("Cap Control")
			.navigateTo(new CapControlTableSolvent("tableId=areaTable")).clickRowByContent("SubArea-01")
			.navigateTo(new PopupMenuSolvent("linklocator=//a[@id='area_state_260630']")).openMenu(); */
	}
}
