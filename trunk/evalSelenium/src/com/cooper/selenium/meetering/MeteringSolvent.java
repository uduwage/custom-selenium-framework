/**
 * 
 */
package com.cooper.selenium.meetering;

import com.cooper.selenium.common.WidgetSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Handle functionalities in Metering page. Idea is to have sperate methods 
 * for each widget.
 * TODO: Should this be MeterWidgetSolvent
 * @author anuradha.uduwage
 *
 */
public class MeteringSolvent extends WidgetSolvent {

	/**
	 * @param params
	 */
	public MeteringSolvent(String... params) {
		super(params);
	}

	@Override
	public void prepare() {
		//nothing to do at this point.
	}
	
	/**
	 * TODO: This is not good but due to crapy dom structure we can't do much for now.
	 * TODO: Should implement a method in super class to get widget by its header.
	 * @return
	 */
	public MeteringSolvent clickMeterSearch() {
		String locator = "//input[@type='submit' and @value='Search']";
		if(!selenium.isElementPresent(locator))
			throw new SeleniumException("Search Button is not available ");
		String elementInForm = "//form[@id='filterForm']/input[@value='Filter']";
		selenium.click(locator);
		selenium.waitForPageToLoad();
		selenium.waitForElementToBecomeVisible(elementInForm, 2000);
		return this;
	}
		
}
