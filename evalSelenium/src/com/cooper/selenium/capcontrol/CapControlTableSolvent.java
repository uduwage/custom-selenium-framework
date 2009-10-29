/**
 * 
 */
package com.cooper.selenium.capcontrol;

import com.cooper.selenium.common.YukonTableSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * @author anuradha.uduwage
 *
 */
public class CapControlTableSolvent extends YukonTableSolvent {

	/**
	 * @param params
	 */
	public CapControlTableSolvent(String... params) {
		super(params);
	}

	public CapControlTableSolvent clickRowByContent(String rowContent) {
		String locator = getXpathRoot() + "//a[contains(text(), '" + rowContent + "')]";
		if(!selenium.isElementPresent(locator))
			throw new SeleniumException("Unable to find link " + locator + "with the text " + rowContent);
		selenium.click(locator);
		return this;
	}

}
