package test;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTestFailed {

WebDriver driver;
	
	@BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        
        driver = new ChromeDriver();
        
		driver.manage().window().maximize();
		
        driver.get("https://www.rossmann.pl/logowanie");
    }
	
	@Test
	@Description("User cannot log in without filling in login fields.")
	public void emptyFieldsLoginFailure() throws InterruptedException {

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);

		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-user"))))
				.getText(), "Proszę wpisać poprawny adres e-mail lub nazwę użytkownika.");

		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-password"))))
				.getText(), "Błąd w obecnym haśle.");
	}


	@Test
	@Description("User cannot log in with an incorrect password.")
	public void incorrectPasswordLoginFailure() throws InterruptedException {

		driver.findElement(By.id("login-user")).sendKeys("cytest123");
		Assert.assertEquals(driver.findElement(By.id("login-user")).getAttribute("value"), "cytest123");

		driver.findElement(By.id("login-password")).sendKeys("randomText");
		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("value"), "randomText");

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);

		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-user"))))
				.getText(), "Niepoprawne dane logowania.");

	}

	@Test
	@Description("User cannot log in with a too short/long login.")
	public void invalidLengthLoginFailure() throws InterruptedException {

		driver.findElement(By.id("login-user")).sendKeys("r");

		driver.findElement(By.id("login-password")).sendKeys("randomText");

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);

		// Checking min login length
		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-user"))))
				.getText(), "Nazwa użytkownika powinna składać się z co najmniej 4 znaków.");
		
		driver.findElement(By.id("login-user")).sendKeys(
				"Loremipsumdolorsitamet.consectetueradipiscingelit.Aeneancommodoligulaegetdolor.Aeneanmassa.Cumsociis");
		
		//Checking max mlogin length
		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);

		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-user"))))
				.getText(), "Nazwa użytkownika powinna składać się z maksymalnie 100 znaków.");
	}

	
	@AfterMethod
	public void afterMethod() {
	        driver.findElement(By.id("login-user")).clear();
	        driver.findElement(By.id("login-password")).clear();
	}
	
	@AfterTest
	public void tearDown() {
		
	    if (driver != null) {
	    	
	        driver.quit();
	    }
	}	

}
