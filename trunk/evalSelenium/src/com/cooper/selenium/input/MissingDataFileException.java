/**
 * 
 */
package com.cooper.selenium.input;

import com.cooper.selenium.SolventSeleniumException;

/**
 * @author anuradha.uduwage
 *
 */
public class MissingDataFileException extends SolventSeleniumException {

	private static final long serialVersionUID = 1L;
	
	public MissingDataFileException(String messString) {
		super(messString);
	}
}
