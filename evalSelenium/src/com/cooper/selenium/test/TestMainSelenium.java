/**
 * 
 */
package com.cooper.selenium.test;

import java.io.FileNotFoundException;

import com.cooper.selenium.SolventSeleniumTestCase;

/**
 * This class shouldn't be here, framework was build to run in ant level, 
 * need a main method for the manifest.
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
	}
}
