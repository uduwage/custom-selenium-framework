/**
 * 
 */
package com.cannon.selenium;

import java.util.Arrays;
import java.util.List;

import sun.security.action.GetBooleanAction;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

/**
 * @author A83E1
 *
 */
public class SolventSelenium extends DefaultSelenium {

	   private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SolventSelenium.class.getName());

	   private String baseURL = null;
	   List<String> ID_SWAP_TYPES = Arrays.asList("object","project", "change", "library", "product", "organization", "site");
	   
	   protected static String SELENIUM_SERVER_HOST="selenium.server.host";
	   protected static String SELENIUM_SERVER_PORT="selenium.server.port";
	   protected static String SELENIUM_BROWSER_STARTCOMMAND="selenium.browser.startCommand";
	   protected static String SELENIUM_BROWSER_URL = "selenium.browser.url";

    /**
     * Allows you to specify everything with hard coded values. Would try to use the default if possible.
     */
    public SolventSelenium(String serverHost, int serverPort, String browserStartCommand,
            String browserURL) {
        super(serverHost, serverPort, browserStartCommand, browserURL);
        baseURL = browserURL;
    }
	
    /**
     * Uses custom CommandProcessor
     */
    public SolventSelenium(CannonCommandProcessor processor) {
    	super(processor);
    	baseURL = processor.getBaseURL();
    }
    
    /**
     * This can be called at the beginning of a test.  It will login correctly under firefox.
     * It will add the "forceTrail=true" onto the end of the url.
     *
     * @param selenium Pass in the selenium object to act on.
     * @param username You could send in "wcadmin", "demo" or another user you have created.
     * @param password You could send in "wcadmin", "demo" or another user you have created.
     * @param location This would need to be anything after the "http://ralberts03d/Windchill/".  This method will
     * figure out the hostname and the web app name.<br /><br />
     *
     * For example it will figure out: $(wt.webserver.protocol)\://$(wt.rmi.server.hostname)\:$(wt.webserver.port)/$(wt.webapp.name)<br /><br />
     * An example of what to enter would be "/netmarkets/jsp/netmarkets/overview.jsp"
     */    
    public void openWithUserAuthentication(String username, String password, String location) {
		String url = getURLForAuthentication(username, password, location);
		//setTimeout(SolventTestConfig.getInstance().getOption("pageload.timeout"));
		open(url);
	}

	private String getURLForAuthentication(String username, String password, String location) {
        String host = baseURL + location;

        if(location.indexOf("/") == 0 && baseURL.endsWith("/")) { //Make sure that if there is a "/" at the beginning we take it off.
            host = baseURL + location.substring(1);
        }

        if(location.indexOf("/") != 0 && !baseURL.endsWith("/")) { //If location or baseHREF has no ending slash, add it.
            host = baseURL + "/" + location;
        }

        String url = host.replaceFirst("://", "://" + username.trim() + ":" + password.trim() + "@"); //check if params already exist or not.
        if(url.indexOf("?") > 0 && url.indexOf("?")+1 < url.length()) {
            url += "&forceTrail=true";
        } else {
            url += "forceTrail=true";
        }

        return url;
	}
	
	protected static String getHostname() {
		return null;
	}
	
	/**
	 * In order it tries to get the base cannon url. <br />
	 * 
	 * 1) Look for a override for the base url. The override is "test.url". <br />
	 * 2) Try to get the base url from the URLFactory(). <br />
	 * 3) Go with the dfault http://qa008:8080/. <br />
	 * @return
	 */
	protected static String getBaseHREF() {
		if(System.getProperty("test.url") != null) {
			String cannon = System.getProperty("test.url");
			
			if(!cannon.startsWith("http://"))
				return "http://" + cannon;
			
			return cannon;
		}else {
			return System.getProperty(SELENIUM_BROWSER_URL); 
		}
	}
	
	/**
	 * Waits for an alert to appear. This is most useful for asynchronus operation that 
	 * create an alert dialog on completion.
	 * @param waitTime
	 * @return
	 */
	public String waitForAlert(long waitTime) {
		while(waitTime > 0 && !isAlertPresent()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error("Wait interrupted!", e);
			}
			waitTime -= 1000;
		}

		if(isAlertPresent()) {
			try {
				return getAlert();
			}catch (Exception e) {
				log.error("Could not get alert. ", e);
			}
		}else {
			throw new SeleniumException("Error: waitForAlert time out!");
		}
		return null;
	}
	
	
	

}
