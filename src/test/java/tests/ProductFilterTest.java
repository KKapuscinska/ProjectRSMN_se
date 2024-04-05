package test.java.tests;

import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import main.java.pages.ProductCatalogue;
import test.java.basetest.BaseTest;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProductFilterTest extends BaseTest {

	HomePage homePage;
	ProductCatalogue productCatalogue;
	
	@BeforeTest
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		homePage.acceptCookiesInCookieBar();
	}

	@BeforeMethod
	public void beforeMethod() {
		homePage.goToProductCataloguePage();
		
	}

	@Test(enabled = true)
	@Description("Verify that selecting the 'Feel Atmosphere' filter working correctly.")
	public void verifyFeelAtmosphereFilter() throws InterruptedException {

		productCatalogue.selectFeelAtmosphereFilter();
		
		boolean isFeelAtmosphereChipVisible = productCatalogue.isFeelAtmosphereChipVisibleOnProductCatalogue();
        Assert.assertTrue(isFeelAtmosphereChipVisible);
		
        Assert.assertEquals(productCatalogue.getFeelAtmosphereFilterChipText(), "CZUJESZ KLIMAT?");
        
		driver.navigate().refresh();
		
		//Check if random product on the page have FeelAtmosphere label
		productCatalogue.clickToRandomProductOnPage();
        
		boolean isFeelAtmosphereLabelVisible = productCatalogue.isFeelAtmosphereLabelVisibleOnProductPage();
        Assert.assertTrue(isFeelAtmosphereLabelVisible);
        
        driver.navigate().back();
        
        //Delete filter
        productCatalogue.clickCloseButtonOnFeelAtmosphereFilterLabel();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rossmann.pl/szukaj");
        
        boolean isFeelAtmosphereChipVisibleAfterRemoval = productCatalogue.isFeelAtmosphereChipVisibleOnProductCatalogue();
        Assert.assertFalse(isFeelAtmosphereChipVisibleAfterRemoval);
	}

	@Test(enabled = true)
	@Description("Verify that the 'Mega' filter displays only products with the 'MEGA!' badge.")
	public void verifyMegaFilter() throws InterruptedException {
	
		productCatalogue.selectMegaFilter();
		
		boolean isMegaChipVisible = productCatalogue.isMegaChipVisibleOnProductCatalogue();
        Assert.assertTrue(isMegaChipVisible);
        
		Assert.assertEquals(productCatalogue.getMegaFilterChipText(), "MEGA");
		
		Thread.sleep(1000);

		// Checking the display of products without 'Mega' badge
		List<String> productsWithoutBadge = productCatalogue
				.getProductsMissingBadge(productCatalogue.badgeSelectorMega);

		if (!productsWithoutBadge.isEmpty()) {
			for (String product : productsWithoutBadge) {
				System.out.println("No badge found for the following product: " + product);
			}
			throw new AssertionError("Test failed - some products do not have a 'Mega' badge on product image.");
		}

		//Delete filter
        productCatalogue.clickCloseButtonOnMegaFilterLabel();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rossmann.pl/szukaj");
        
        boolean isMegaChipVisibleAfterRemoval = productCatalogue.isMegaChipVisibleOnProductCatalogue();
        Assert.assertFalse(isMegaChipVisibleAfterRemoval);
        
	}
	
	@Test(enabled = true)
	@Description("Verify that the 'Promotion' filter is working correctly and displaying only products that are on promotion.")
	public void verifyPromotionFilter() throws InterruptedException {

		
		productCatalogue.selectPromotionsFilter();
		
		boolean isPromotionChipVisible = productCatalogue.isPromotionChipVisibleOnProductCatalogue();
        Assert.assertTrue(isPromotionChipVisible);
		
		Assert.assertEquals(productCatalogue.getPromotionsFilterChipText(), "PROMOCJA");
		
		driver.navigate().refresh();
		
		// Checking if every product on the page displays information about the lowest price.
		List<String> productsWithoutTheLowestPriceInfo = productCatalogue.getProductsMissingTheLowestPriceInformation();
		
		if (!productsWithoutTheLowestPriceInfo.isEmpty()) {
			for (String product : productsWithoutTheLowestPriceInfo) {
				System.out.println("No lowest price information found for the following product: " + product);
			}
			throw new AssertionError(
					"Test failed - some products do not have information available about the lowest price from the last 30 days.");
		}
		
		//Delete filter
        productCatalogue.clickCloseButtonOnPromotionFilterLabel();
        
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.rossmann.pl/szukaj");
        
        boolean isPromotionChipVisibleAfterRemoval = productCatalogue.isPromotionChipVisibleOnProductCatalogue();
        Assert.assertFalse(isPromotionChipVisibleAfterRemoval);

	}

	



	@Description("Verify that selecting the 'Sort By' option sorts products according to the selected criteria.")
	public void verifySortingOption() {

	}

	@Description("Verify selecting the 'Brand' filter shows only products from the selected brand.")
	public void verifyBrandFilter() {

	}

}
