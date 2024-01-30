package Test;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class contact {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.rossmann.pl/kontakt");

		driver.manage().window().maximize();

		// Cookies approval
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ot-sdk-row")));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));

		driver.findElement(By.id("onetrust-accept-btn-handler")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ot-sdk-row")));

		// Validation of Email, Phone Number, and Message Field

		driver.findElement(By.cssSelector(".ladda-button.btn")).click();

		// Checking the validation message - blank email field
		Assert.assertEquals(driver.findElement(By.cssSelector("#input-email+div")).getText(),
				"Proszę podaj adres e-mail.");

		// Checking the validation message - blank message field
		Assert.assertEquals(driver.findElement(By.cssSelector("#message+div")).getText(),
				"Proszę wpisać treść wiadomości");

		// Checking the validation message - Captcha
		Assert.assertEquals(driver.findElement(By.cssSelector(".re-captcha.is-invalid div.invalid-feedback")).getText(),
				"Proszę wypełnić captchę");

		driver.findElement(By.id("input-email")).sendKeys("a");

		driver.findElement(By.id("input-phone")).sendKeys("a$");

		// Checking validation of entering letter/special char. in phone number field
		Assert.assertEquals(driver.findElement(By.id("input-phone")).getText(), "");

		driver.findElement(By.id("input-phone")).sendKeys("6");

		driver.findElement(By.id("message")).sendKeys("Kliku kliku kliku ");

		// Checking max message length
		Assert.assertEquals(driver.findElement(By.id("message")).getAttribute("maxlength"), "5000");

		driver.findElement(By.cssSelector(".ladda-button")).click();

		// Checking the validation message - email without @
		Assert.assertEquals(driver.findElement(By.cssSelector("#input-email+div")).getText(),
				"Podany adres e-mail nie jest poprawny.");

		// Checking the validation message - too short value in Phone number field
		Assert.assertEquals(driver.findElement(By.cssSelector("#input-phone+div")).getText(),
				"Wpisz poprawny numer telefonu");

		// Checking the validation message - too short value in Message field
		Assert.assertEquals(driver.findElement(By.cssSelector("#message+div")).getText(), "Wiadomość jest za krótka");

		// TestCase 2 - Filling out the Contact Form

		driver.findElement(By.id("input-name-1")).sendKeys("Krzysztof");
		driver.findElement(By.id("input-name-1")).sendKeys("Jarzyna");
		driver.findElement(By.id("input-email")).sendKeys("@o2.pl");

		driver.findElement(By.id("input-phone")).sendKeys("66666666");

		driver.findElement(By.id("message")).sendKeys("kliku");

		driver.findElement(By.cssSelector(".ladda-button")).click();

		// Checking if the correct completion of the form only activates the captcha validation message
		Assert.assertEquals(driver.findElements(By.cssSelector(".invalid-feedback")).size(), 1);

		driver.close();
	}
}
