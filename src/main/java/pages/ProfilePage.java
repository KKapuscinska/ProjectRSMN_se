package main.java.pages;

import java.util.List;

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
	
	@FindBy(xpath="//section/div/div/div/nav/a[1]")
	public
	WebElement purchaseHistoryTab;
	
	
	//Methods related to Account
	
	public void goToPurchaseHistoryTab() {
		purchaseHistoryTab.click();
		waitForUrlToContains(URL_PROFILE_PURCHASE_RELATIVE);
	}
}
