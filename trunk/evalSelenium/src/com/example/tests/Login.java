package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.DefaultSelenium;

import java.util.regex.Pattern;

public class Login {
	static Selenium browser;
	
	public static void main(String arg[])
	{
		browser = new DefaultSelenium("localhost",
	            4444, "*pifirefox", "http://10.106.33.89:8080/login.jsp");

		browser.start();
		
		browser.open("http://qa007:8080/login.jsp");
		browser.type("USERNAME", "yukon");
		browser.type("PASSWORD", "yukon");
		browser.click("login");
		//browser.waitForPageToLoad("30000");
	}
	
}
