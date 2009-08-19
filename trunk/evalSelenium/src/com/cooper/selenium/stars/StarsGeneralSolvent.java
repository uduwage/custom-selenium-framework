/**
 * 
 */
package com.cooper.selenium.stars;

import com.cooper.selenium.AbstractSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * @author anuradha.uduwage
 *
 */
public class StarsGeneralSolvent extends AbstractSolvent {

	/**
	 * @param params
	 */
	public StarsGeneralSolvent(String... params) {
		super(params);
	}
	
	@Override
	public void prepare() {
		getSeleniumDriver().waitForPageToLoad();
	}
	
	/**
	 * Click General link from Yukon Operator Page.
	 * @return
	 */
	public StarsGeneralSolvent clickGeneral() {
		String general = "//div[@class='menuOption2']//a[text() = 'General']";
		if(!selenium.isElementPresent(general))
			throw new SeleniumException("Could not find link General to click");
		selenium.click("//div[@class='menuOption2']//a[text() = 'General']");
		return this;
	}
	
	/**
	 * Click any link on the left hand panel with a given link name.
	 * @param linkName name of the link.
	 * @return return an instance of the AbstractSeleniumDriver.
	 */
	public StarsGeneralSolvent clickStarsLeftMenuLink(String linkName) {
		String linkLocator = "//div[@class='menuOption2']//a[text() = '" + linkName + "']";
		selenium.waitForElement(linkLocator);
		if(!selenium.isElementPresent(linkLocator))
			throw new SeleniumException("Could not find link " + linkLocator + " to click.");
		selenium.click(linkLocator);
		return this;
	}	

}
