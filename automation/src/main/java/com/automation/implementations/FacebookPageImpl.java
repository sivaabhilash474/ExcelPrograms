package com.automation.implementations;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FacebookPageImpl {
	//WebDriver driver;
/*	 WebElement firstName = driver.findElement(By.xpath("//input[@id='u_0_n']"));
	 WebElement password = driver.findElement(By.xpath("//input[@id='u_0_p']"));
	 WebElement mobileOrEmail = driver.findElement(By.xpath("//input[@id='u_0_s']"));
	 WebElement newPassword = driver.findElement(By.xpath("//input[@id='u_0_z']"));*/
	
	public static void main(String[] args) throws AWTException
	{
		System.setProperty("webdriver.chrome.driver", "c:\\selenium\\chromedriver.exe");
		FacebookPageImpl impl = new FacebookPageImpl();
		WebDriver driver = new ChromeDriver();
		//driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement firstName = driver.findElement(By.xpath("//input[@name='q']"));
		WebElement searchButton = driver.findElement(By.xpath("//div[@class='jsb']//child::input[@type='submit'][1]"));
		firstName.sendKeys("cheese");
		Robot robot = new Robot();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		WebElement wikiLink = driver.findElement(By.xpath("//a[contains(text(),'Cheese - Wikipedia')]"));
		wikiLink.click();
		driver.quit();
		
		
	}
	
}
