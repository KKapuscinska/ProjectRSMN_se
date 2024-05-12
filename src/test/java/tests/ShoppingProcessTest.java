package test.java.tests;

import jdk.jfr.Description;

import main.java.pages.HomePage;
import main.java.pages.ProductCatalogue;
import test.java.basetest.BaseTest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ShoppingProcessTest extends BaseTest {

	HomePage homePage;
	ProductCatalogue productCatalogue;
	
	@BeforeTest(groups = { "smoketests" })
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		homePage.acceptCookiesInCookieBar();
	}

	@BeforeMethod(groups = { "smoketests" })
	public void beforeTest() {
		homePage.goToProductCataloguePage();
	}

	@Test(groups = { "smoketests" }, enabled = true)
	@Description("Verify that the user can successfully add a product to the shopping cart and remove it using the remove button.")
	public void addAndRemoveProductFromShoppingCart() throws InterruptedException {
		
		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		
		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromIcon(), "1", 
				"Expected one product in the cart icon.");
		
		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");
		
		productCatalogue.clickRemoveButtonInCartForAllProducts();
		
		Assert.assertEquals(productCatalogue.getTextFromHeaderElement(), "Twój koszyk jest pusty", 
				"Expected header with info about zero products in the cart");
	}
	
	@Test(groups = { "smoketests" }, enabled = true)
	@Description("Verify that the correct product details are displayed in the shopping cart after adding it.")
	public void verifyProductDetailsInShoppingCart() throws InterruptedException {
		
		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		
		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");

		List<String> cartProductNames = productCatalogue.getCartProductNames();
		List<String> cartProductPrices = productCatalogue.getCartProductPrices();
		
		productCatalogue.cartProductNameElement.click();
		
		Thread.sleep(1500);
		
		WebElement productNameElement = productCatalogue.productPageNameElement;
		String productName = productNameElement.getText().split("\n")[0].trim();
		String productPrice = productCatalogue.productPagePriceElement.getText();

		boolean checkProductNameInCart = cartProductNames.contains(productName);
		boolean checkProductPriceInCart = cartProductPrices.contains(productPrice);
		
		Assert.assertTrue(checkProductNameInCart, 
				"Expected product name in the cart to be the same as on the product page.");
		
		Assert.assertTrue(checkProductPriceInCart, 
				"Expected product price in the cart to be the same as on the product page.");

		productCatalogue.clearCart();
	}

	@Test(enabled = true)
	@Description("Verify that the user can remove a product from the shopping cart by decreasing its quantity to zero.")
	public void decreaseProductQuantityInShoppingCartToZero() throws InterruptedException {

		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		
		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");

		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromCounter(), "1", 
				"Expected a specific quantity of the product in the cart.");

		//Select zero in quantity dropdown
		productCatalogue.openQuantityProductDropdownInCart();

		List<WebElement> dropdownElements = productCatalogue.quantityDropdownElements;
	    for (WebElement item : dropdownElements) {
	        if (item.getText().equals("0")) {
	            item.click();
	            break;
	        }
	    }
	    
		//Verify empty cart
	    Assert.assertEquals(productCatalogue.getTextFromHeaderElement(), "Twój koszyk jest pusty", 
				"Expected header with info about zero products in the cart");
		
		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 0, 
				"Expected zero product in the cart.");
	}

	@Test(groups = { "smoketests" }, enabled = true)
	@Description("Verify that the total price is updated correctly after changing the quantity of products in the shopping cart.")
	public void verifyTotalPriceAfterChangingQuantityInShoppingCart() throws InterruptedException {

		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();

		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");
		
		String productPriceBefore = productCatalogue.getCartProductPrices().get(0);
		String cartValueBefore = productCatalogue.getShoppingCartValue();

		// Selecting the highest available number of articles from a dropdown list
		productCatalogue.openQuantityProductDropdownInCart();

		List<WebElement> dropdownOptions = productCatalogue.quantityDropdownElements;
		WebElement maxQuantity = dropdownOptions.get(dropdownOptions.size() - 1);

		String maxQuantityText = maxQuantity.getText();
		int maxQuantityValue = Integer.parseInt(maxQuantityText);

		maxQuantity.click();
		homePage.waitForElementToDisappear(productCatalogue.cartQuantityDropdownBy);

		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromCounter(),
				maxQuantityText);

		// Checking if the cart total matches the products price
		String productPriceAfter = productCatalogue.getCartProductPrices().get(0);
		String cartValueAfter = productCatalogue.getShoppingCartValue();

		double priceBefore = Double.parseDouble(productPriceBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double priceAfter = Double.parseDouble(productPriceAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(priceAfter, priceBefore * maxQuantityValue, 0.01);

		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * maxQuantityValue, 0.01);

		productCatalogue.clearCart();

	}


	@Description("Verify that the user can proceed to checkout from the shopping cart.")
	public void proceedToCheckoutFromShoppingCart() {
	}

	@Description("Verify that the user can select a shipping method during the checkout process.")
	public void selectShippingMethodDuringCheckout() {
	}

}
