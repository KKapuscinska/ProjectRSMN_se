package test.java.tests;

import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.ProductCatalogue;
import test.java.basetest.BaseTest;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProductFilterTest extends BaseTest {

	@BeforeTest
	public void setup() throws IOException {
		HomePage homePage = new HomePage(driver);
		homePage.acceptCookiesInCookieBar();
	}

	@BeforeMethod
	public void beforeMethod() {
		HomePage homePage = new HomePage(driver);
		homePage.goToProductCataloguePage();
	}

	@Test(enabled = true)
	@Description("Verify that selecting the 'Feel Atmosphere' checkbox enables the filter.")
	public void verifyFeelAtmosphereFilter() throws InterruptedException {
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);

		productCatalogue.selectFilter("Czujesz klimat?");
		Assert.assertTrue(productCatalogue.isFilterSelected("Czujesz klimat?"));
		productCatalogue.clickShowResultsBtn();
		Assert.assertEquals(productCatalogue.getFilterTagText(), "CZUJESZ KLIMAT?");
	}

	@Test(enabled = true)
	@Description("Verify that the 'promotion' filter displays only products that are on promotion.")
	public void verifyPromotionsFilter() throws InterruptedException {
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);

		productCatalogue.selectFilter("Promocja");
		Assert.assertTrue(productCatalogue.isFilterSelected("Promocja"));
		productCatalogue.clickShowResultsBtn();
		Assert.assertEquals(productCatalogue.getFilterTagText(), "PROMOCJA");

		List<String> productsWithoutTheLowestPriceInfo = productCatalogue.getProductsMissingTheLowestPriceInformation();

		// Checking the display of products without information about the lowest price
		if (!productsWithoutTheLowestPriceInfo.isEmpty()) {
			for (String product : productsWithoutTheLowestPriceInfo) {
				System.out.println("No lowest price information found for the following product: " + product);
			}
			throw new AssertionError(
					"Test failed - some products do not have information available about the lowest price from the last 30 days.");
		}

	}

	@Test
	@Description("Verify that the 'Mega' filter displays only products with the 'MEGA!' badge.")
	public void verifyMegaFilter() throws InterruptedException {
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);

		productCatalogue.selectFilter("Mega");
		Assert.assertTrue(productCatalogue.isFilterSelected("Mega"));
		productCatalogue.clickShowResultsBtn();
		Assert.assertEquals(productCatalogue.getFilterTagText(), "MEGA");

		Thread.sleep(1500);

		List<String> productsWithoutBadge = productCatalogue
				.getProductsMissingBadge(productCatalogue.badgeSelectorMega);

		// Checking the display of products without 'Mega' badge
		if (!productsWithoutBadge.isEmpty()) {
			for (String product : productsWithoutBadge) {
				System.out.println("No badge found for the following product: " + product);
			}
			throw new AssertionError("Test failed - some products do not have a 'Mega' badge on product image.");
		}

	}

	@Test
	@Description("Verify that the 'Online Only' filter displays only products with the 'Online Only' badge.")
	public void verifyOnlineOnlyFilter() throws InterruptedException {
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);

		productCatalogue.selectFilter("TYLKO ONLINE");
		Assert.assertTrue(productCatalogue.isFilterSelected("TYLKO ONLINE"));
		productCatalogue.clickShowResultsBtn();
		Assert.assertEquals(productCatalogue.getFilterTagText(), "TYLKO ONLINE");

		Thread.sleep(1500);

		List<String> productsWithoutBadge = productCatalogue
				.getProductsMissingBadge(productCatalogue.badgeSelectorOnlyOnline);

		// Checking the display of products without 'Only online' badge
		if (!productsWithoutBadge.isEmpty()) {
			for (String product : productsWithoutBadge) {
				System.out.println("No badge found for the following product: " + product);
			}
			throw new AssertionError("Test failed - some products do not have a 'Only online' badge on product image.");
		}
	}

	@Test
	@Description("Verify that changing number of products per page option works correctly and check default options.")
	public void verifyChangeNumberOfProductsPerPageOption() throws InterruptedException {
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);

		// Checking default number of products per page == 24
		Assert.assertTrue(productCatalogue.selectedPerPageOption.getText().contains("24"));
		Assert.assertEquals(productCatalogue.productList.size(), 24);
		Thread.sleep(1500);
		productCatalogue.changeNumberOfProductsPerPage("96 wyników");
		Assert.assertTrue(productCatalogue.selectedPerPageOption.getText().contains("96"));
		Assert.assertEquals(productCatalogue.productList.size(), 96);
		Thread.sleep(1500);
		productCatalogue.changeNumberOfProductsPerPage("48 wyników");
		Assert.assertTrue(productCatalogue.selectedPerPageOption.getText().contains("48"));
		Assert.assertEquals(productCatalogue.productList.size(), 48);
		Thread.sleep(1500);
		productCatalogue.changeNumberOfProductsPerPage("24 wyniki");
		Assert.assertTrue(productCatalogue.selectedPerPageOption.getText().contains("24"));
		Assert.assertEquals(productCatalogue.productList.size(), 24);
	}

	@Description("Verify that selecting the 'Sort By' option sorts products according to the selected criteria.")
	public void verifySortingOption() {

	}

	@Description("Verify selecting the 'Brand' filter shows only products from the selected brand.")
	public void verifyBrandFilter() {

	}

}
