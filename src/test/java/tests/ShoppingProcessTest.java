package test.java.tests;

import jdk.jfr.Description;
import main.java.pages.*;
import org.testng.annotations.DataProvider;
import test.java.basetest.BaseTest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
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
	LoginPage loginPage;
	CheckoutPage checkoutPage;

	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		productCatalogue = PageFactory.initElements(driver, ProductCatalogue.class);
		cartPage = PageFactory.initElements(driver, CartPage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		checkoutPage = PageFactory.initElements(driver, CheckoutPage.class);
		homePage.acceptCookiesInCookieBar();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest() {

		homePage.goToProductCataloguePage();
		productCatalogue.addToCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
	}

	@Test(groups="smoke")
	@Description("Verify that the user can successfully add a product to the shopping cart and remove it using the remove button.")
	public void addAndRemoveProductFromShoppingCart() throws InterruptedException {

		Assert.assertEquals(homePage.getProductQuantityFromIcon(), "1",
				"Expected 1 product in the cart icon.");

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected 1 product in the cart.");

		cartPage.clickRemoveButtonForAllProducts();

		homePage.waitForElementToAppearWebElement(cartPage.emptyCartHeader);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 0,
				"Expected 0 products in the cart.");

		Assert.assertEquals(cartPage.getTextFromHeaderElement(), cartPage.emptyCartHeaderText,
				"Expected header with info about zero products in the cart");
	}

	@Test(groups="smoke")
	@Description("Verify that the correct product details are displayed in the shopping cart after adding it.")
	public void verifyProductDetailsInShoppingCart() throws InterruptedException {

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected 1 product in the cart.");

		List<String> cartProductNames = cartPage.getCartProductNames();
		List<String> cartProductPrices = cartPage.getCartProductPrices();

		cartPage.productNameElement.click();
		homePage.waitForElementToAppearWebElement(productCatalogue.productPageNameElement);

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

		Thread.sleep(1000);
		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected 1 product in the cart.");

		String productPrice = cartPage.getCartProductPrices().get(0);
		String cartValueBefore = cartPage.getCartValue();

		// Attempt to add 6 pieces of one product to the cart.
		// The final quantity added may be limited by the product's maximum limit.
		cartPage.increaseNumberOfProduct(5);
			Thread.sleep(2000);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected one product in the cart.");

		String productQuantityText = cartPage.getProductQuantityFromCounter();
		int productQuantityValue = Integer.parseInt(productQuantityText);


		Assert.assertEquals(cartPage.getProductQuantityFromCounter(),
				productQuantityText);

		// Checking if the cart total matches the products price
		String cartValueAfter = cartPage.getCartValue();

		double singleProductPrice = Double.parseDouble(productPrice.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * productQuantityValue, 0.01);

		Assert.assertEquals(cartAfter, singleProductPrice * productQuantityValue, 0.01);

		cartPage.clearCart();

	}

	@Test(dataProvider = "getPlainUserLoginData")
	@Description("Verify that the user can log in from the popup in cart and proceed to checkout.")
	public void loginAndProceedToCheckoutFromPopup(HashMap<String, String> input) throws InterruptedException {

		cartPage.clickSummaryButton();
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CART_RELATIVE),
				"Expected current URL to be cart page.");

		cartPage.clickSummaryButton();
		homePage.waitForElementToAppear(checkoutPage.deliverySectionBy);

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CHECKOUT_RELATIVE),
				"Expected current URL to be checkout page.");

		checkoutPage.clickGoBackBtn();

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CART_RELATIVE),
				"Expected current URL to be cart page.");

		cartPage.clickRemoveButtonForAllProducts();
		homePage.waitForElementToAppearWebElement(cartPage.emptyCartHeader);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 0,
				"Expected 0 products in the cart.");

		homePage.logout();
	}

	@Test(dataProvider = "getPlainUserLoginData")
	@Description("Verify that the products remain in the cart after logging out and logging back in.")
	public void verifyCartPersistenceAfterLogout(HashMap<String, String> input) throws InterruptedException {

		cartPage.clickSummaryButton();
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CART_RELATIVE),
				"Expected current URL to be cart page.");

		Thread.sleep(1000);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected 1 products in the cart.");

		Assert.assertEquals(homePage.getProductQuantityFromIcon(), "1",
				"Expected 1 product in the cart icon.");

		homePage.logout();
		homePage.goToLoginPage();
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		homePage.goToShoppingCart();

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 1,
				"Expected 1 products in the cart.");

		Assert.assertEquals(homePage.getProductQuantityFromIcon(), "1",
				"Expected 1 product in the cart icon.");

		cartPage.clickRemoveButtonForAllProducts();

		homePage.waitForElementToAppearWebElement(cartPage.emptyCartHeader);

		Assert.assertEquals(cartPage.getNumberOfUniqueProductsInCart(), 0,
				"Expected zero products in the cart.");

		loginPage.logout();
	}
	@Test
	@Description("Verify that the user can proceed to checkout without registration.")
	public void proceedToCheckoutWithoutRegistration() throws InterruptedException {

		cartPage.clickBuyWithoutRegistration();

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CHECKOUT_RELATIVE),
				"Expected current URL to be checkout page.");

		checkoutPage.clickGoBackBtn();
		cartPage.clickRemoveButtonForAllProducts();
	}
	@Test
	@Description("Verify that the order details in the final step match the cart details.")
	public void verifyOrderDetailsInFinalStep() throws InterruptedException {

		List<String> cartProductNames = cartPage.getCartProductNames();
		String cartFinalPrice = cartPage.getCartValue();

		cartPage.clickBuyWithoutRegistration();

		Assert.assertTrue(driver.getCurrentUrl().contains(homePage.URL_CHECKOUT_RELATIVE),
				"Expected current URL to be checkout page.");

		List<String> checkoutProductNames = cartPage.getCartProductNames();
		String checkoutFinalPrice = checkoutPage.finalPrice.getText();

		Assert.assertEquals(checkoutProductNames, cartProductNames,
				"Expected product names in the cart to be the same as on the checkout.");

		Assert.assertEquals(cartFinalPrice, checkoutFinalPrice,
				"Expected product price in the cart to be the same as on the checkout.");

		checkoutPage.clickGoBackBtn();
		cartPage.clickRemoveButtonForAllProducts();
	}

	@Test
	@Description("Verify the number and responsiveness of delivery methods at checkout.")
	public void verifyDeliveryOptionsAtCheckout() throws InterruptedException {

		cartPage.clickBuyWithoutRegistration();

		Assert.assertEquals(checkoutPage.countDeliveryOption(), 4,
		"Expected 4 delivery options");

		//
		//
		//

		checkoutPage.clickGoBackBtn();
		cartPage.clickRemoveButtonForAllProducts();
	}

	@Test
	@Description("Verify validation message appears when no delivery method is selected.")
	public void verifyValidationForNoDeliveryOption() throws InterruptedException {

		cartPage.clickBuyWithoutRegistration();
		checkoutPage.clickOrderButton();

		homePage.waitForElementToAppearWebElement(checkoutPage.sectionTopBarErrorMsg);
		Assert.assertEquals(checkoutPage.sectionTopBarErrorMsg.getText(), checkoutPage.deliveryValidationErrorMsg,
				"Expected specific error validation message.");

		Assert.assertTrue(driver.getCurrentUrl().contains("#delivery"),
				"Expected current URL to be checkout page.");

		checkoutPage.clickGoBackBtn();
		cartPage.clickRemoveButtonForAllProducts();
	}

	@Description("Verify validation message appears when customer details are missing.")
	public void verifyValidationForMissingCustomerDetails() {
	}

	@Description("Verify validation message appears when no payment method is selected.")
	public void verifyValidationForMissingPaymentMethod() {
	}


	@DataProvider
	public Object[][] getPlainUserLoginData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\PlainUser.json");
		return new Object[][]	{{data.get(0)}};
	}
}