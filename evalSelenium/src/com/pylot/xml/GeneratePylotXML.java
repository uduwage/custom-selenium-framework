/**
 * 
 */
package com.pylot.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author A83E1
 * 
 */
public class GeneratePylotXML {

	public static void main(String[] args) throws FileNotFoundException {
		
		File testCase = new File("testcases.xml");
		PrintStream ps = new PrintStream(new FileOutputStream(testCase));

		String urlValue = "http://qa008:8080";
		
		ps.println("<?xml version=\"1.0\"?>");
		ps.println("<testcases>");
		ps.println("<method>"+"</method>");

	    for (int i = 1; i <= 10; i++) {
			ps.println("<case>");
			ps.println("<url>" + urlValue + "</url>");
			ps.println("<verify>" + "</verify>");
			ps.println("</case>");
	    }
	    
	    ps.println("</testcases>");
		ps.close();
	}
}
