/**
 * 
 */
package com.cooper.selenium.test;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.cooper.selenium.SolventSeleniumTestCase;
import com.cooper.selenium.input.InvalidDataException;

/**
 * @author anuradha.uduwage
 *
 */
public class TestInputParsingSelenium extends SolventSeleniumTestCase {

	private static final Logger log = Logger.getLogger(TestInputParsingSelenium.class.getName());
	
	@Test
	public void testInputFileFinder() {
		//Test that single-value params are read correctly
		Assert.assertEquals(getParamString("Organization"), "YUKON");
		
		Assert.assertEquals(getParamString("cooper_admin"), "yukonAdmin");
		
		// Test that multi-value parameters are read in correctly
		String[] creators = getParamStrings("creators");
		
		Assert.assertEquals("yukonUser1", creators[0]);
		Assert.assertEquals("yukonUser2", creators[1]);
		Assert.assertEquals("yukonUser3", creators[2]);

		// Test that non existing param throws exception
		boolean exceptionCaught = false;
		
		try {
			getParamString("non-existant-param");
		} catch (InvalidDataException e) {
			exceptionCaught = true;
			log.error(e);
		}
		Assert.assertTrue(exceptionCaught);
		exceptionCaught = false;
		
		// Test that accessing single-value param through multi-value accesor throws exception
		try {
			getParamStrings("Organization");
		} catch (InvalidDataException e) {
			log.error(e);
			exceptionCaught = true;
		}
		
		Assert.assertTrue(exceptionCaught);		
	}
}
