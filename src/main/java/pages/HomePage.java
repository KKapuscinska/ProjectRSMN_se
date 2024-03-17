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
	
	
	@FindBy(css = "a[title='Konto użytkownika']")
    WebElement userAccountLink;
	
	@FindBy(xpath="//span[text()='Zaloguj się']")
	WebElement loginButton;
    
    @FindBy(xpath="//button[text()='Wyloguj']")
	WebElement logoutButton;

	@FindBy(id = "onetrust-accept-btn-handler")
    WebElement acceptBtnInCookieBar;
	
	
	@FindBy(css=".nav__link")
	public
	List<WebElement> mainCategoryList;
	
	@FindBy(css=".sub-nav__link")
	public
	List<WebElement> subCategoryList;


	By cookieBarBy = By.cssSelector(".ot-sdk-container");
	By acceptBtnInCookieBarBy = By.id("onetrust-accept-btn-handler");
	By userAccountDropdownBy = By.cssSelector("a[title='Konto użytkownika']+.nav-user__dropdown");
	By loginPopupBy = By.className("login-form");
    
	
	//Pages
	public void goToHomePage() {
		driver.get("https://www.rossmann.pl/");
	}
	
	public void goToContactPage() {
		driver.get("https://www.rossmann.pl/kontakt");
	}
	
	public void goToLoginPage() {
		driver.get("https://www.rossmann.pl/logowanie");
	}
	
	public void goToProductCataloguePage() {
		driver.get("https://www.rossmann.pl/szukaj");
	}
	
	public void maximizePage() {
		driver.manage().window().maximize();
	}
	
	
	
	public void clickAccountButtonIcon() {
		userAccountLink.click();
    }
	
	
	public void hoverOverUserAccountLinkAndClickLogin() {
        actions.moveToElement(userAccountLink).build().perform();
        waitForElementToAppear(userAccountDropdownBy);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginButton.click();
        waitForElementToAppear(loginPopupBy);
    }
	
	public void logout() {
        actions.moveToElement(userAccountLink).build().perform();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logoutButton.click(); 
        waitForElementToDisappear(userAccountDropdownBy);
    }
	
	
	
	public void acceptCookiesInCookieBar(){
		waitForElementToAppear(cookieBarBy);
		waitForElementToBeClicable(acceptBtnInCookieBarBy);
		acceptBtnInCookieBar.click();
		waitForElementToDisappear(cookieBarBy);
		
	}
	
	
}
