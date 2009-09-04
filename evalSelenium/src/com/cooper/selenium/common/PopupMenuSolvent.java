/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.thoughtworks.selenium.SeleniumException;

/**
 * This solvent should handle most of the popup menus such as popup menu
 * we get buy clicking "enable" link in cap control.
 * @author anuradha.uduwage
 *
 */
public class PopupMenuSolvent extends AbstractSolvent {

	public PopupMenuSolvent(String...params) {
		super(params);
	}

	@Override
	public void prepare() {
		getSeleniumDriver().waitForPageToLoad();
	}
	
	private int WAIT_TIME_TO_OPEN = 10000;
	
	public PopupMenuSolvent openMenu() {
		selenium.click(getParams("linklocator"));
		selenium.waitForElementToBecomeVisible(getMenuXpath(), WAIT_TIME_TO_OPEN);
		return this;
	}
	
	/**
	 * Construct the Xpath expression for the menu.
	 * if menuId param is set, then it get used, else look
	 * for div. 
	 * @return manuXpath xpath of the menu.
	 */
	private String getMenuXpath() {
		String menuXpath = "";
		if(getParams("menuId") != null)
			menuXpath = "//*[@id='" + getParams("menuId") + "']";
		else
			menuXpath = "//div[@id='overDiv']";
		
		return menuXpath;
	}

}
