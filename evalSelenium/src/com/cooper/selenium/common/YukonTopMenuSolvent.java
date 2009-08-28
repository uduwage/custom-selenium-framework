/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Yukon top menu bar is common for multiple areas of the application. This class 
 * is a place holder for all the methods represent top menu bar.
 * @author anuradha.uduwage
 *
 */
public class YukonTopMenuSolvent extends AbstractSolvent {

	public YukonTopMenuSolvent(String...params) {
		// TODO: Nothing to do at this point.
	}

	@Override
	public void prepare() {
		getSeleniumDriver().waitForPageToLoad();
	}	
	
	/**
	 * Given a string locator this method will select the option from menu.
	 * @param option string value of the option in dropdown menu.
	 * @return
	 */
	public YukonTopMenuSolvent selectALocation(String option) {
		String selectLocator = "//div[@id='Menu']//select[@onchange=" +
				"'javascript:window.location=(this[this.selectedIndex].value);']";
		selenium.waitForElement(selectLocator);
		if(!selenium.isElementPresentAndVisible(selectLocator, 5000))
			throw new SeleniumException("Could not find 'Select Location'Drop Down Menu");
		selenium.select(selectLocator, option);
		return this;
	}
	
	/**
	 * Method to take the user to home location
	 * @return
	 */
	public YukonTopMenuSolvent clickHome() {
		String home = "//div[@class='stdhdr_rightSide']//a[normalize-space(text())='Home']";
		selenium.waitForElement(home);
		if(!selenium.isElementPresent(home)) 
			throw new SeleniumException("Could not find 'Home' link on the page");
		selenium.click(home);
		return this;
	}
	/**
	 * Function to click home link when you are in All Trend Page
	 * TODO: at somepoint we will combine above method and this.
	 * @return
	 */
	public YukonTopMenuSolvent clickAllTrendsHome() {
		String allTrendHome = "//a[normalize-space(text())='Home']";
		if(!selenium.isElementPresent(allTrendHome))
			throw new SeleniumException("Could not find 'Home' link on All Trends Page");
		selenium.click(allTrendHome);
		return this;
	}
	
	/**
	 * This method should provide access to all the breadcrumb links at the top menu.
	 * @param bcrumbName name of the breadcrumb link.
	 * @return
	 */
	public YukonTopMenuSolvent clickBreadcrumb(String bcrumbName) {
		String linklocator = "//a[normalize-space(text())='" + bcrumbName + "']";
		if(!selenium.isElementPresent(linklocator))
			throw new SeleniumException("Could not find " + bcrumbName + "breadcrumb link.");
		selenium.click(linklocator);
		return this;
	}

}
