package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

import jdk.jfr.Description;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BrokenLinksTest {

	WebDriver driver;
	
	@BeforeTest(groups = {"smoketests"})
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
        driver.get("https://www.rossmann.pl");
    }
	
	@Test(groups = {"smoketests"})
	@Description("Check functionality of main categories menu links.")
	public void checkMainCategoriesMenuLinks() throws MalformedURLException, IOException {

		List<WebElement> links = driver.findElements(By.cssSelector(".nav__link"));
		SoftAssert a = new SoftAssert();

		for (WebElement link : links) {
			String url = link.getAttribute("href");

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("HEAD");
			conn.connect();
			int respCode = conn.getResponseCode();

			// System.out.println(respCode);

			a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);

		}
		a.assertAll();

	}

	@Test(groups = {"smoketests"})
	@Description("Check functionality of subcategories menu links.")
	public void checkSubcategoriesMenuLinks() throws MalformedURLException, IOException {

		List<WebElement> links = driver.findElements(By.cssSelector(".sub-nav__link"));
		SoftAssert a = new SoftAssert();

		for (WebElement link : links) {
			String url = link.getAttribute("href");

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("HEAD");
			conn.connect();
			int respCode = conn.getResponseCode();

			// System.out.println(respCode);

			a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);

		}
		a.assertAll();
	}


	@AfterTest(groups = {"smoketests"})
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}