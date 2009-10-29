/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SolventSelenium;

/**
 * @author A83E1
 *
 */
public class YukonTreeSolvent extends AbstractSolvent {

	/**
	 * @param params
	 */
	public YukonTreeSolvent(String... params) {
		super(params);
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
	}
	/*
	 * //div[@id='internalTreeContainer']//li//*[normalize-space(text()) = 'Billing']/preceding::div[@class='x-tree-node-el x-unselectable x-tree-node-expanded']
	 */
	public YukonTreeSolvent expandByDoubleClick(String expandText) {
		String locator = "//div[@id='internalTreeContainer']//li//*[normalize-space(text()) = '" + expandText + "']";
		selenium.doubleClick(locator);
		selenium.pause(5000);
		return this;
	}
	
	public YukonTreeSolvent testCollapseIcon() {
		String locator = "//div[@id='internalTreeContainer']//li//*[normalize-space(text()) = 'Flags']/preceding::div[@class='x-tree-node-el x-unselectable x-tree-node-expanded']/img[@class='x-tree-ec-icon x-tree-elbow-minus']";
		selenium.click(locator);
		return this;
	}
}
