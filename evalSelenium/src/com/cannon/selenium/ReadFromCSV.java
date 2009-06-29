/**
 * 
 */
package com.cannon.selenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;

/**
 * This class will extract username and password from csv file.
 * @author a83E1
 * 
 */
public class ReadFromCSV {

	private String username;
	private String password;
	
	@Before
	public void setUp() {
		
	}

	/**
	 * Reading the the csv file and add to a String collection.
	 * 
	 * @param filename name of the csv file.
	 * @return lines collection of username and password.
	 */
	public Collection<String> parse(String filename) {
		Collection<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					filename)));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int i;
		for (i = 0; i < userInfo(lines).size(); i++) {
			if (i < 1) {
				this.username = userInfo(lines).get(i);
				this.password = userInfo(lines).get(i+1);
				//System.out.println(username + "-" + password);
			}
		}		
	
		return lines;
	}


	/**
	 * split the line at the end and format.
	 * 
	 * @param lines collection of lines
	 */
	public ArrayList<String> userInfo(Collection<String> lines) {
		ArrayList<String> userNamePasswordArrayList = new ArrayList<String>();
		for (String line : lines) {
			String[] recordsOnLine = line.split(",");
			for (String record : recordsOnLine) {
				userNamePasswordArrayList.add(record);
			}
		}
		return userNamePasswordArrayList;
	}

	/**
	 * Reminder: File needs to be at the root level of the workspace (in
	 * eclipse) I found this by hard way.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new ReadFromCSV().parse("UserName_Password.csv");

	}


	/**
	 * Return username from csv file. 
	 * @return username yukon app username
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Return password from csv file.
	 * @return password yukon password.
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
