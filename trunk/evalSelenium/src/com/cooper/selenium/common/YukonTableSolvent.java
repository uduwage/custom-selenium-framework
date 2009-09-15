/**
 * 
 */
package com.cooper.selenium.common;

import com.cooper.selenium.AbstractSolvent;
import com.cooper.selenium.SeleniumSession;


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
	 * Based on the index of the row and index of the cell the method will return 
	 * the text of the cell.
	 * 
	 * @param rowInxed index of the row.
	 * @param cellIndex index of the cell.
	 * @return cellText string value of the text in cell.
	 */
	public String getTextInCell(int rowInxed, int cellIndex) {
		String cellLocator = this.getTableRowXPathRoot(rowInxed) + "//td[" + cellIndex +"]";
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
	
	public String tableFromTitle(String title) {
		String tId = this.getTableIDForTitle(title);
		System.out.println("tableId " + tId);
		return tId;
	}
	
	private String getTableIDForTitle(String title) {
		String js = "eval_xpath(\""
			+ this.getTitleBasedXPathRoot(title)
			+ "\", selenium.browserbot.getCurrentWindow().document[0].getAttribute('id');";
		String id = SeleniumSession.get().getEval(js);
		return id;
	}
	//TODO: if all the tables had same class this method could have been a static 
	//figureout to get the xpath buy the title name of the table.
	private String getTitleBasedXPathRoot(String title) {
		return "//table[contains(@id, '"+ 
				this.tableId +"')]//div[contains(.,"+ 
				title +")]/ancestor::div[@class='content roundedContainer_content']";
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
