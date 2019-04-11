package com.automation.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import junit.framework.Assert;

public class FacebookPage {
	private static final String CONTACT_US_MESSAGE ="THANK YOU FOR CONTACTING YETI";

	public static void main(String[] args)
	{
		System.setProperty("webdriver.chrome.driver", "c:\\selenium\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://yeti.formstack.com/forms/contactus");
		
		WebElement Fname = driver.findElement(By.xpath("//input[@id='field43290060-first']"));
		Fname.sendKeys("abhilash");
		WebElement Lname = driver.findElement(By.xpath("//input[@id='field43290060-last']"));
		Lname.sendKeys("siva");
		WebElement email = driver.findElement(By.xpath("//input[@id='field43290064']"));
		email.sendKeys("siva@gmail.com");
		WebElement Confirmemail = driver.findElement(By.xpath("//input[@id='field43290064_confirm']"));
		Confirmemail.sendKeys("siva@gmail.com");
		WebElement phone = driver.findElement(By.xpath("//input[@id='field43290683']"));
		phone.sendKeys("2342345678");
		WebElement streetAddress = driver.findElement(By.xpath("//input[@id='field43290169']"));
		streetAddress.sendKeys("105 prospect street");
		
		
		WebElement city = driver.findElement(By.xpath("//input[@id='field43290173']"));
		city.sendKeys("herndon");
		
		WebElement state = driver.findElement(By.xpath("//select[@id='field43290182']"));		
		Select stateDropDown = new Select(state);
		stateDropDown.selectByVisibleText("VA");
		
		
		WebElement zipCode = driver.findElement(By.xpath("//input[@id='field43290387']"));
		
		zipCode.sendKeys("20170");
		
		WebElement textArea = driver.findElement(By.xpath("//textarea[@id='field43291546']"));
		textArea.sendKeys("this is a test");
		
		WebElement submitButton = driver.findElement(By.xpath("//input[@id='fsSubmitButton2394224']"));
		submitButton.click();
		
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		
		WebElement message = driver.findElement(By.xpath("//h3[contains(text(),'Thank you for contacting')]"));
		String obtainedMessage = message.getText();
		
		System.out.println(obtainedMessage);

		

		
		
		
	
	}
	
}
