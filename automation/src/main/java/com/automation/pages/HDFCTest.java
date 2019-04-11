package com.automation.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HDFCTest {
	final static Logger logger = Logger.getLogger(HDFCTest.class);
	public static void main(String[] args) throws InterruptedException{
		
	System.setProperty("webdriver.chrome.driver","c:\\selenium\\chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	logger.info("Opening browser instance");
	driver.get("https://www.hdfcbank.com/");
	
	
	
	Thread.sleep(30*1000);
	List<WebElement> links = driver.findElements(By.tagName("a"));
	int n = links.size();
	logger.info("Number of links in the web page are: "+n);
	logger.info("Closing browser.........");
	driver.quit();
		
		
		
		
	}

}
