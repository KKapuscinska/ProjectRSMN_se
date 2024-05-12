package test.java.tests;


import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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
	
	@Test
	@Description("User can successfully log in and log out via a popup login window.")
	public void successfulLoginViaPopup() throws InterruptedException, IOException {

		homePage.goToHomePage();
		homePage.hoverOverUserAccountLinkAndClickLogin();
		loginPage.loginByCorrectCredentials("automation224@gmail.com", "Tester123");
		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/profil/ustawienia-konta"),
				"Expected current URL to be profile page.");

		homePage.logout();
		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");
	}

	@Test(groups = { "smoketests" })
	@Description("User can successfully log in and log out via the login page.")
	public void successfulLoginViaPage() throws InterruptedException {

		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");

		loginPage.loginByCorrectCredentials("automation224@gmail.com", "Tester123");

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

		homePage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains("/logowanie"),
				"Expected current URL to be login page.");

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "password", 
				"Expected password input field to be of type 'password'");

		loginPage.clickShowPasswordIcon();

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "text",
				"Expected password input field to be of type 'text'");

	}

	@Test(enabled = true)
	@Description("User cannot log in without filling in login fields.")
	public void failureLoginEmptyFields() throws InterruptedException {

		homePage.goToLoginPage();
		loginPage.logInByIncorrectCredentials("", "");

		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.loginInputBy)))
				.getText(),"Proszę wpisać poprawny adres e-mail lub nazwę użytkownika.");

		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.passwordInputBy)))
				.getText(), "Błąd w obecnym haśle.");
	}

	@Test(enabled = true)
	@Description("User cannot log in with an incorrect password.")
	public void failureLoginIncorrectPassword() throws InterruptedException {

		homePage.goToLoginPage();

		loginPage.logInByIncorrectCredentials("cytest123", "randomText");

		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.loginInputBy)))
				.getText(),"Niepoprawne dane logowania.");

		loginPage.clearLoginFields();
	}

	@Test(enabled = true)
	@Description("User cannot log in with a too short/long login.")
	public void failureLoginInvalidLength() throws InterruptedException {

		homePage.goToLoginPage();
		loginPage.logInByIncorrectCredentials("r", "randomText");

		// Checking min login length
		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.loginInputBy)))
				.getText(),"Nazwa użytkownika powinna składać się z co najmniej 4 znaków.");

		loginPage.logInByIncorrectCredentials(
				"Loremipsumdolorsitamet.consectetueradipiscingelit.Aeneancommodoligulaegetdolor.Aeneanmassa.Cumsociis","");

		Thread.sleep(1500);

		// Checking max login length
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback"))
				.below(driver.findElement(By.id("login-user"))))
				.getText(), "Nazwa użytkownika powinna składać się z maksymalnie 100 znaków.");

		loginPage.clearLoginFields();
	}

}
