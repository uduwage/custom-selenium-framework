/**
 * 
 */
package com.cannon.selenium;

import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;

/**
 * @author A83E1
 *
 */
public class ExampleTest extends TestCase {

	private static final String MAX_WAIT_TIME_IN_MS = "90000";
	private static final String BASE_URL = "http://www.google.com/";
	
	protected DefaultSelenium createSeleniumClient(String url) throws Exception {
		return new DefaultSelenium("localhost", 4444, "*iexplore", url);
	}

	
	public void testClickingLink() throws Exception {
		DefaultSelenium selenium = createSeleniumClient("http://www.google.com/");
		selenium.open("/");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Advertising Programs");
		selenium.waitForPageToLoad("30000");
		/*
		String arrWindowIds[] = selenium.getAllWindowIds();
		for (String string : arrWindowIds) {
			System.out.print("Window id " + string);
		}
		*/
		assertTrue(selenium.isTextPresent("Advertise to people searching on Google and our advertising network"));
		selenium.waitForPageToLoad(MAX_WAIT_TIME_IN_MS);

		String expectedTitle = "Google Advertising";
		assertEquals(expectedTitle, selenium.getTitle());
	}
}
