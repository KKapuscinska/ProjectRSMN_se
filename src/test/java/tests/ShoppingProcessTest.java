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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Random;

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
		Thread.sleep(1500);
		
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
		Thread.sleep(1500);
		
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

		// Clear the cart
		homePage.goToShoppingCart();
		productCatalogue.clickRemoveButtonInCartForAllProducts();
	}

	@Test(enabled = true)
	@Description("Verify that the user can remove a product from the shopping cart by decreasing its quantity to zero.")
	public void decreaseProductQuantityInShoppingCartToZero() throws InterruptedException {

		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		Thread.sleep(1500);
		
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

	@Test(enabled = true)
	@Description("Verify that the user can increase the quantity of a product in the shopping cart.")
	public void increaseProductQuantityInShoppingCart() throws InterruptedException {

		productCatalogue.addToShoppingCartFirstAvailableProductOnProductCatalogue();
		homePage.goToShoppingCart();
		Thread.sleep(1500);
		
		Assert.assertEquals(productCatalogue.getNumberOfUniqueProductsInCart(), 1, 
				"Expected one product in the cart.");

		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromCounter(), "1", 
				"Expected a specific quantity of the product in the cart.");
		
		//Select random number (without zero) in quantity dropdown
		productCatalogue.openQuantityProductDropdownInCart();
				
		List<WebElement> dropdownElements = productCatalogue.quantityDropdownElements;

		Random rand = new Random();
		int randomIndex = rand.nextInt(dropdownElements.size() - 2) + 2;
		WebElement randomElement = dropdownElements.get(randomIndex);
		String randomElementNumber = randomElement.getText();
		randomElement.click();

		Thread.sleep(1500);

		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromCounter(), randomElementNumber, 
				"Expected quantity of products in cart counter to be: " + randomElementNumber);
		
		Assert.assertEquals(productCatalogue.getProductQuantityInCartFromIcon(), randomElementNumber, 
				"Expected quantity of products in cart icon to be: " + randomElementNumber);
		
		// Clear the cart
		homePage.goToShoppingCart();
		productCatalogue.clickRemoveButtonInCartForAllProducts();
	}

	@Test(groups = { "smoketests" }, enabled = false)
	@Description("Verify that the total price is updated correctly after modifying the quantity of products in the shopping cart.")
	public void verifyTotalPriceInShoppingCart() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));

		// Adding to the cart first available product
		List<WebElement> products = driver.findElements(By.cssSelector(".product-list__col--thirds .nav-user__icon"));
		if (products.size() > 0) {
			WebElement firstProduct = products.get(0);
			firstProduct.click();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("nav-user__quantity")));
		driver.findElement(By.cssSelector("a[title='Koszyk']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-product__name")));

		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(), "1");

		String productPriceBefore = driver.findElement(By.cssSelector(".cart-product__price")).getText();

		String cartValueBefore = driver.findElement(By.cssSelector(".price-details__value-total")).getText();

		driver.findElement(By.cssSelector(".cart-product__quantity")).click();

		Thread.sleep(1500);
		// Selecting the highest available number of articles from a dropdown list
		List<WebElement> dropdownOptions = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='rowgroup'] div")));

		WebElement lastVisibleElement = dropdownOptions.get(dropdownOptions.size() - 1);

		String lastVisibleElementText = lastVisibleElement.getText();

		int lastVisibleElementValue = Integer.parseInt(lastVisibleElementText);

		lastVisibleElement.click();

		Thread.sleep(1500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(),
				lastVisibleElementText);

		// Checking if the cart total matches the products price

		String productPriceAfter = driver.findElement(By.cssSelector(".cart-product__price")).getText();
		String cartValueAfter = driver.findElement(By.cssSelector(".price-details__value-total")).getText();

		double priceBefore = Double.parseDouble(productPriceBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double priceAfter = Double.parseDouble(productPriceAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(priceAfter, priceBefore * lastVisibleElementValue, 0.01);

		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * lastVisibleElementValue, 0.01);

		// Clear the cart
		homePage.goToShoppingCart();
		productCatalogue.clickRemoveButtonInCartForAllProducts();

	}

	@Test(enabled = false)
	@Description("Add maximum quantity of one product to shopping cart and verify popup message.")
	public void addMaxQuantityToShoppingCart() throws InterruptedException {

		

		boolean maxProductsReached = false;

		while (!maxProductsReached) {

			// Adding to the cart first available product
			List<WebElement> products = productCatalogue.getAvailableToSellProductList();
			if (products.size() > 0) {
				WebElement firstProduct = products.get(0);
				firstProduct.click();

				try {

					// Check if popup with maximum quantity message appears
					WebElement maxProductsPopup = driver.findElement(By.cssSelector(".Toastify__toast-body"));
					String popupText = maxProductsPopup.getText();
					if (popupText.equals("Przepraszamy, nie mamy więcej sztuk tego produktu")) {
						System.out.println("Test passed: Popup message matches expected text.");
						maxProductsReached = true; // Set flag to true when popup is visible
					} else {
						System.out.println("Test failed: Popup message does not match expected text.");
						break;
					}
				} catch (NoSuchElementException e) {
					Thread.sleep(300);
					
					// Continue adding product if popup is not visible
				}
			}
		}

		
		
    
		Thread.sleep(1500);

		// Clear the cart
		homePage.goToShoppingCart();
		productCatalogue.clickRemoveButtonInCartForAllProducts();
	}


	@Description("Verify that the user can proceed to checkout from the shopping cart.")
	public void proceedToCheckoutFromShoppingCart() {
	}

	@Description("Verify that the user can select a shipping method during the checkout process.")
	public void selectShippingMethodDuringCheckout() {
	}

}
