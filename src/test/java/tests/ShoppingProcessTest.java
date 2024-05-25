package test.java.tests;

import jdk.jfr.Description;
import main.java.pages.CartPage;
import main.java.pages.HomePage;
import main.java.pages.ProductCatalogue;
import test.java.basetest.BaseTest;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShoppingProcessTest extends BaseTest {

	HomePage homePage;
	ProductCatalogue productCatalogue;
	CartPage cartPage;
	
	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		cartPage = PageFactory.initElements(driver, CartPage.class);
		homePage.acceptCookiesInCookieBar();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest() {
		homePage.goToProductCataloguePage();
	}
 
	@Test(groups="smoke")
	@Description("Verify that the user can successfully add a product to the shopping cart and remove it using the remove button.")
	public void addAndRemoveProductFromShoppingCart() throws InterruptedException {
		
		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		
		Assert.assertEquals(cartPage.getProductQuantityInCartFromIcon(), "1", 
				"Expected one product in the cart icon.");
		
		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");
		
		cartPage.clickRemoveButtonInCartForAllProducts();
		
		Thread.sleep(1000);
		Assert.assertEquals(cartPage.getTextFromHeaderElement(), "Twój koszyk jest pusty", 
				"Expected header with info about zero products in the cart");
		
		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 0, 
				"Expected zero products in the cart.");
	}
	
	@Test(groups="smoke")
	@Description("Verify that the correct product details are displayed in the shopping cart after adding it.")
	public void verifyProductDetailsInShoppingCart() throws InterruptedException {
		
		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		
		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");

		List<String> cartProductNames = cartPage.getCartProductNames();
		List<String> cartProductPrices = cartPage.getCartProductPrices();
		
		cartPage.cartProductNameElement.click();
		
		Thread.sleep(1500);
		
		WebElement productNameElement = productCatalogue.productPageNameElement;
		String productName = productNameElement.getText();
		String productPrice = productCatalogue.productPagePriceElement.getText();

		boolean checkProductNameInCart = cartProductNames.contains(productName);
		boolean checkProductPriceInCart = cartProductPrices.contains(productPrice);
		
		Assert.assertTrue(checkProductNameInCart, 
				"Expected product name in the cart to be the same as on the product page.");
		
		Assert.assertTrue(checkProductPriceInCart, 
				"Expected product price in the cart to be the same as on the product page.");

		cartPage.clearCart();
	}

	@Test(groups="smoke")
	@Description("Verify that the total price is updated correctly after changing the quantity of products in the shopping cart.")
	public void verifyTotalPriceAfterChangingQuantityInShoppingCart() throws InterruptedException {

		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");
		
		String productPrice = cartPage.getCartProductPrices().get(0);
		String cartValueBefore = cartPage.getShoppingCartValue();

		// Attempt to add 6 pieces of one product to the cart.
		// The final quantity added may be limited by the product's maximum limit.
		cartPage.increaseNumberOfProductInCart(5);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");
		
		String productQuantityText = cartPage.getProductQuantityInCartFromCounter();
		int productQuantityValue = Integer.parseInt(productQuantityText);
		

		Assert.assertEquals(cartPage.getProductQuantityInCartFromCounter(),
				productQuantityText);

		// Checking if the cart total matches the products price
		String cartValueAfter = cartPage.getShoppingCartValue();

		double singleProductPrice = Double.parseDouble(productPrice.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * productQuantityValue, 0.01);
		
		Assert.assertEquals(cartAfter, singleProductPrice  * productQuantityValue, 0.01);

		cartPage.clearCart();

	}


	@Description("Verify that the user can proceed to checkout from the shopping cart.")
	public void proceedToCheckoutFromShoppingCart() {
	}

	@Description("Verify that the user can select a shipping method during the checkout process.")
	public void selectShippingMethodDuringCheckout() {
	}

}
