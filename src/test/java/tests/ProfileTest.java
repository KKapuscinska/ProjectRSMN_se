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
import main.java.pages.ProductCatalogue;
import main.java.pages.ProfilePage;
import test.java.basetest.BaseTest;

public class ProfileTest extends BaseTest{

	HomePage homePage;
	LoginPage loginPage;
	ProfilePage profilePage;
	ProductCatalogue productCatalogue;
	
	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		profilePage = PageFactory.initElements(driver, ProfilePage.class);
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		homePage.acceptCookiesInCookieBar();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeTest() {
		homePage.goToLoginPage();
	}
	
	@Test(dataProvider="getPlainUserLoginData")
	@Description("User verifies empty purchase history tab.")
	public void viewPurchaseHistoryTabNoHistory(HashMap<String, String> input) throws MalformedURLException, IOException {
		
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		profilePage.goToPurchaseHistoryTab();
		
		Assert.assertNotEquals(profilePage.purchaseHistoryEmptyBanner.getSize().getWidth(), 0,
			    "Expected to appear empty info banner.");
	}

	@Test(dataProvider="getBasicUserLoginData")
	@Description("User checks for the presence of orders upon accessing the purchase history tab.")
	public void viewPurchaseHistoryTabWithHistory(HashMap<String, String> input) throws MalformedURLException, IOException, InterruptedException {
		
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		profilePage.goToPurchaseHistoryTab();
		
		Thread.sleep(500);
		
		Assert.assertFalse(profilePage.purchaseHistoryList.isEmpty(), 
				"At least one purchase history element should be visible");
	}
	
	@Test(dataProvider="getPlainUserLoginData")
	@Description("User verifies empty favourite products tab.")
	public void viewFavoriteProductsTabWithoutFavorites(HashMap<String, String> input) throws MalformedURLException, IOException {
		
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		profilePage.goToFavoritesTab();

		homePage.waitForElementToAppearWebElement(profilePage.favoritesEmptyListButton);

		profilePage.favoritesEmptyListButton.click();

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_PRODUCTS_RELATIVE),
				"Expected current URL to be products page.");
	}
	
	@Test(dataProvider="getPlainUserLoginData")
	@Description("User navigates from the empty favorite products tab to the products page, adds and deletes favorite product.")
	public void addDeleteFavoriteProductFromFavTab(HashMap<String, String> input) throws MalformedURLException, IOException, InterruptedException  {
		
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		profilePage.goToFavoritesTab();

		Assert.assertEquals(profilePage.favoriteProductsCounter.getText(), "(0/500)",
				"Expected 0 products in favourite counter");

		profilePage.favoritesEmptyListButton.click();
		homePage.waitForElementToPresentNumberOfElements(productCatalogue.productListBy, productCatalogue.defaultProductsPerPage);
		productCatalogue.clickToRandomProductOnPage();

		homePage.waitForElementToAppearWebElement(productCatalogue.productPageFavoritesBtn);
		productCatalogue.productPageFavoritesBtn.click();

		Assert.assertEquals(homePage.checkAlertPopupMessage(), homePage.favoriteProductAddedPopupMessage,
				"Expected specific popup message");

		profilePage.goToFavoritesTab();

		Assert.assertEquals(profilePage.favoriteProductsCounter.getText(), "(1/500)",
				"Expected 1 product in favourite counter");

		Assert.assertEquals(profilePage.favoritesList.size(), 1,
				"Expected 1 favorite product on the list");

		productCatalogue.productPageFavoritesBtn.click();

		homePage.waitForElementToAppearWebElement(profilePage.favoritesEmptyListButton);

		Assert.assertEquals(profilePage.favoriteProductsCounter.getText(), "(0/500)",
				"Expected 0 product in favourite counter");
	}
	
	@Test(dataProvider="getBasicUserLoginData")
	@Description("User can enable 'Show only favorite products on sale' feature.")
	public void enableShowOnlyFavoritesOnSale(HashMap<String, String> input) throws MalformedURLException, IOException, InterruptedException {
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		profilePage.goToFavoritesTab();

		homePage.waitForElementToBeClickableWebElement(profilePage.onlyPromotionsFavSwitcher);
		profilePage.onlyPromotionsFavSwitcher.click();
		productCatalogue.getProductsMissingTheLowestPriceInformation();
	}



//	@Description("User can add a new address in the account settings.")
//	public void addNewAddressInAccountSettings() {}
//
//	@Description("User can delete an existing address from the account settings.")
//	public void deleteExistingAddressFromAccountSettings() {}	
	
	@DataProvider
	public Object[][] getBasicUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\BasicUser.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider
	public Object[][] getClubUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\ClubUser.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider
	public Object[][] getPlainUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\PlainUser.json");
		return new Object[][]	{{data.get(0)}};
	}
}
