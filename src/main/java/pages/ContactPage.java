package main.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage extends BasePage {

WebDriver driver;
	
	public ContactPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//WebElements declarations
		
	@FindBy(xpath = "//span[text()='Wyślij wiadomość']")
    WebElement sendContactFormButton;
	
	@FindBy(id = "input-email")
    WebElement emailInput;
	
	@FindBy(id = "message")
	public
    WebElement messageInput;
	
	@FindBy(id = "input-phone")
	public
    WebElement phoneInput;
	
	@FindBy(id = "input-name-1")
    WebElement nameInput;
	
	@FindBy(id = "input-name-2")
    WebElement lastNameInput;
	
	@FindBy(css = "div[name='recaptcha']")
    WebElement captchaSelector;
	
	public By nameInputBy = By.id("input-name-1");
	public By lastNameInputBy = By.id("input-name-2");
	public By emailInputBy = By.id("input-email");
	public By messageInputBy = By.id("message");
	public By phoneInputBy = By.id("input-phone");
	public By valiadationMessageBy = By.cssSelector(".invalid-feedback");
	public By recaptchaBy = By.cssSelector("div[name='recaptcha']");
	public By sendFormBtnBy = By.xpath("//span[text()='Wyślij wiadomość']");

	
	 
	//Methods related to contactPage
	
	public void sendContactForm()
	{
		scrollToElementCenter(sendContactFormButton);
		waitForElementToBeClickableWebElement(sendContactFormButton);
		sendContactFormButton.click();
	}
	
	public void fillEmail(String email)
	{
		emailInput.clear();
		emailInput.sendKeys(email);
	}
	
	public void fillMessage(String message)
	{
		messageInput.clear();
		messageInput.sendKeys(message);
	}
	
	public void fillPhone(String phone)
	{
		phoneInput.clear();
		phoneInput.sendKeys(phone);
	}
	
	public void fillName(String name)
	{
		nameInput.clear();
		nameInput.sendKeys(name);
	}
	
	public void fillLastName(String lastName)
	{
		lastNameInput.clear();
		lastNameInput.sendKeys(lastName);
	}
}
