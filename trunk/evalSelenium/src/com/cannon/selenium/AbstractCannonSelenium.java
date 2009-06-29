/**
 * 
 */
package com.cannon.selenium;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * @author A83E1
 *
 */
public class AbstractCannonSelenium extends DefaultSelenium {
	
	protected static Selenium selenium;

	/**
	 * @param processor
	 */
	public AbstractCannonSelenium(CommandProcessor processor) {
		super(processor);
		// TODO Auto-generated constructor stub
	}
	
	public AbstractCannonSelenium() {
		super("localhost", 4444, "*firefox", "http://qa007:8080");
	}

	/**
	 * constructor that user can override the default setting of the selenium server.
	 * @param serverHost
	 * @param serverPort
	 * @param browserStartCommand
	 * @param browserURL
	 */
	public AbstractCannonSelenium(String serverHost, int serverPort,
			String browserStartCommand, String browserURL) {
		super(serverHost, serverPort, browserStartCommand, browserURL);
	}
	
	/**
	 * Method for authentication.
	 * @param username
	 * @param password
	 * @return
	 */
	public String authentication(String username, String password) {
		return null;
	}

}
