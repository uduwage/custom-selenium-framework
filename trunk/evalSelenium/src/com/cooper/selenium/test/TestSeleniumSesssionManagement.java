/**
 * 
 */
package com.cooper.selenium.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cooper.selenium.SeleniumSession;
import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.OperationsPageSolvent;
import com.cooper.selenium.common.PopupMenuSolvent;
import com.cooper.selenium.common.YukonTableSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Test Session, ThreadLocal implementation.
 * @author anuradha.uduwage
 *
 */

public class TestSeleniumSesssionManagement extends SolventSeleniumTestCase {
	
	@Test
	public void testSessionCaching() {

		start(getAuthenticatedSeleniumSession());
		/*
		this.start(this.getAuthenticatedSeleniumSession(),
				new OperationsPageSolvent()).clickLinkItem("Cap Control")
				.navigateTo(new YukonTableSolvent());
		/*
		PopupMenuSolvent popupMenuSolvent = new PopupMenuSolvent();
		popupMenuSolvent.selectNewLocation("Metering");
		*/

		// Store this session
		SeleniumSession.store("Session1");

		System.out.println(SeleniumSession.get().toString());
		// Start a new session
		start(getAuthenticatedSeleniumSession());

		// Restore the old session
		SeleniumSession.restore("Session1");

		// Attempt to restore a non-existant session -- should throw an
		// exception
		boolean exceptionCaught = false;

		try {
			SeleniumSession.restore("NonExistant");
		} catch (SeleniumException e) {
			exceptionCaught = true;
		}

		Assert.assertTrue(
				"Attempting to restore a non-existant session did not throw an exception",
				exceptionCaught);

		SeleniumSession.endSession();
	}
}
