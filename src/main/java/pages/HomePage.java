package main.java.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BasePage {

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
