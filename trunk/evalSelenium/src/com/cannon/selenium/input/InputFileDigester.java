/**
 * 
 */
package com.cannon.selenium.input;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author a83E1
 *
 */
public class InputFileDigester {

	private static final Logger log = Logger.getLogger(InputFileDigester.class.getName());
	
	private Document doc;
	
	/**
	 * Builds Document object from the specified InputStream
	 * @param in The InputStream to read the document from.
	 */
	public InputFileDigester(InputStream in) {
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(in);
		}catch (DocumentException e) {
			log.error("Error when attempting to parse input file", e );
		}
	}
	
	/**
	 * Parses parameters from the xml file (input file) and places then in 
	 * a HashMap for use the test.
	 * @param params 
	 * @return the HashMap populated with the params, if any were found.
	 */
	public HashMap<String, Object> parseParams(HashMap<String, Object> params) {
		
		Element root = doc.getRootElement();
		for(Iterator i = root.elementIterator("Parameter"); i.hasNext();) {
			Element elm = (Element) i.next();
			String paramName = elm.attributeValue("name");
			if(elm.elements().size() < 2) {
				String paramValue = elm.element("value").getText();
				log.info("Adding input param: " + paramName + "=> " + paramValue);
			}
			else {
				ArrayList<String> values = new ArrayList<String>();
				for(Iterator j = elm.elementIterator("value"); j.hasNext();) {
					values.add(((Element)j.next()).getText());
				}
				params.put(paramName, values.toArray(new String[0]));
			}
		}
		return params;
	}
}
