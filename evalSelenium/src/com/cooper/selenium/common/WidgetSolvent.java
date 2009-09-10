/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SolventSelenium;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Solvent that handles all the widgets in Yukon application.
 * <br><br>
 * @author anuradha.uduwage
 *
 */
public class WidgetSolvent extends AbstractSolvent {

	/**
	 * @param params
	 */
	public WidgetSolvent(String... params) {
		super(params);
	}

	/* (non-Javadoc)
	 * @see com.cooper.selenium.AbstractSolvent#prepare()
	 */
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		////div[contains(text(),'Meter Information')]/preceding::img[@class='minMax' and contains(@id, 'minusImg')]

	}
	
	/**
	 * Method can be used to minimze and expand the widgets on any page.
	 * @param widgetName Title/Name of the Widget
	 * @return
	 */
	public WidgetSolvent expandCollapseWidgetByTitle(String widgetName) {
		String plusMinusLocator = getXpathBaseForWidgetMinMax(widgetName);
		if(!selenium.isElementPresent(plusMinusLocator))
			throw new SeleniumException("Unable to find widget " + widgetName + "did you spell it correct?");
		selenium.click(plusMinusLocator);
		return this;
	}
	
	/**
	 * Construct the baseXpath for plus minus icon and return its XPath as a string.
	 * @param widgetTitle title of the widget.
	 * @return XPath as a string.
	 */
	private String getXpathBaseForWidgetMinMax(String widgetTitle) {
		String baseLocator = "//div[contains(text(),'" + widgetTitle + "')]";
		String minusLocator = baseLocator + "/preceding::img[@class='minMax' and contains(@id, 'minusImg')]";
		String plusLocator = baseLocator + "/preceding::img[@class='minMax' and contains(@id, 'plusImg')]";
		String plusMinusXpath = "";
		if(selenium.isElementPresentAndVisible(minusLocator, 10000))
			plusMinusXpath = minusLocator;
		if(selenium.isElementPresentAndVisible(plusLocator, 10000))
			plusMinusXpath = plusLocator;
		
		return plusMinusXpath;
	}
	
	public String getWidgetTitle() {
		String title = selenium.getAttribute("//div[@class='title boxContainer_title']");
		return title;
	}
	

}
