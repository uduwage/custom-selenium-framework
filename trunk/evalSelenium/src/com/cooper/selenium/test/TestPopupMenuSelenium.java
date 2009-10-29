/**
 * 
 */
package com.cooper.selenium.test;

import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.PopupMenuSolvent;
import com.cooper.selenium.common.YukonTableSolvent;

/**
 * @author anuradha.uduwage
 *
 */
public class TestPopupMenuSelenium extends SolventSeleniumTestCase {

	@Test
	public void testPopupMenuSolvent() {
		this.start(getAuthenticatedSeleniumSession(), new LoginLogoutSolvent())
			.cannonLogin("yukon", "yukon")
			.navigateTo(new OperationsPageSolvent()).clickLinkItem("Cap Control")
			.navigateTo(new PopupMenuSolvent("linklocator=//a[@id='area_state_260630']")).openMenu();
	}
}
