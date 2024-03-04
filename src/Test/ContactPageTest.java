package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

import jdk.jfr.Description;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContactPageTest {

WebDriver driver;

	@BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
		driver.manage().window().maximize();
		
		driver.get("https://www.rossmann.pl/kontakt");
		
		// Cookies approval
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ot-sdk-row")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
		driver.findElement(By.id("onetrust-accept-btn-handler")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ot-sdk-row")));
    }
	
	@Test
	@Description("Verify contact form field validation for empty fields, invalid phone number format or letters, invalid email address format, and invalid message field length.")
	public void contactFormValidation() {

		driver.findElement(By.xpath("//span[text()='Wyślij wiadomość']")).click();

		// Checking the validation message - blank email field
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("input-email"))))
				.getText(), "Proszę podaj adres e-mail.");

		// Checking the validation message - blank message field
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("message"))))
				.getText(), "Proszę wpisać treść wiadomości");

		// Checking the validation message - Captcha
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(".invalid-feedback"))
						.below(driver.findElement(By.cssSelector("div[name='recaptcha']")))).getText(),
				"Proszę wypełnić captchę");

		driver.findElement(By.id("input-phone")).sendKeys("a$");

		// Checking validation of entering letter/special char. in phone number field
		Assert.assertEquals(driver.findElement(By.id("input-phone")).getText(), "");

		driver.findElement(By.id("input-phone")).sendKeys("6");

		driver.findElement(By.id("input-email")).sendKeys("a");

		// Checking max message length
		Assert.assertEquals(driver.findElement(By.id("message")).getAttribute("maxlength"), "5000");

		driver.findElement(By.id("message")).sendKeys("Kliku kliku kliku ");

		driver.findElement(By.xpath("//span[text()='Wyślij wiadomość']")).click();

		// Checking the validation message - too short value in Phone number field
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("input-phone"))))
				.getText(), "Wpisz poprawny numer telefonu");

		// Checking the validation message - email without @
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("input-email"))))
				.getText(), "Podany adres e-mail nie jest poprawny.");

		// Checking the validation message - too short value in Message field
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("message"))))
				.getText(), "Wiadomość jest za krótka");

	}

	//@Test
	@Description("Verify that the contact form displays only the CAPTCHA validation message when all fields are filled correctly.")
	public void submitContactFormAndValidateCaptchaMessage() {

		driver.findElement(By.id("input-name-1")).sendKeys("Krzysztof");

		driver.findElement(By.id("input-name-2")).sendKeys("Jarzyna");

		driver.findElement(By.id("input-email")).sendKeys("cytest@gmail.pl");

		driver.findElement(By.id("input-phone")).sendKeys("666666666");

		driver.findElement(By.id("message")).sendKeys("Kliku kliku kliku kliku kliku");

		driver.findElement(By.xpath("//span[text()='Wyślij wiadomość']")).click();

		// Checking if the correct completion of the form only activates the captcha validation message
		Assert.assertEquals(driver.findElements(By.cssSelector(".invalid-feedback")).size(), 1);

		
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.findElement(By.id("input-name-1")).clear();
			driver.findElement(By.id("input-name-2")).clear();
			driver.findElement(By.id("input-email")).clear();
			driver.findElement(By.id("input-phone")).clear();
			driver.findElement(By.id("message")).clear();
			driver.quit();
		}
		
	}
}
