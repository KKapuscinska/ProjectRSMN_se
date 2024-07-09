package main.java.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pageobject.PageObject;

public class ProfilePage extends PageObject{

		WebDriver driver;
	    Actions actions;
		
		public ProfilePage(WebDriver driver)
		{
			super(driver);
			this.driver = driver;
			this.actions = new Actions(driver);
	        PageFactory.initElements(driver, this);
		}	
		
	//WebElements declarations
		
	@FindBy(xpath="//section/div/div/div/nav/a")
	public
	List<WebElement> profileLinkList;
	
	//PurchaseHstory Tab
	@FindBy(xpath="//a[contains(@href,'/profil/zamowienia') and contains(@class, 'Nav-module_link')]")
	public
	WebElement purchaseHistoryTab;
	
	@FindBy(xpath="//article[contains(@class, 'empty-list')]")
	public
	WebElement purchaseHistoryEmptyBanner;
	
	@FindBy(xpath="//div[contains(@class, 'history__card')]")
	public
	List<WebElement> purchaseHistoryList;
	
	By purchaseHistoryTabBy = By.xpath("//a[contains(@href,'/profil/zamowienia') and contains(@class, 'Nav-module_link')]");
	
	//Favorites Tab
	
	@FindBy(xpath="//a[contains(@href,'/profil/ulubione') and contains(@class, 'Nav-module_link')]")
	public
	WebElement favoritesTab;

	@FindBy(xpath="(//a[contains(text(),'Przeglądaj produkty')])")
	public
	WebElement favoritesEmptyListButton;

	@FindBy(xpath="//div/div[@class='ui-row']//strong")
	public
	WebElement favoriteProductsCounter;

	@FindBy(xpath="//section/div/div/section/div[2]/div")
	public
	List<WebElement> favoritesList;

	@FindBy(xpath="//input[@name='isOnPromotion']/parent::label/span")
	public
	WebElement onlyPromotionsFavSwitcher;


	By favoritesTabBy = By.xpath("//a[contains(@href,'/profil/ulubione') and contains(@class, 'Nav-module_link')]");
	public By favoritesEmptyListButtonBy = By.xpath("//a[contains(text(),'Przeglądaj produkty')]");
	public By onlyPromotionsFavSwitcherBy = By.xpath("//input[@name='isOnPromotion']/parent::label/span");
	
	//Methods related to Account
	
	public void goToPurchaseHistoryTab() {
		clickAccountButtonIcon();
		waitForElementToAppearWebElement(purchaseHistoryTab);
		purchaseHistoryTab.click();
		waitForUrlToContains(URL_PROFILE_PURCHASE_RELATIVE);
	}
	
	public void goToFavoritesTab() {
		clickAccountButtonIcon();
		waitForElementToAppearWebElement(favoritesTab);
		favoritesTab.click();
		waitForUrlToContains(URL_FAVORITES_RELATIVE);
	}
	
	
}
