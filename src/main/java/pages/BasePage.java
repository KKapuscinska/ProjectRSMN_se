package main.java.pages;

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



public class BasePage {

	WebDriver driver;
	Actions actions;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
	}

    
	
	//Wait
	public void waitForElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public WebElement waitForElementToAppearWebElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToDisappear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}

	public void waitForElementToDisappearWebElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public void waitForElementToBeClickable(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}

	public void waitForElementToBeClickableWebElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForElementToPresentValue(By findBy, String Text)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.textToBePresentInElementValue(findBy, Text));
	}

	public void waitForElementToPresentValueWebElement(WebElement element, String Text)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.textToBePresentInElementValue(element, Text));
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
    public final String URL_PRODUCTS_RELATIVE = "/produkty";
    public final String URL_PROFILE_PURCHASE_RELATIVE = "/profil/zamowienia";
    public final String URL_FAVORITES_RELATIVE = "/profil/ulubione";
	public final String URL_CHECKOUT_RELATIVE = "/zamowienie/kasa";
	
	//WebElements declarations
	
	@FindBy(xpath = "//a[@title='Profil']")
    WebElement userAccountLink;
	
	@FindBy(xpath="//button[text()='Zaloguj się']")
	WebElement loginButton;
    
    @FindBy(xpath="//button[text()='Wyloguj się']")
	WebElement logoutButton;

    @FindBy(xpath = "//a[@title='Koszyk']")
   	WebElement shoppingCartLink;

	@FindBy(xpath="//div[3]/a/div[contains(@class, 'StatusBadge-module_badge')]")
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
	public By loginPopupBy = By.className("login-form");

	public By shoppingCartQuantityIconBy = By.xpath("//div[contains(@class, 'StatusBadge-module_badge')]");

	//aletr popups
	@FindBy(xpath="//div[@class='Toastify__toast-body']")
	public
	WebElement alertPopupBody;
	By alertPopupBodyBy = By.xpath("//div[@class='Toastify__toast-body']");
	public String favoriteProductAddedPopupMessage = "Dodano produkt do Ulubionych";
	
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
		
	public void goToShoppingCart() {
		shoppingCartLink.click();
		waitForUrlToContains(URL_CART_RELATIVE);
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
	   waitForElementToDisappearWebElement(logoutButton);
	}
		
	public void acceptCookiesInCookieBar(){
		waitForElementToAppear(cookieBarBy);
		waitForElementToBeClickableWebElement(acceptBtnInCookieBar);
		acceptBtnInCookieBar.click();
		waitForElementToDisappear(cookieBarBy);
	}
	
	public String checkAlertPopupMessage(){
		waitForElementToAppear(alertPopupBodyBy);
		return alertPopupBody.getText();
	}

	public void scrollToTopOfPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
	
	public void scrollToBottomOfPage() {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollToElementCenter(WebElement element) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		waitForElementToAppearWebElement(element);
	}

	public String getProductQuantityFromIcon() {
		return shoppingCartQuantityIcon.getText();
	}
}
