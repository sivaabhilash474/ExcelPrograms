package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FacebookHomePage {
	
	public final String FIRST_NAME = "//input[@id='u_0_n']";
	public final String PASSWORD = "//input[@id='u_0_p']";
	public final String MOBILE_OR_EMAIL = "//input[@id='u_0_s']";
	public final String NEW_PASSWORD = "//input[@id='u_0_z']";
	
	@CacheLookup
	@FindBy(xpath = FIRST_NAME)
	public WebElement firstName;
	
	@CacheLookup
	@FindBy(xpath = PASSWORD)
	public WebElement password;
	
	@CacheLookup
	@FindBy(xpath = MOBILE_OR_EMAIL)
	public WebElement mobileOrEmail;
	
	@CacheLookup
	@FindBy(xpath = NEW_PASSWORD)
	public WebElement newPassword;
	
	public static FacebookHomePage createPage(WebDriver webDriver)
	{
		FacebookHomePage page = PageFactory.initElements(webDriver, FacebookHomePage.class);
		return page;
	}
	
	
	public void setFormFieldValue()
	{
		firstName.sendKeys("siva");
		password.sendKeys("password");
		mobileOrEmail.sendKeys("xxx-xxx-xxxx");
		newPassword.sendKeys("newPassword");
		
	}
}
