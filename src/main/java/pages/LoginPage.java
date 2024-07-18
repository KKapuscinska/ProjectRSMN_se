package main.java.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

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
	
	@FindBy(xpath = "//*[@name='login']")
	WebElement loginInput;
	
	@FindBy(xpath = "//*[@name='password']")
	WebElement passwordInput;
	
	@FindBy(xpath="//button[@type='submit'][contains(text(),'Zaloguj się')][1]")
	WebElement loginButton;
	
	@FindBy(xpath="//*[@name='password']/following-sibling::button")
	WebElement showPasswordIcon;
	

	By loginButtonBy = By.xpath("//button[@type='submit'][contains(text(),'Zaloguj się')][1]");
    public By loginInputBy = By.xpath("//*[@name='login']");
    public By passwordInputBy = By.xpath("//*[@name='password']");
    public By invaldFeedbackBy = By.xpath("//div[contains(@class, 'Notification-module_label')][1]");
	
    //Methods related to LoginPage
    
	public void loginByCorrectCredentials(String mail, String password)
	{
		waitForElementToAppearWebElement(loginInput);
		loginInput.sendKeys(mail);
		passwordInput.sendKeys(password);
		loginButton.click();
		waitForElementToDisappearWebElement(loginButton);
	}
	
	public void logInByIncorrectCredentials(String mail, String password)
	{
		loginInput.sendKeys(mail);
		passwordInput.sendKeys(password);
		loginButton.click();
		waitForElementToAppear(invaldFeedbackBy);
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
		waitForElementToPresentValueWebElement(loginInput, "");
		waitForElementToPresentValueWebElement(passwordInput, "");
	}
}
 
