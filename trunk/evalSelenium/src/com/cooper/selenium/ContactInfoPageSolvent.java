/**
 * 
 */
package com.cooper.selenium;

import com.thoughtworks.selenium.SeleniumException;

/**
 * @author A83E1
 *
 */
public class ContactInfoPageSolvent extends AbstractSeleniumDriver {
	
	public ContactInfoPageSolvent() {
		//nothind to do at this point.
	}
	
	public ContactInfoPageSolvent inputFirstName(String text) {
		selenium.setSpeed("5000");
		String locator = "//table[@class='contactTable']//input[@name='firstName']";
		if(!selenium.isElementPresent(locator))
			throw new SeleniumException("Could not find input labele firstName to type in");
		selenium.type(locator, text);
		return this;
	}
}
