package main.java.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
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
