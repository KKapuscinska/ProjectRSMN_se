package main.java.pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageObject {

	WebDriver driver;
	Actions actions;
	
	public PageObject(WebDriver driver) { 
		this.driver = driver;
		this.actions = new Actions(driver);
	}

    
	
	//Wait
	public void waitForElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public WebElement waitForElementToAppearWebElement(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToDisappear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToBeClicable(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}
	
	public void waitForElementToPresentValue(By findBy, String Text)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.textToBePresentInElementValue(findBy, Text));
	}
	
	public void waitForUrlToContains(String Text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.urlContains(Text));
	}
	
	public void waitForElementToPresentNumberOfElements(By findBy, int Value)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.numberOfElementsToBe(findBy, Value));
		
	}
	
	//WebElements declarations
	
	@FindBy(css = ".nav-user-product__btn")
    WebElement goToCartBtn;
	
	@FindBy(css = "a[title='Konto użytkownika']")
    WebElement userAccountLink;
	
	@FindBy(xpath="//span[text()='Zaloguj się']")
	WebElement loginButton;
    
    @FindBy(xpath="//button[text()='Wyloguj']")
	WebElement logoutButton;

    @FindBy(css = "a[title='Koszyk']")
   	WebElement shoppingCartLink;
    
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

	
	//Methods related to pages
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
		
	public void goToShoppingCart() throws InterruptedException {
		shoppingCartLink.click();
		waitForUrlToContains("/zamowienie/koszyk");
		Thread.sleep(1000);
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
		
	// Methods related to every page
	public void acceptCookiesInCookieBar(){
		waitForElementToAppear(cookieBarBy);
		waitForElementToBeClicable(acceptBtnInCookieBarBy);
		acceptBtnInCookieBar.click();
		waitForElementToDisappear(cookieBarBy);	
	}
	
	public void maximizePage() {
		driver.manage().window().maximize();
	}
	
	public void scrollToTopOfPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
	
	
	
}
