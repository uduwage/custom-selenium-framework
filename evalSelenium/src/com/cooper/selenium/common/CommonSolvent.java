/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SolventSelenium;
import com.cooper.selenium.SolventSeleniumException;

/**
 * Any Common Functionalities that are not specific and available cross 
 * the application should get implemented here.
 * @author anuradha.uduwage
 *
 */
public class CommonSolvent extends AbstractSolvent {

	/**
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
		selenium.waitForElement(txtLocator);
		if(!selenium.isElementPresent(txtLocator))
			throw new SolventSeleniumException("Unable to find " + text + "how about double check and try!");
		String foundText = selenium.getText(txtLocator);
		return foundText;
	}	

}
