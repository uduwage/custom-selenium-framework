/**
 * 
 */
package com.cannon.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author a83E1
 *
 */
public abstract class Solvent {

	private static final org.apache.log4j.Logger log = Logger.getLogger(Solvent.class.getName());
	
	private final HashMap<String, String> parameters = new HashMap<String, String>();
	
	/**
	 * This member is here strictly for convenience. It is a pain to keep it in sync, but 
	 * it saves having to type getSelenium() all the time.
	 */
	protected SolventSelenium selenium = null;
	
	/**
	 * Accessor for ThreadLocal session
	 * @return
	 */
	public SolventSelenium getSelenium() {
		return SeleniumSession.get();
	}
	
	public void setSelenium(SolventSelenium selenium) {
		SeleniumSession.set(selenium);
		//This is required to keep the Solvent instance's selenium member in
		//sync iwth the ThreadLocal var.
		this.selenium = getSelenium();
	}
	
	protected Solvent() {
		// This is required to keep this instance's "selenium" member in sync with the ThreadLocal session
		selenium = getSelenium();
	}
	
	public Solvent(String... params) {
		for(String param:params) {
			try {
				String name = param.substring(0, param.indexOf("=")).trim();
				String value = param.substring(param.indexOf("=")+1).trim();
				if(!parameters.containsKey(name))
					parameters.put(name, value);
				else
					log.warn("Duplicate parameter '" + name + "' given. Ignoring...");
			} catch (Exception e) {
				log.error("Error evaulating Solvent Parameter '" + param + 
						"'.  Correct format is 'name=value'. This parameter will be ignored.\n" + e.getMessage());
			}
		}
		// This is required to keep this instance's "selenium" member in sync with the ThreadLocal session
		selenium = SeleniumSession.get();
	}
	
	public String getParam(String name) {
		if(!parameters.containsKey(name))
			log.error("Requested parameter '" + name + "' does not exist! Returning null.");
		return parameters.get(name);
	}
	
	public void setParam(String name, String value) {
	    parameters.put(name, value);
	}

	protected Solvent(SolventSelenium solventSelenium) {
		this.setSelenium(solventSelenium);
	}
	
	/**
	 * The prepare method should implement any code that is required to prepare the application 
	 * under test for use by the Solvent. This method is called by the navigateTo() method.
	 */
	public abstract void prepare();
	
	/**
	 * This method simply calls a Solvents prepare method.
	 * @param <X>
	 * @param sol
	 * @return
	 */
	public <X extends Solvent> X navigateTo(X sol) {
		sol.prepare();
		return sol;
	}
	
	/**
	 * Calls the session stop() method which ends a "testComplete" command to the Selenium server to 
	 * shutdown the browser session.
	 */
	public void end() {
		getSelenium().stop();
		setSelenium(null);
	}

    /**
     * This method is used to encode strings in a method that is consistent with the 
     * format used for trail ids. Basically, all special (non-alphanumeric) characters are 
     * encoded using their ASCII value with the exception of the underbar ('_') character 
     * (ASCII value 95).
     * 
     * @param str The str to encode
     * @return The encoded String
     */
    public static String trailEncode(String str) {
    	StringBuffer fromStr = new StringBuffer(str);
    	StringBuffer rval = new StringBuffer();
    	
    	for(int i=0;i<fromStr.length();i++) {
    		int c = fromStr.charAt(i);
    		if((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 &&  c < 123) || c == 95)
    			rval.append(fromStr.charAt(i));
    		else 
    			rval.append("_u" + Integer.toString(c) + "_");
    	}
    	
    	return rval.toString();
    }
}
