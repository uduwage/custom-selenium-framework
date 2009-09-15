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
import com.cooper.selenium.meetering.MeteringSolvent;

/**
 * @author anuradha.uduwage
 *
 */

public class TestYukonTableSolventSelenium extends SolventSeleniumTestCase {
	
	@Test
	public void testTable() {
		this.start(this.getAuthenticatedSeleniumSession(), new LoginLogoutSolvent())
			.cannonLogin("yukon", "yukon")
			.navigateTo(new OperationsPageSolvent()).clickLinkItem("Metering")
			.navigateTo(new MeteringSolvent()).clickMeterSearch()
			.navigateTo(new YukonTableSolvent("tableId=deviceTable"))
			.getTextInCell(2, 1);
	}
}
