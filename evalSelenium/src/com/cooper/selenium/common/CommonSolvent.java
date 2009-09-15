/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SolventSeleniumException;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Any Common Functionalities that are not specific and available cross 
 * the application should get implemented here.
 * @author anuradha.uduwage
 *
 */
public class CommonSolvent extends AbstractSolvent {

	/**
	 * Constructor that accepts multiple params.
	 * @param params
	 */
	public CommonSolvent(String... params) {
		super(params);
	}
	
	/* (non-Javadoc)
	 * @see com.cooper.selenium.AbstractSolvent#prepare()
	 */
	@Override
	public void prepare() {
		//nothing at this point.
	}
	
	/**
	 * Method to used in AssertEquals, to check if we are in the correct page after 
	 * a navigation task. This method should work most of the time, we will have
	 * specific getText method for texts in tables (Cell base approach).  
	 * @param text string of characters.
	 * @return
	 */
	public String getYukonText(String text) {
		String txtLocator = "//*[contains(text(), '" + text + "')]";
		selenium.waitForElementToBecomeVisible(txtLocator, 2000);
		if(!selenium.isElementPresent(txtLocator))
			throw new SolventSeleniumException("Unable to find " + text + "how about double check and try!");
		String foundText = selenium.getText(txtLocator);
		return foundText;
	}
	
	/**
	 * Enters specified text in edit box with the given field label name.
	 * @param inputField name of the field that text are to be typed in.
	 * @param text the text to be entered.
	 * @return CommonSolvent
	 */
	public CommonSolvent enterText(String inputField, String text) {
		String inputLocator = "//input/following::td[normalize-space(text())='" + inputField + "']/following::input[1]";
		if(!selenium.isElementPresent(inputLocator, 2000))
			throw new SeleniumException("Unable to find input field name with '" + inputField + "' to type in '"+ text + "'");
		selenium.type(inputLocator, text);
		return this;
	}

	/**
	 * Click any input/button based on its name. Method will wait for button to appear
	 * if it doesn't appear then throw exception. 
	 * @param buttonName name of the input.
	 * @return
	 */
	public CommonSolvent clickButtonByName(String buttonName) {
		String buttonLocator = "//input[contains(@value, '" + buttonName + "')]";
		selenium.waitForElementToBecomeVisible(buttonLocator, 2000);
		if(!selenium.isElementPresent(buttonLocator))
			throw new SeleniumException("Unable to find '" + buttonName +"' to click, waited 2000ms");
		selenium.click(buttonLocator);
		return this;
	}
	
	/**
	 * Method to enter url from anywhere of the application. 
	 * TODO: get home url from the server settings and add function to navigate to home.
	 * @param url exact web address of the location.
	 * @return
	 */
	public CommonSolvent openURL(String url) {
		selenium.open(url);
		selenium.waitForPageToLoad(9000);
		return this;
	}
	
	/**
	 * Yukon application is so inconsistent so if there is an area that we can't use the 
	 * {@link #enterText(String, String)} use this method in such localized area to customize.
	 * @param id id value of the input
	 * @param text value
	 * @return
	 */
	public CommonSolvent inputTextById(String id, String text) {
		String locator = "//input[@id='"+ id +"']";
		selenium.waitForElement(locator);
		if(!selenium.isElementPresent(locator))
			throw new SeleniumException("Can not find input with id='" + id + "' to enter text");
		selenium.type(locator, text);
		return this;
	}
	
}
