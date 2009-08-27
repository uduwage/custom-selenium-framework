/**
 * 
 */
package com.cooper.selenium.test;

import org.junit.Assert;
import org.junit.Test;

import com.cooper.selenium.SeleniumSession;
import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.common.LoginLogoutSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Test Session, ThreadLocal implementation.
 * @author anuradha.uduwage
 *
 */

public class TestSeleniumSesssionManagement extends SolventSeleniumTestCase {
	
	@Test
	public void testSessionCaching() {

		start(getAuthenticatedSeleniumSession(), new LoginLogoutSolvent()).cannonLogin("yukon", "yukon");

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
