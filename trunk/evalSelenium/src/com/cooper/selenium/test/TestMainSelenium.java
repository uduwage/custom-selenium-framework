/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;

import com.cooper.selenium.SolventSeleniumTestCase;

/**
 * This class shouldn't be here, attempt to test the jar
 * @author anuradha.uduwage
 *
 */
public class TestMainSelenium extends SolventSeleniumTestCase {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		org.junit.runner.JUnitCore.main("com.cooper.selenium.test.TestAuthenticationSelenium");
		org.junit.runner.JUnitCore.main("com.cooper.selenium.test.TestInputParsingSelenium");
		org.junit.runner.JUnitCore.main("com.cooper.selenium.test.TestJSCATableSolventSelenium");

	}

}
