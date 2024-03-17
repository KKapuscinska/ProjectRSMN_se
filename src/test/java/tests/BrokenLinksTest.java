package test.java.tests;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import jdk.jfr.Description;
import main.java.pages.ContactPage;
import main.java.pages.HomePage;
import test.java.basetest.BaseTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrokenLinksTest extends BaseTest{

	HomePage homePage;
	
	@BeforeTest
    public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
    }
	
	@Test(groups = {"smoketests"})
	@Description("Check functionality of main categories menu links.")
	public void checkMainCategoriesMenuLinks() throws MalformedURLException, IOException {
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.mainCategoryList) {
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
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.subCategoryList) {
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

}