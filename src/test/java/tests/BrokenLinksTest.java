package test.java.tests;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import jdk.jfr.Description;
import main.java.pageObject.PageObject;
import main.java.pages.ContactPage;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import test.java.basetest.BaseTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrokenLinksTest extends BaseTest{

	HomePage homePage;
	LoginPage loginPage;
	
	@BeforeTest
    public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
    }
	
	@Test(groups = {"smoketests"})
	@Description("Check functionality of company link in the top of the page.")
	public void checkTopBarCompanyLinks() throws MalformedURLException, IOException {
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.companyLinkList) {
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
	@Description("Check functionality of main categories menu links.")
	public void checkMainCategoriesMenuLinks() throws MalformedURLException, IOException {
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.mainCategoryLinkList) {
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

		for (WebElement link : homePage.subCategoryLinkList) {
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
	@Description("Check functionality of footer links.")
	public void checkFooterLinks() throws MalformedURLException, IOException, InterruptedException {
		
		homePage.goToShoppingCart();
		homePage.scrollToBottomOfPage();
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.footerLinkList) {
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
	@Description("Check functionality of profile tab links.")
	public void checkProfileTabLinks() throws MalformedURLException, IOException, InterruptedException {
		
		loginPage.goToLoginPage();
		loginPage.loginByCorrectCredentials("automation224+2@gmail.com", "Tester123");
		loginPage.clickAccountButtonIcon();
		
		SoftAssert a = new SoftAssert();

		for (WebElement link : homePage.profileLinkList) {
			String url = link.getAttribute("href");

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("HEAD");
			conn.connect();
			int respCode = conn.getResponseCode();

			// System.out.println(respCode);

			a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);

		}
		
		a.assertAll();
		
		loginPage.logout();
	}

}