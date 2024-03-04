package test;

import jdk.jfr.Description;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Random;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ShoppingProcessTest {

	WebDriver driver;

	@BeforeTest(groups = {"smoketests"})
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		
	}

	@BeforeMethod(groups = {"smoketests"})
	public void beforeTest() {
		driver.get("https://www.rossmann.pl/szukaj");

	}

	@Test(priority = 0, groups = {"smoketests"})
	@Description("Verify that the user can successfully add a product to the shopping cart and remove it using the remove button.")
	public void addAndRemoveProductFromShoppingCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		String productName = null;
		
		// Adding to the cart first available product 
		List<WebElement> productList = driver.findElements(By.cssSelector(".product-list__col--thirds"));
		for (WebElement product : productList) {
			List<WebElement> cartIconList = product.findElements(By.cssSelector(".nav-user__icon"));

			if (cartIconList.size() > 0) {
				// Grabbing name of first available product 
				WebElement productNameElement = product.findElement(By.cssSelector(".tile-product__name strong"));
				productName = productNameElement.getText();
				WebElement cartIconElement = product.findElement(By.cssSelector(".nav-user__icon"));
				cartIconElement.click();
				break;
			}
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-mini_basket")));

		driver.findElement(By.cssSelector(".nav-user-product__btn")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-product__name strong")));

		WebElement cartProductNameElement = driver.findElement(By.cssSelector(".cart-product__name strong"));

		String cartProductName = cartProductNameElement.getText();

		// Verifying if the product added to the cart matches the product in the cart
		Assert.assertEquals(productName, cartProductName);

		// Click to remove btn
		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}

	@Test(groups = {"smoketests"})
	@Description("Verify that the correct product details are displayed in the shopping cart after adding it.")
	public void verifyProductDetailsInShoppingCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// Adding to the cart first available product 
		List<WebElement> products = driver.findElements(By.cssSelector(".product-list__col--thirds .nav-user__icon"));
		if (products.size() > 0) {
			WebElement firstProduct = products.get(0);
			firstProduct.click();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("nav-user__quantity")));
		driver.findElement(By.cssSelector("a[title='Koszyk']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-product__name")));

		String cartProductName = driver.findElement(By.cssSelector(".cart-product__name strong")).getText();

		String cartProductPrice = driver.findElement(By.cssSelector(".cart-product__price")).getText();

		driver.findElement(By.cssSelector(".cart-product__name strong")).click();

		WebElement productNameElement = driver.findElement(By.cssSelector("div.product-info__name > h1.h1"));
		String productName = productNameElement.getText().split("\n")[0].trim();

		String productPrice = driver.findElement(By.cssSelector(".product-price .h2")).getText();

		Assert.assertEquals(cartProductName, productName);

		Assert.assertEquals(cartProductPrice, productPrice);

		// Clear the cart
		driver.get("https://www.rossmann.pl/zamowienie/koszyk");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-product__name strong")));
		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");

	}

	@Test
	@Description("Verify that the user can remove a product from the shopping cart by decreasing its quantity to zero.")
	public void decreaseProductQuantityInShoppingCartToZero() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		driver.findElement(By.cssSelector(".cart-product__quantity")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		WebElement element = driver.findElement(By.xpath("//div[@class='sri-select__item ' and text()='0']"));
		element.click();

		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}

	@Test
	@Description("Verify that the user can increase the quantity of a product in the shopping cart.")
	public void increaseProductQuantityInShoppingCart() throws InterruptedException {
		driver.get("https://www.rossmann.pl/szukaj");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		driver.findElement(By.cssSelector(".cart-product__quantity")).click();

		// Selecting random available number of articles from a dropdown list
		List<WebElement> dropdownOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='rowgroup'] div")));

		Random rand = new Random();
		int randomIndex = rand.nextInt(dropdownOptions.size() - 2) + 2;

		WebElement randomElement = dropdownOptions.get(randomIndex);

		String randomElementText = randomElement.getText();

		randomElement.click();
		
		Thread.sleep(1500);

		// Checking if the number of products has been selected correctly
		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(), randomElementText);

		// Checking if the number of products has been change above cart icon
		Assert.assertEquals(driver.findElement(By.cssSelector(".nav-user__quantity")).getText(), randomElementText);

		// Clean the shopping cart
		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}

	@Test(groups = {"smoketests"})
	@Description("Verify that the total price is updated correctly after modifying the quantity of products in the shopping cart.")
	public void verifyTotalPriceInShoppingCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		// Selecting the highest available number of articles from a dropdown list
		List<WebElement> dropdownOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='rowgroup'] div")));

		WebElement lastVisibleElement = dropdownOptions.get(dropdownOptions.size() - 1);
		
		String lastVisibleElementText = lastVisibleElement.getText();
		
		int lastVisibleElementValue = Integer.parseInt(lastVisibleElementText);

		lastVisibleElement.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(), lastVisibleElementText);

		// Checking if the cart total matches the products price
		
		String productPriceAfter = driver.findElement(By.cssSelector(".cart-product__price")).getText();
		String cartValueAfter = driver.findElement(By.cssSelector(".price-details__value-total")).getText();

		double priceBefore = Double.parseDouble(productPriceBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double priceAfter = Double.parseDouble(productPriceAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(priceAfter, priceBefore * lastVisibleElementValue, 0.01);

		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * lastVisibleElementValue, 0.01);

		// Clean the shopping cart
		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");

	}

	@Test
	@Description("Add maximum quantity of one product to shopping cart and verify popup message.")
	public void addMaxQuantityToShoppingCart() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		boolean maxProductsReached = false;

		while (!maxProductsReached) {
			
			// Adding to the cart first available product 
		    List<WebElement> products = driver.findElements(By.cssSelector(".product-list__col--thirds .nav-user__icon"));
		    if (products.size() > 0) {
		        WebElement firstProduct = products.get(0);
		        firstProduct.click();
		        
		        try {
		        	
		        	// Check if popup with maximum quantity message appears
	                WebElement maxProductsPopup = driver.findElement(By.cssSelector("div.Toastify__toast-body"));
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
		
		// Clear the cart
		driver.get("https://www.rossmann.pl/zamowienie/koszyk");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-product__name strong")));
		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}
	
	@Description("Verify that the user can proceed to checkout from the shopping cart.")
	public void proceedToCheckoutFromShoppingCart() {
	}

	@Description("Verify that the user can select a shipping method during the checkout process.")
	public void selectShippingMethodDuringCheckout() {
	}

	@AfterTest(groups = {"smoketests"})
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
	}
}
