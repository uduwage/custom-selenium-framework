/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.SeleniumSession;
import com.cooper.selenium.SolventSelenium;

/**
 * @author anuradha.uduwage
 *
 */
public class NavigationHelper {

	protected static SolventSelenium getSelenium() {
		return SeleniumSession.get();
	}
	
	public static void waitForPageToLoad() {
		int timeout = 25000;

	}
}
