package test.java.tests;


import static org.openqa.selenium.support.locators.RelativeLocator.with;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import test.java.listeners.Retry;

public class LoginTest extends BaseTest {
	LoginPage loginPage;
	
	@BeforeClass(alwaysRun = true)
    public void setup() throws IOException {
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.acceptCookiesInCookieBar();
    }
	
	@Test(dataProvider = "validData", groups="smoke")
	@Description("User can successfully log in and log out via a popup login window.")
	public void successfulLoginViaPopup(HashMap<String, String> formData) throws InterruptedException, IOException {

		loginPage.goToHomePage();
		loginPage.hoverOverUserAccountLinkAndClickLogin();
		loginPage.loginByCorrectCredentials(formData.get("username"), formData.get("password"));
		loginPage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_PROFILE_RELATIVE),
				"Expected current URL to be profile page.");

		loginPage.logout();
		loginPage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_LOGIN_PAGE_RELATIVE),
				"Expected current URL to be login page.");
	}

	@Test(dataProvider = "validData", groups="smoke")
	@Description("User can successfully log in and log out via the login page.")
	public void successfulLoginViaPage(HashMap<String, String> formData) throws InterruptedException {

		loginPage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_LOGIN_PAGE_RELATIVE),
				"Expected current URL to be login page.");

		loginPage.loginByCorrectCredentials(formData.get("username"), formData.get("password"));

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_PROFILE_RELATIVE),
				"Expected current URL to be profile page.");

		loginPage.logout();
		loginPage.clickAccountButtonIcon();

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_LOGIN_PAGE_RELATIVE),
				"Expected current URL to be login page.");
	}

	@Test
	@Description("Verify password visibility functionality.")
	public void verifyPasswordVisibilityFunctionality() throws InterruptedException {

		loginPage.goToLoginPage();

		Assert.assertTrue(driver.getCurrentUrl().contains(loginPage.URL_LOGIN_PAGE_RELATIVE),
				"Expected current URL to be login page.");

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "password", 
				"Expected password input field to be of type 'password'");

		loginPage.clickShowPasswordIcon();

		Assert.assertEquals(driver.findElement(loginPage.passwordInputBy).getAttribute("type"), "text",
				"Expected password input field to be of type 'text'");

	}
	
	@Test(dataProvider = "invalidData", retryAnalyzer=Retry.class)
	@Description("User cannot log in with an incorrect credentials.")
	public void failureLogin(HashMap<String, String> formData) {

		loginPage.goToLoginPage();
		loginPage.logInByIncorrectCredentials(formData.get("username"), formData.get("password"));

		Assert.assertEquals(driver
				.findElement(with(loginPage.invaldFeedbackBy)
				.below(driver.findElement(loginPage.loginInputBy)))
				.getText(), formData.get("invalidFeedback"));

		loginPage.clearLoginFields();
	}

	
	@DataProvider(name = "validData")
	public Object[][] getValidData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\ValidData.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	
	@DataProvider(name = "invalidData")
	public Object[][] getInvalidData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\login\\InvalidData.json");
		return new Object[][]	{{data.get(0)}, {data.get(1)}, {data.get(2)}, {data.get(3)}};
	}
}
