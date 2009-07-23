/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSeleniumDriver;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Handle all the functionalities in the operations page.
 * @author A83E1
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
				"'javascript:window.location=(this[this.selectedIndex].value);']" +
				"//option[contains(text(), '" + option + "')]";
		this.waitForElement(selectLocator);
		if(!this.isElementPresent(selectLocator))
			throw new SeleniumException("Could not find 'Select Location'Drop Down Menu");
		selenium.select(selectLocator, option);
		return this;
	}
}
