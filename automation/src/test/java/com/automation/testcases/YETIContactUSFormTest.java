package com.automation.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.automation.pages.YETIContacUSFormPage;

public class YETIContactUSFormTest {
	static WebDriver driver;
	static YETIContacUSFormPage page;
	public static YETIContacUSFormPage getPage()
	{
		System.setProperty("webdriver.chrome.driver", "c:\\selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://yeti.formstack.com/forms/contactus");
		if(page==null)
		{
			page = YETIContacUSFormPage.createPage(driver); 
		}
		return page;
	}
	
	
	
	public static void main(String[] args)
	{
		
		getPage();
		//YETIContacUSFormPage.setFormFieldValue("abhilash");
		//contact.setFormFieldValue("abhilash");
		driver.quit(); 
	}

	
	
}
