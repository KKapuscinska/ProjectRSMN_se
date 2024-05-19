package test.java;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;

import main.java.pages.HomePage;
import test.java.basetest.BaseTest;

public class ProfileTest extends BaseTest{

	

	
	
	@Description("User verifies empty purchase history tab.")
	public void viewPurchaseHistoryTabNoHistory(){
	
	}

	@Description("User checks for the presence of orders upon accessing the purchase history tab.")
	public void viewPurchaseHistoryTabWithHistory(){}
	
	@Description("User views their favorite products upon entering the favorites tab.")
	public void viewFavoriteProductsTabWithFavorites() {}

	@Description("User can enable 'Show only favorite products on sale' feature.")
	public void enableShowOnlyFavoritesOnSale() {}

	@Description("User verifies empty favourite products tab.")
	public void viewFavoriteProductsTabWithoutFavorites() {}
	
	@Description("User can add a new address in the account settings.")
	public void addNewAddressInAccountSettings() {}

	@Description("User can delete an existing address from the account settings.")
	public void deleteExistingAddressFromAccountSettings() {}
	
}
