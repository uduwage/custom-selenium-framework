/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSeleniumDriver;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Yukon top menu bar is common for multiple areas of the application. This class 
 * is a place holder for all the methods represent top menu bar.
 * @author anuradha.uduwage
 *
 */
public class YukonTopMenuSolvent extends AbstractSeleniumDriver {

	public YukonTopMenuSolvent(String...params) {
		// TODO: Nothing to do at this point.
	}
	
	/**
	 * Given a string locator this method will select the option from menu.
	 * @param option string value of the option in dropdown menu.
	 * @return
	 */
	public YukonTopMenuSolvent selectLocation(String option) {
		String selectLocator = "//div[@id='Menu']//select[@onchange=" +
				"'javascript:window.location=(this[this.selectedIndex].value);']";
		this.waitForElement(selectLocator);
		if(!this.isElementPresent(selectLocator))
			throw new SeleniumException("Could not find 'Select Location'Drop Down Menu");
		selenium.select(selectLocator, option);
		return this;
	}
}
