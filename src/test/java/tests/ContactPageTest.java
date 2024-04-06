package test.java.tests;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

import java.io.IOException;
import jdk.jfr.Description;
import main.java.pages.ContactPage;
import main.java.pages.HomePage;
import test.java.basetest.BaseTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContactPageTest extends BaseTest{

	ContactPage contactPage;
	HomePage homePage;
	
	@BeforeTest
    public void setup() throws IOException {
		contactPage = PageFactory.initElements(driver, ContactPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.goToContactPage();
		homePage.acceptCookiesInCookieBar();
    }
	
	@Test
	@Description("Verify contact form field validation for empty fields, invalid phone number format or letters, invalid email address format, and invalid message field length.")
	public void contactFormValidation() {

		contactPage.sendContactForm();

		// Checking the validation message - blank form
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.emailInputBy)))
				.getText(), "Proszę podaj adres e-mail.",
				"Expected specific validation message for the 'email' field.");
		
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.messageInputBy)))
				.getText(), "Proszę wpisać treść wiadomości",
				"Expected specific validation message for the 'message' field.");

		// Checking the validation message - Captcha
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.recaptchaBy)))
				.getText(), "Proszę wypełnić captchę",
				"Expected specific validation message for 'recaptcha' field.");

		contactPage.fillPhone("a$");

		// Checking validation of entering letter/special char. in phone number field
		Assert.assertEquals(contactPage.phoneInput.getText(), "",
				"Expected phone input field to be empty");

		contactPage.fillPhone("6");
		contactPage.fillEmail("a");

		// Checking max message length
		Assert.assertEquals(contactPage.messageInput.getAttribute("maxlength"), "5000",
				"Expected maximum character limit for the message field.");

		contactPage.fillMessage("Kliku kliku kliku ");
		contactPage.sendContactForm();

		// Checking the validation message - too short value in Phone number field
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.phoneInputBy)))
				.getText(), "Wpisz poprawny numer telefonu",
				"Expected specific validation message for the 'phone number' field.");

		// Checking the validation message - email without @
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.emailInputBy)))
				.getText(), "Podany adres e-mail nie jest poprawny.",
				"Expected specific validation message for the email field.");

		// Checking the validation message - too short value in Message field
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.messageInputBy)))
				.getText(), "Wiadomość jest za krótka",
				"Expected specific validation message for the 'message' field.");
	}

	@Test
	@Description("Verify that the contact form displays only the CAPTCHA validation message when all fields are filled correctly.")
	public void submitContactForm() {
		
		contactPage.fillName("Krzysztof");
		contactPage.fillLastName("Jarzyna");
		contactPage.fillEmail("cytest@gmail.pl");
		contactPage.fillPhone("666666666");
		contactPage.fillMessage("Kliku kliku kliku kliku kliku");
		contactPage.sendContactForm();

		// Checking if the correct completion of the form only activates the captcha validation message
		Assert.assertEquals(driver.findElements(contactPage.valiadationMessageBy).size(), 1,
				"Expected only one validation message to appear");

		
	}

}
