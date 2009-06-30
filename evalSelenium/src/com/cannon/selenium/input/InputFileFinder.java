/**
 * 
 */
package com.cannon.selenium.input;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.cannon.selenium.SolventTestCase;

/**
 * @author a83E1
 *
 */
public class InputFileFinder {

	private static final Logger log = Logger.getLogger(InputFileFinder.class.getName());
	
	private static final String DEFAULT_LANG_CODE = "en";
	
	/**
	 * 
	 * @param test
	 * @return
	 */
	public static InputStream getInputFileAsStream(SolventTestCase test) {
		return test.getClass().getClassLoader().getResourceAsStream(getInputFileNameForTest(test));
	}

	/**
	 * Determines the appropriate name and location of the input file for the given test
	 * @param test
	 * @return
	 */
	private static String getInputFileNameForTest(SolventTestCase test) {
		//Check to see if we are in the default package; this happens often when we are running 
		//from the Selenium Runner. if we are in the default package, null is returned 
		//by the call to getPackage(). This must be handeld correctly.
		String packageName = "";
		if(test.getClass().getPackage() != null) {
			packageName = test.getClass().getPackage().getName() + ".";
		}
		String resourceName = (packageName + test.getClass().getSimpleName()).replace('.', '/');
		String languageCode = System.getProperty("user.lnaguage");
		if(!languageCode.equals(DEFAULT_LANG_CODE)) {
			resourceName += "_" + languageCode;
		}
		resourceName += ".xml";
		log.info("Seaching for input file " + resourceName);
		return resourceName;
	}
}
