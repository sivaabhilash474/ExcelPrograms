package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YETIContacUSFormPage {
	String fname;

	private static final String Fname = "//input[@id='field43290060-first']";
	private static final String Lname = "//input[@id='field43290060-last']";
	private static final String Email = "//input[@id='field43290064']";
	private static final String CONFEmail = "//input[@id='field43290064_confirm']";
	
//	public WebDriver driver;
	
	@CacheLookup
	@FindBy(xpath=Fname)
	public static WebElement firstName;
	
	@CacheLookup
	@FindBy(xpath=Lname)
	public static WebElement lastName;
	
	@CacheLookup
	@FindBy(xpath=Email)
	public static WebElement firstemail;
	
	@CacheLookup
	@FindBy(xpath=CONFEmail)
	public static WebElement confirmEmail;
	
	public static YETIContacUSFormPage createPage(WebDriver webdriver)
	{
		YETIContacUSFormPage page = PageFactory.initElements(webdriver, YETIContacUSFormPage.class);
		return page;
	}
	
	
	/*public YETIContacUSFormPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}*/
	
	public static void setFormFieldValue(String fname, String lname, String email, String confEmail)
	{
		firstName.sendKeys(fname);
		lastName.sendKeys(lname);
		firstemail.sendKeys(email);
		confirmEmail.sendKeys(confEmail);
		
	}
	
	
}
