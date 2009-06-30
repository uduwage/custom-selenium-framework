/**
 * 
 */
package com.cannon.selenium;

import com.thoughtworks.selenium.HttpCommandProcessor;

/**
 * @author A83E1
 *
 */
public class SolventCommandProcessor extends HttpCommandProcessor {
	
	private static final String FIREFOX2_LABEL = "*cooper-firefox2";
	private static final String FIREFOX2_EXE = "browsers/windows/firefox/Firefox2/App/firefox.exe";
	private static final String FIREFOX2_PROFILE_DIR = "cooper-firefox2";
	
	private static final String FIREFOX3_LABEL = "*cooper-firefox3";
	private static final String FIREFOX3_EXE = "browsers/windows/firefox/Firefox3/App/firefox.exe";
	private static final String FIREFOX3_PROFILE_DIR = "cooper-firefox3";
	
	private String browserStartCommand = null;
	private String browserURL = null;
	private String optionsString = null;
	private String sessionId = null;

	public SolventCommandProcessor(String serverHost, int serverPort, String browserStartCommand, String browserURL) {
		
		super(serverHost, serverPort, getFixedCommand(browserStartCommand), browserURL);
		this.browserURL = browserURL;
		this.browserStartCommand = browserStartCommand;
		
		if(this.browserStartCommand.equals(FIREFOX2_LABEL)) {
			this.optionsString = "profile:" + FIREFOX2_PROFILE_DIR;
		}else if (browserStartCommand.equals(FIREFOX3_LABEL)) {
			this.optionsString = "profile:" + FIREFOX3_PROFILE_DIR;
		}
		else {
			this.optionsString = "";
		}
	}
	
	public String getBaseURL() {
		return this.browserURL;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	@Override
	public void start() {
        // The code of this method is functionally equivalent to
        //     HttpCommandProcessor.start(String optionsString)
        // and could be replaced with the call:
        //
        //     super.start(this.optionsString);
        //
        // The purpose of having it this way is to be able to get hold
        // of value of access "sessionId" (which is private in the parent class)
        // and make it available to clients via "getSessionId()" method.
		String[] args = {getFixedCommand(browserStartCommand), browserURL, "", optionsString};
		sessionId = getString("getNewBrowserSession", args);
		setSessionInProgress(sessionId);
	}
	
	private static String getFixedCommand(String browserStartCommand) {
		String cooperBrowserStartCommand;
		if(browserStartCommand.equals(FIREFOX2_LABEL)) {
			cooperBrowserStartCommand = "*firefox2 " + FIREFOX2_EXE;
		} else if (browserStartCommand.equals(FIREFOX3_LABEL)) {
			cooperBrowserStartCommand = "firefox3 " + FIREFOX3_EXE;
		}
		else {
			cooperBrowserStartCommand = browserStartCommand;
		}
		return cooperBrowserStartCommand;
	}

}
