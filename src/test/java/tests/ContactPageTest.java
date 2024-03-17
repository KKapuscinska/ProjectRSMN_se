package test.java.tests;


import org.testng.Assert;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

import java.io.IOException;
import jdk.jfr.Description;
import main.java.pages.ContactPage;
import main.java.pages.HomePage;
import test.java.testComponents.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContactPageTest extends BaseTest{


	@BeforeTest
    public void setup() throws IOException {
		HomePage homePage = new HomePage(driver);
		homePage.goToContactPage();
		homePage.acceptCookiesInCookieBar();
    }
	
	@Test
	@Description("Verify contact form field validation for empty fields, invalid phone number format or letters, invalid email address format, and invalid message field length.")
	public void contactFormValidation() {
		ContactPage contactPage = new ContactPage(driver);
		
		contactPage.sendContactForm();

		// Checking the validation message - blank form
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.emailInputBy)))
				.getText(), "Proszę podaj adres e-mail.");
		
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.messageInputBy)))
				.getText(), "Proszę wpisać treść wiadomości");

		// Checking the validation message - Captcha
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.recaptchaBy)))
				.getText(), "Proszę wypełnić captchę");

		contactPage.fillPhone("a$");

		// Checking validation of entering letter/special char. in phone number field
		Assert.assertEquals(contactPage.phoneInput.getText(), "");

		contactPage.fillPhone("6");
		contactPage.fillEmail("a");

		// Checking max message length
		Assert.assertEquals(contactPage.messageInput.getAttribute("maxlength"), "5000");

		contactPage.fillMessage("Kliku kliku kliku ");
		contactPage.sendContactForm();

		// Checking the validation message - too short value in Phone number field
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.phoneInputBy)))
				.getText(), "Wpisz poprawny numer telefonu");

		// Checking the validation message - email without @
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.emailInputBy)))
				.getText(), "Podany adres e-mail nie jest poprawny.");

		// Checking the validation message - too short value in Message field
		Assert.assertEquals(driver
				.findElement(with(contactPage.valiadationMessageBy)
				.below(driver.findElement(contactPage.messageInputBy)))
				.getText(), "Wiadomość jest za krótka");
	}

	@Test
	@Description("Verify that the contact form displays only the CAPTCHA validation message when all fields are filled correctly.")
	public void submitContactForm() {
		ContactPage contactPage = new ContactPage(driver);
		
		contactPage.fillName("Krzysztof");
		contactPage.fillLastName("Jarzyna");
		contactPage.fillEmail("cytest@gmail.pl");
		contactPage.fillPhone("666666666");
		contactPage.fillMessage("Kliku kliku kliku kliku kliku");
		contactPage.sendContactForm();

		// Checking if the correct completion of the form only activates the captcha validation message
		Assert.assertEquals(driver.findElements(contactPage.valiadationMessageBy).size(), 1);

		
	}

}
