package main.java.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pageobject.PageObject;

public class LoginPage extends PageObject{

	WebDriver driver;
	Actions actions;
	
	public LoginPage(WebDriver driver) 
	{
		super(driver);
		this.driver=driver;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	//WebElements declarations
	
	@FindBy(id="login-user")
	WebElement loginInput;
	
	@FindBy(id="login-password")
	WebElement passwordInput;
	
	@FindBy(xpath="//span[text()='Zaloguj się']")
	WebElement loginButton;
	
	@FindBy(css=".input-group-text")
	WebElement showPasswordIcon;
	
    By loginPopupBy = By.className("login-form");
    public By loginInputBy = By.id("login-user");
    public By passwordInputBy = By.id("login-password");
    public By invaldFeedbackBy = By.cssSelector(".invalid-feedback");
	
    //Methods related to LoginPage
    
	public void loginByCorrectCredentials(String mail, String password)
	{
		loginInput.sendKeys(mail);
		passwordInput.sendKeys(password);
		loginButton.click();
		waitForElementToDisappear(loginPopupBy);
	}
	
	public void logInByIncorrectCredentials(String mail, String password) throws InterruptedException
	{
		loginInput.sendKeys(mail);
		passwordInput.sendKeys(password);
		loginButton.click();
		Thread.sleep(1000);
	}
	
	public void clickShowPasswordIcon() throws InterruptedException
	{
		showPasswordIcon.click();
		Thread.sleep(1000);
	}
	
	public void clearLoginFields()
	{
		loginInput.clear();
		passwordInput.clear();
		waitForElementToPresentValue(loginInputBy, "");
		waitForElementToPresentValue(passwordInputBy, "");
	}
}
 
