package test;

import jdk.jfr.Description;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
		List<WebElement> productList = driver.findElements(By.cssSelector(".product-list__col--thirds"));

		for (WebElement product : productList) {
			List<WebElement> cartIconList = product.findElements(By.cssSelector(".nav-user__icon"));

			if (cartIconList.size() > 0) {
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

		Assert.assertEquals(productName, cartProductName);

		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}

	@Test(groups = {"smoketests"})
	@Description("Verify that the correct product details are displayed in the shopping cart after adding it.")
	public void verifyProductDetailsInShoppingCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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
	public void increaseProductQuantityInShoppingCart() {
		driver.get("https://www.rossmann.pl/szukaj");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		WebElement element = driver.findElement(By.cssSelector("div[role='rowgroup'] div:nth-child(3)"));
		element.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(), "2");

		Assert.assertEquals(driver.findElement(By.cssSelector(".nav-user__quantity")).getText(), "2");

		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");
	}

	@Test(groups = {"smoketests"})
	@Description("Verify that the total price is updated correctly after modifying the quantity of products in the shopping cart.")
	public void verifyTotalPriceInShoppingCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		WebElement element = driver.findElement(By.cssSelector("div[role='rowgroup'] div:nth-child(3)"));
		element.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".sri-select__items-container")));

		Assert.assertEquals(driver.findElement(By.cssSelector(".sri-select__selected")).getText(), "2");

		String productPriceAfter = driver.findElement(By.cssSelector(".cart-product__price")).getText();
		String cartValueAfter = driver.findElement(By.cssSelector(".price-details__value-total")).getText();

		double priceBefore = Double.parseDouble(productPriceBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double priceAfter = Double.parseDouble(productPriceAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(priceAfter, priceBefore * 2, 0.01);

		double cartBefore = Double.parseDouble(cartValueBefore.replaceAll("[^0-9.,]+", "").replace(",", "."));
		double cartAfter = Double.parseDouble(cartValueAfter.replaceAll("[^0-9.,]+", "").replace(",", "."));

		Assert.assertEquals(cartAfter, cartBefore * 2, 0.01);

		driver.findElement(By.cssSelector(".btn-del")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".h3")).getText(), "Twój koszyk jest pusty");

	}

	@Description("Add maximum quantiti of one product to  shopping cart.")
	public void addMaxQuantityToShoppingCart() {
			
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
