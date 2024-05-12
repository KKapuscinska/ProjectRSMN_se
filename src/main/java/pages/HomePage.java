package main.java.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import main.java.pageObject.PageObject;


public class HomePage extends PageObject{

	WebDriver driver;
    Actions actions;
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
	}	
	
}
