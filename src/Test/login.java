package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class login {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.rossmann.pl/logowanie");

		driver.manage().window().maximize();
		
		WebElement loginField = driver.findElement(By.id("login-user"));
		
		WebElement passField = driver.findElement(By.id("login-password"));
		
		WebElement loginBtn = driver.findElement(By.xpath("//span[text()='Zaloguj się']"));
		
		WebElement showPassBtn = driver.findElement(By.cssSelector("div.input-group-append"));
		
		String invalidFeedbackCSS = ".invalid-feedback";
		
		String login = "cytest123";
		
		String singleLetter = "r";
		
		String randomText = "randomText";
		
		String longText = "Loremipsumdolorsitamet,consectetueradipiscingelit.Aeneancommodoligulaegetdolor.Aeneanmassa.Cumsociis";
		
		String validation_invalid_login_and_password = "Proszę wpisać poprawny adres e-mail lub nazwę użytkownika.";
		
		String validation_invalid_password = "Błąd w obecnym haśle.";
		
		String validation_min_length_requirement = "Nazwa użytkownika powinna składać się z co najmniej 4 znaków.";
		
		String validation_max_length_requirement =  "Nazwa użytkownika powinna składać się z maksymalnie 100 znaków.";
				
		String validation_invalid_login_credentials = "Niepoprawne dane logowania.";
		

		// TestCase 1 - Empty fields
		
		loginBtn.click();

		Thread.sleep(1500);
		
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(loginField)).getText(),
				validation_invalid_login_and_password);
		
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(passField)).getText(),
				validation_invalid_password);
				
		
		// TestCase 2 - Too short credentials

		loginField.sendKeys(singleLetter);

		passField.sendKeys(singleLetter);

		loginBtn.click();

		Thread.sleep(1500);
				
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(loginField)).getText(),
				validation_min_length_requirement);
		
			
		// TestCase 3 - Too long login

		loginField.sendKeys(longText);

		loginBtn.click();

		Thread.sleep(1500);
						
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(loginField)).getText(),
				validation_max_length_requirement);
		
		loginField.clear();
		passField.clear();
				
		
		// TestCase 4 - Incorrect Login and Password
		
		loginField.sendKeys(randomText);
		Assert.assertEquals(loginField.getAttribute("value"), randomText);

		passField.sendKeys(randomText);
		Assert.assertEquals(passField.getAttribute("value"), randomText);

		loginBtn.click();

		Thread.sleep(1500);

		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(loginField)).getText(),
				validation_invalid_login_credentials);

		loginField.clear();

		
		// TestCase 5 - Incorrect password

		loginField.sendKeys(login);
		Assert.assertEquals(loginField.getAttribute("value"), login);

		loginBtn.click();

		Thread.sleep(1500);
		
		Assert.assertEquals(
				driver.findElement(with(By.cssSelector(invalidFeedbackCSS)).below(loginField)).getText(),
				validation_invalid_login_credentials);

		
		// TestCase 6 - Show password Functionality

		Assert.assertEquals(passField.getAttribute("type"), "password");

		showPassBtn.click();

		Assert.assertEquals(passField.getAttribute("type"), "text");
		
		showPassBtn.click();
		
		Assert.assertEquals(passField.getAttribute("type"), "password");
		
		driver.close();


	}

}
