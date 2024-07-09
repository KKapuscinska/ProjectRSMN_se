package test.java.tests;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import static org.openqa.selenium.support.locators.RelativeLocator.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import jdk.jfr.Description;
import main.java.pages.ContactPage;
import main.java.pages.HomePage;
import test.java.basetest.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.listeners.Retry;

public class ContactPageTest extends BaseTest{

	ContactPage contactPage;
	HomePage homePage;
	
	@BeforeClass
    public void setup() throws IOException {
		homePage = PageFactory.initElements(driver, HomePage.class);
		contactPage = PageFactory.initElements(driver, ContactPage.class);
		homePage.goToContactPage();
		homePage.acceptCookiesInCookieBar();
    }
	
	@Test(dataProvider = "invalidNameData", retryAnalyzer= Retry.class)
	@Description("Verify name fields validation.")
	public void contactFormValidationName(HashMap<String, String> formData) {
			
		contactPage.fillName(formData.get("name"));
		contactPage.fillLastName(formData.get("lastName"));
		contactPage.sendContactForm();

		// Checking the validation message
		Assert.assertEquals(driver
		        .findElement(with(contactPage.valiadationMessageBy)
		        .below(driver.findElement(contactPage.nameInputBy)))
		        .getText(), formData.get("nameInvalidFeedback"),
		        "Expected specific validation message for the email field.");
		    
		Assert.assertEquals(driver
		        .findElement(with(contactPage.valiadationMessageBy)
		        .below(driver.findElement(contactPage.lastNameInputBy)))
		        .getText(), formData.get("nameInvalidFeedback"),
		        "Expected specific validation message for the email field.");
	}
		
	@Test(dataProvider = "invalidEmailData", retryAnalyzer= Retry.class)
	@Description("Verify email field validation.")
	public void contactFormValidationEmail(HashMap<String, String> formData) {
		
		contactPage.fillEmail(formData.get("email"));
	    contactPage.sendContactForm();

	    // Checking the validation message
	    Assert.assertEquals(driver
	            .findElement(with(contactPage.valiadationMessageBy)
	            .below(driver.findElement(contactPage.emailInputBy)))
	            .getText(), formData.get("emailInvalidFeedback"),
	            "Expected specific validation message for the email field.");
	}
	
	@Test(dataProvider = "invalidPhoneData")
	@Description("Verify Phone Number field validation.")
	public void contactFormValidationPhoneNumber(HashMap<String, String> formData) {

	    contactPage.fillPhone(formData.get("phoneNumber"));
	    contactPage.sendContactForm();

	    // Checking the validation message
	    Assert.assertEquals(driver
	            .findElement(with(contactPage.valiadationMessageBy)
	            .below(driver.findElement(contactPage.phoneInputBy)))
	            .getText(), formData.get("phoneNumberInvalidFeedback"),
	            "Expected specific validation message for the 'phone number' field.");
	}

	@Test(dataProvider = "invalidMessageData")
	@Description("Verify Message field validation.")
	public void contactFormValidationMessage(HashMap<String, String> formData) {
		
	    // Validating the maximum length of the message field
	    Assert.assertEquals(contactPage.messageInput.getAttribute("maxlength"), "5000",
	            "Expected maximum character limit for the message field.");

	    contactPage.fillMessage(formData.get("message"));

	    contactPage.sendContactForm();

	    // Checking the validation message
	    Assert.assertEquals(driver
	            .findElement(with(contactPage.valiadationMessageBy)
	            .below(driver.findElement(contactPage.messageInputBy)))
	            .getText(), formData.get("messageInvalidFeedback"),
	            "Expected specific validation message for the 'message' field.");
	}

	@Test(dataProvider = "validData")
	@Description("Verify that the contact form displays only the CAPTCHA validation message when all fields are filled correctly.")
	public void submitContactForm(HashMap<String, String> formData) {
			
		contactPage.fillName(formData.get("name"));
		contactPage.fillLastName(formData.get("lastName"));
		contactPage.fillEmail(formData.get("email"));
		contactPage.fillPhone(formData.get("phoneNumber"));
		contactPage.fillMessage(formData.get("message"));
		contactPage.sendContactForm();

		// Checking the validation message - Captcha
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
		        .below(driver.findElement(contactPage.recaptchaBy)))
		        .getText(), formData.get("reCaptchaInvalidFeedback"),
				"Expected specific validation message for 'recaptcha' field.");
		    
		// Checking if the correct completion of the form only activates the captcha validation message
		Assert.assertEquals(driver.findElements(contactPage.valiadationMessageBy).size(), 1,
				"Expected only one validation message to appear");	
		}		

	
	@DataProvider(name = "invalidNameData")
	public Object[][] getInvalidNameData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\contactform\\InvalidNameData.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider(name = "invalidEmailData")
	public Object[][] getInvalidEmailData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\contactform\\InvalidEmailData.json");
		return new Object[][]	{{data.get(0)}, {data.get(1)}, {data.get(2)}};
	}

	@DataProvider(name = "invalidPhoneData")
	public Object[][] getInvalidPhoneData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\contactform\\InvalidPhoneData.json");
		return new Object[][]	{{data.get(0)}};
	}
	
	@DataProvider(name = "invalidMessageData")
	public Object[][] getInvalidMessageData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\contactform\\InvalidMessageData.json");
		return new Object[][]	{{data.get(0)}, {data.get(1)}};
	}
	
	@DataProvider(name = "validData")
	public Object[][] getValidData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\main\\resources\\testdata\\contactform\\ValidData.json");
		return new Object[][]	{{data.get(0)}, {data.get(1)}};
	}
}
