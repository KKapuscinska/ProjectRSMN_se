package test.java.tests;


import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import test.java.basetest.BaseTest;

public class LoginTest extends BaseTest {

	HomePage homePage;
	LoginPage loginPage;
	
	@BeforeTest
    public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
    }
	
	@Test(dataProvider = "loginValidData")
	@Description("User can successfully log in and log out via a popup login window.")
	public void successfulLoginViaPopup(HashMap<String, String> input) throws InterruptedException, IOException {

		homePage.goToHomePage();
		homePage.hoverOverUserAccountLinkAndClickLogin();
		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/profil/ustawienia-konta"),
				"Expected current URL to be profile page.");

		homePage.logout();
		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");
	}

	@Test(dataProvider = "loginValidData", groups = { "smoketests" })
	@Description("User can successfully log in and log out via the login page.")
	public void successfulLoginViaPage(HashMap<String, String> input) throws InterruptedException {

		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");

		loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));

		Assert.assertTrue(driver.getCurrentUrl().contains("/profil/ustawienia-konta"),
				"Expected current URL to be profile page.");

		homePage.logout();
		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");
	}

	@Test
	@Description("Verify password visibility functionality.")
	public void verifyPasswordVisibilityFunctionality() throws InterruptedException {

		homePage.goToLoginPage();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "password", 
				"Expected password input field to be of type 'password'");

		loginPage.clickShowPasswordIcon();

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "text",
				"Expected password input field to be of type 'text'");

	}
	
	@Test(dataProvider = "loginInvalidData")
	@Description("User cannot log in with an incorrect creddentials.")
	public void failureLogin(HashMap<String, String> input) throws InterruptedException {

		homePage.goToLoginPage();

		loginPage.logInByIncorrectCredentials(input.get("username"), input.get("password"));

		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.loginInputBy)))
				.getText(), input.get("invalidFeedback"));

		loginPage.clearLoginFields();
	}

	
	@DataProvider(name = "loginValidData")
	public Object[][] getDataValid() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\data\\LoginValidData.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	
	@DataProvider(name = "loginInvalidData")
	public Object[][] getDataInvalid() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\data\\LoginInvalidData.json");
		return new Object[][]	{{data.get(0)}, {data.get(1)}, {data.get(2)}, {data.get(3)}};
	}
}
