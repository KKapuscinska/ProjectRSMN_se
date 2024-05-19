package main.java.pageobject;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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
	
	//Pages
	public static final String URL_HOME_PAGE = "https://www.rossmann.pl/";
    public static final String URL_CONTACT_PAGE = "https://www.rossmann.pl/kontakt";
    public static final String URL_LOGIN_PAGE = "https://www.rossmann.pl/logowanie";
    public final String URL_LOGIN_PAGE_RELATIVE = "/logowanie";
    public static final String URL_PRODUCT_CATALOGUE_PAGE = "https://www.rossmann.pl/szukaj";
    public final String URL_CART_RELATIVE = "/zamowienie/koszyk";
    public final String URL_PROFILE_RELATIVE = "/profil";

	
	//WebElements declarations
	
	@FindBy(xpath = "//a[@title='Profil']")
    WebElement userAccountLink;
	
	@FindBy(xpath="//button[text()='Zaloguj się']")
	WebElement loginButton;
    
    @FindBy(xpath="//button[text()='Wyloguj się']")
	WebElement logoutButton;

    @FindBy(xpath = "//a[@title='Koszyk']")
   	WebElement shoppingCartLink;
    
    @FindBy(xpath="//div[contains(@class, 'StatusBadge-module_badge')]")
	public
	WebElement shoppingCartQuantityIcon;
    
	@FindBy(id = "onetrust-accept-btn-handler")
    WebElement acceptBtnInCookieBar;
	
	//Link lists
	@FindBy(xpath="//*[@id=\"header\"]/section/section/div[2]/div[starts-with(@class, 'NavUserButtons-module_btnWithDropdown')]/a")
	public
	List<WebElement> navUserDropdownButtonsList;
	
	@FindBy(xpath="//*[@id=\"header\"]/section/section/div[2]/a[starts-with(@class, 'NavUserButtons-module')]")
	public
	List<WebElement> navUserButtonsList;
	
	@FindBy(xpath="//*[@id='__next']/section//section//ul//li//a")
	public
	List<WebElement> topBarCategoryLinkList;
	
	@FindBy(xpath="//*[@id='__next']/section/section/div/div[starts-with(@class, 'Capsules-module_capsules')]//a")
	public
	List<WebElement> capsuleLinkList;
	
	@FindBy(xpath="//*[@id='__next']/section/section/div/div[starts-with(@class, 'Capsules-module_campaigns')]//a")
	public
	List<WebElement> campainLinkList;
	
	@FindBy(xpath="//*[@id='__next']/section/section/div//div[starts-with(@class, 'Navigation-module_content')]//div//a")
	public
	List<WebElement> footerLinkList;
	
	
	By cookieBarBy = By.cssSelector(".ot-sdk-container");
	By acceptBtnInCookieBarBy = By.id("onetrust-accept-btn-handler");
	By userAccountDropdownBy = By.xpath("//*[starts-with(@class, 'NavUserButtons-module_dropContent')] /div");
	By loginPopupBy = By.className("login-form__wrapper");

	
	//Methods related to pages
	public void goToHomePage() {
		driver.get(URL_HOME_PAGE);
	}
		
	public void goToContactPage() {
		driver.get(URL_CONTACT_PAGE);
	}
		
	public void goToLoginPage() {
		driver.get(URL_LOGIN_PAGE);
	}
		
	public void goToProductCataloguePage() {
		driver.get(URL_PRODUCT_CATALOGUE_PAGE);
	}
		
	public void goToShoppingCart() throws InterruptedException {
		shoppingCartLink.click();
		waitForUrlToContains(URL_CART_RELATIVE);
		Thread.sleep(1000);
	}

	// Methods related to every page
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
	
	public void maximizePage() {
		driver.manage().window().maximize();
	}
	
	public void scrollToTopOfPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
	
	public void scrollToBottomOfPage() {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	
	
}
