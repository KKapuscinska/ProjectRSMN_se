package test.java.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import main.java.pages.ProfilePage;
import test.java.basetest.BaseTest;

public class AccountTest extends BaseTest{

	HomePage homePage;
	LoginPage loginPage;
	ProfilePage profilePage;
	
	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		profilePage = PageFactory.initElements(driver, ProfilePage.class);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeTest() {
		homePage.goToLoginPage();
	}
	
	@Test(dataProvider="getPlainUserLoginData")
	@Description("User verifies empty purchase history tab.")
	public void viewPurchaseHistoryTabNoHistory(HashMap<String, String> input) throws MalformedURLException, IOException {
		
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		homePage.clickAccountButtonIcon();
		profilePage.goToPurchaseHistoryTab();
		
		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_PROFILE_PURCHASE_RELATIVE),
				"Expected current URL to be purchase history page.");
		
	}

	
//	@Description("User checks for the presence of orders upon accessing the purchase history tab.")
//	public void viewPurchaseHistoryTabWithHistory(){}
//	
//	@Description("User views their favorite products upon entering the favorites tab.")
//	public void viewFavoriteProductsTabWithFavorites() {}
//
//	@Description("User can enable 'Show only favorite products on sale' feature.")
//	public void enableShowOnlyFavoritesOnSale() {}
//
//	@Description("User verifies empty favourite products tab.")
//	public void viewFavoriteProductsTabWithoutFavorites() {}
//	
//	@Description("User can add a new address in the account settings.")
//	public void addNewAddressInAccountSettings() {}
//
//	@Description("User can delete an existing address from the account settings.")
//	public void deleteExistingAddressFromAccountSettings() {}	
	
	@DataProvider
	public Object[][] getBasicUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\testdata\\login\\BasicUser.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider
	public Object[][] getClubUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\testdata\\login\\ClubUser.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider
	public Object[][] getPlainUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\testdata\\login\\PlainUser.json");
		return new Object[][]	{{data.get(0)}};
	}
}
