package test.java.tests;

import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.ProductCatalogue;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductFilterTest extends BaseTest {
	ProductCatalogue productCatalogue;

	@BeforeClass
	public void setup() throws IOException {
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		productCatalogue.acceptCookiesInCookieBar();
	}

	@BeforeMethod
	public void beforeMethod() {
		productCatalogue.goToProductCataloguePage();
	}

	@Test(enabled = true)
	@Description("Verify that selecting the 'Feel Atmosphere' filter working correctly.")
	public void verifyFeelAtmosphereFilter() throws InterruptedException {

		productCatalogue.selectFeelAtmosphereFilter();
		boolean isFeelAtmosphereChipVisible = productCatalogue.isFeelAtmosphereChipVisibleOnProductCatalogue();

		Assert.assertTrue(isFeelAtmosphereChipVisible,
        		"Expected 'Feel Atmosphere' chip to be visible on the product catalogue page.");

        Assert.assertEquals(productCatalogue.getFeelAtmosphereFilterChipText(), "CZUJESZ KLIMAT?",
        		"Expected the 'Feel Atmosphere' filter chip to be displayed with specific text.");

		driver.navigate().refresh();

		//Check if random product on the page have FeelAtmosphere label
		productCatalogue.clickToRandomProductOnPage();
		boolean isFeelAtmosphereLabelVisible = productCatalogue.isFeelAtmosphereLabelVisibleOnProductPage();

		Assert.assertTrue(isFeelAtmosphereLabelVisible,
        		"Expected 'Feel Atmosphere' label to be visible on the product page.");

        driver.navigate().back();

        //Delete filter
        productCatalogue.clickCloseButtonOnFeelAtmosphereFilterLabel();

		Assert.assertEquals(driver.getCurrentUrl(), productCatalogue.URL_PRODUCT_CATALOGUE_PAGE,
        		"Expected current URL to be productCatalogue page.");

        boolean isFeelAtmosphereChipVisibleAfterRemoval = productCatalogue.isFeelAtmosphereChipVisibleOnProductCatalogue();

		Assert.assertFalse(isFeelAtmosphereChipVisibleAfterRemoval,
        		"Expected 'Feel Atmosphere' chip to be not visible on the product catalogue page.");
	}

	@Test(enabled = true)
	@Description("Verify that the 'Mega' filter displays only products with the 'MEGA!' badge.")
	public void verifyMegaFilter() throws InterruptedException {

		productCatalogue.selectMegaFilter();
		boolean isMegaChipVisible = productCatalogue.isMegaChipVisibleOnProductCatalogue();

		Assert.assertTrue(isMegaChipVisible,
        		"Expected 'Mega' chip to be visible on the product catalogue page.");

		Assert.assertEquals(productCatalogue.getMegaFilterChipText(), "MEGA",
        		"Expected the 'Mega' filter chip to be displayed with specific text.");

		Thread.sleep(1000);

		// Checking the display of products without 'Mega' badge
		List<String> productsWithoutBadge = productCatalogue
		        .getProductsMissingBadge(productCatalogue.badgeSelectorMegaXPath);

		if (!productsWithoutBadge.isEmpty()) {
			for (String product : productsWithoutBadge) {
				System.out.println("No badge found for the following product: " + product);
			}
			throw new AssertionError("Test failed - some products do not have a 'Mega' badge on product image.");
		}

		//Delete filter
        productCatalogue.clickCloseButtonOnMegaFilterLabel();

		Assert.assertEquals(driver.getCurrentUrl(), productCatalogue.URL_PRODUCT_CATALOGUE_PAGE,
        		"Expected current URL to be productCatalogue page.");

        boolean isMegaChipVisibleAfterRemoval = productCatalogue.isMegaChipVisibleOnProductCatalogue();

		Assert.assertFalse(isMegaChipVisibleAfterRemoval,
        		"Expected 'Mega' chip to be not visible on the product catalogue page.");

	}

	@Test(enabled = true)
	@Description("Verify that the 'Promotion' filter is working correctly and displaying only products that are on promotion.")
	public void verifyPromotionFilter() throws InterruptedException {

		productCatalogue.selectPromotionsFilter();
		boolean isPromotionChipVisible = productCatalogue.isPromotionChipVisibleOnProductCatalogue();

		Assert.assertTrue(isPromotionChipVisible,
        		"Expected 'Promotion' chip to be visible on the product catalogue page.");

		Assert.assertEquals(productCatalogue.getPromotionFilterChipText(), "PROMOCJA",
        		"Expected the 'Promotion' filter chip to be displayed with specific text.");

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

        Assert.assertEquals(driver.getCurrentUrl(), productCatalogue.URL_PRODUCT_CATALOGUE_PAGE,
        		"Expected current URL to be productCatalogue page.");

        boolean isPromotionChipVisibleAfterRemoval = productCatalogue.isPromotionChipVisibleOnProductCatalogue();

		Assert.assertFalse(isPromotionChipVisibleAfterRemoval,
        		"Expected 'Promotion' chip to be not visible on the product catalogue page.");

	}


	@Description("Verify that selecting the 'Sort By' option sorts products according to the selected criteria.")
	public void verifySortingOption() {

	}

	@Description("Verify selecting the 'Brand' filter shows only products from the selected brand.")
	public void verifyBrandFilter() {

	}

}
