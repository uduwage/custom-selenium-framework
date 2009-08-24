/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * 
 * @author anuradha.uduwage
 *
 */
public class OperationsPageSolvent extends AbstractSolvent {

	/**
	 * Default Constructor that accept multiple params.
	 * @param params
	 */
	public OperationsPageSolvent(String... params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see com.cooper.selenium.AbstractSolvent#prepare()
	 */
	@Override
	public void prepare() {
		getSeleniumDriver().waitForPageToLoad();
	}
	
	public OperationsPageSolvent clickLinkItem(String linkName) {
		String linkLocator = "//a[normalize-space(text())='" + linkName + "']";
		selenium.waitForElement(linkLocator);
		if(!selenium.isElementPresent(linkLocator))
			throw new SeleniumException("Unalbe to fine Link " + linkName + "check the link String.");
		selenium.click(linkLocator);
		return this;
	}	

}
