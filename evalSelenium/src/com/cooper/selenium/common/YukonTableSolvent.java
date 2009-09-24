/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SeleniumDefaultProperties;
import com.cooper.selenium.SeleniumSession;
import com.cooper.selenium.SolventSeleniumException;


/**
 * Solvent that handles all tables in yukon application.<br>
 * Any table that has a tableId defined can use this class.
 * <br><br>
 * @author anuradha.uduwage
 *
 */
public class YukonTableSolvent extends AbstractSolvent {

	private String tableId = null;
	private String xpathRoot = null;
	
	@Override
	public void prepare() {
		//nothing to do
	}
	
	public YukonTableSolvent(String...params) {
		super(params);
		this.tableId = this.getParams("tableId");
		this.setXpathRoot(getTableLocator(tableId));
	}
	
	/**
	 * Given the row number method will simulate mouse over action on any row.
	 * @param rowNum integer value of the row.
	 * @return
	 */
	public YukonTableSolvent mouseOver(int rowNum) {
		selenium.mouseOver(getTableRowXPathRoot(rowNum));
		return this;
	}
	
	/**
	 * Click the row by given row index.
	 * @param rowIndex Index of the row.
	 * @return
	 */
	public YukonTableSolvent clickRowByIndex(int rowIndex) {
		String rowLocator = this.getTableRowXPathRoot(rowIndex);
		selenium.click(rowLocator);
		return this;
	}
	/**
	 * Based on the index of the row and index of the cell the method will return 
	 * the text of the cell.
	 * 
	 * @param rowInxed index of the row.
	 * @param cellIndex index of the cell.
	 * @return cellText string value of the text in cell.
	 */
	public String getTextInCell(int rowInxed, int cellIndex) {
		String cellLocator = this.getTableRowXPathRoot(rowInxed) + "//td[" + cellIndex +"]";
		selenium.waitForElement(cellLocator, 
				Integer.parseInt(SeleniumDefaultProperties.getResourceAsStream("default.yukon.table.reload.timeout")));
		this.waitForJSObject(2000);
		String cellText = selenium.getText(cellLocator);
		return cellText;
	}
	/**
	 * Returns the locator for a table element in the current page.
	 * @param tableId The table id to search for
	 * @return The XPath locator string for a given table id.
	 */
	public static String getTableLocator(String tableId) {
		return "//*[@id='" + tableId + "']";
	}
	
	/**
	 * Gets the xpath expression for the row with the given index.
	 * @param rowNum integer value of the row.
	 * @return an xpath expression for the row with the index.
	 */
	protected String getTableRowXPathRoot(int rowNum) {
		return getXpathRoot() + "//tr[" + rowNum + "]"; 
	}
	
	/**
	 * Convenient method to get the default timeout for table to load.
	 * @see YukonTableSolvent#waitForJSObject(int) 
	 */
	protected void waitForJSObject() {
		waitForJSObject(Integer.parseInt
				(SeleniumDefaultProperties.getResourceAsStream("default.yukon.table.reload.timeout")));
	}
	
	/**
	 * Method waits for the JavaScript object tableId to appear in windows.deviceTables array.
	 * @param timeout time to wait in milliseconds.
	 */
	protected void waitForJSObject(int timeout) {
		int time = 0;
		while(time < timeout && !isJSObjectAvailable()) {
			time += 500;
		}
		if(time > timeout) {
			throw new SolventSeleniumException("Time out while looking for tableID " + this.tableId);
		}
	}
	
	/**
	 * Method confirms if the JavaScript object for this table is present, if not return false.
	 * @return True if the JSObject is present, return false otherwise.
	 */
	protected boolean isJSObjectAvailable() {
		return new Boolean(selenium.getEval
				("(window.deviceTables && (window.deviceTables['" + this.tableId + "'] != 'undefined'))"));
	}

	/**
	 * Return the tableId
	 * @return
	 */
	public String getTableId() {
		return this.tableId;
	}
	
	/**
	 * Method return the xpath root for an element.
	 * @return xpathRoot xpath for the root element.
	 */
	public String getXpathRoot() {
		return xpathRoot;
	}
	public void setXpathRoot(String xpathRoot) {
		this.xpathRoot = xpathRoot;
	}
	

}
