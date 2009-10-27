/**
 * 
 */
package com.cooper.selenium.meetering;

import com.cooper.selenium.common.CommonSolvent;
import com.cooper.selenium.common.WidgetSolvent;
import com.cooper.selenium.common.YukonTableSolvent;
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
	 * TODO: This is not good but due to crazy dom structure we can't do much for now.
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
	
	/**
	 * This method specifically for filter criteria on the meter search page.
	 * @param filter name of the text box of filter criteria
	 * @param text input text.
	 * @return YukonTableSolvent 
	 */
	public YukonTableSolvent enterMeterFilterCriteria(String filter, String text) {
		String inputLocator = "//div[contains(text(), '"+ filter + "')]/following::input[1]";
		selenium.waitForElement(inputLocator, 2000);
		if(!selenium.isElementPresent(inputLocator))
			throw new SeleniumException("Can not find input text box for '" + filter +"' to type '" + text + "'.");
		selenium.type(inputLocator, text);
		return new YukonTableSolvent();
	}
	
	/**
	 * Click create button inside any widget available in Metering page.
	 * @param widgetHeader Title of the widget.
	 * @return
	 */
	public MeteringSolvent clickCreateByWidget(String widgetHeader) {
		String inputLocator = "//input/following::div[contains(@id, 'widgetTitledContainer')]" + 
								getXpathRootForWidgetTitle(widgetHeader) +
									"/following::input[@value='Create'][1]";
		selenium.waitForElement(inputLocator);
		if(!selenium.isElementPresent(inputLocator))
			throw new SeleniumException("Can not find create button under '" + widgetHeader + "'Widget.");
		selenium.click(inputLocator);
		return this;
	}
	
	
		
}
