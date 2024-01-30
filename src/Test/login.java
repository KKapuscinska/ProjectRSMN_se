package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class login {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.rossmann.pl/logowanie");

		driver.manage().window().maximize();

		// TestCase 1 - Empty fields

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);
		Assert.assertEquals(
				driver.findElement(By.xpath("//div[1]/div/div[contains(@class,'invalid-feedback')]")).getText(),
				"Proszę wpisać poprawny adres e-mail lub nazwę użytkownika.");

		Assert.assertEquals(
			driver.findElement(By.xpath("//div[2]/div/div[contains(@class,'invalid-feedback')]")).getText(),
			"Błąd w obecnym haśle.");
				
		
		// TestCase 2 - Incorecct Login and Password
		
		driver.findElement(By.id("login-user")).sendKeys("randomText");
		Assert.assertEquals(driver.findElement(By.id("login-user")).getAttribute("value"), "randomText");

		driver.findElement(By.id("login-password")).sendKeys("randomText");
		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("value"), "randomText");

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.invalid-feedback")).getText(),
				"Niepoprawne dane logowania.");

		driver.findElement(By.id("login-user")).clear();
		driver.findElement(By.id("login-password")).clear();

		
		// TestCase 3 - Incorrect password

		driver.findElement(By.id("login-user")).sendKeys("cytest123");
		Assert.assertEquals(driver.findElement(By.id("login-user")).getAttribute("value"), "cytest123");

		driver.findElement(By.id("login-password")).sendKeys("randomText");
		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("value"), "randomText");

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.invalid-feedback")).getText(),
				"Niepoprawne dane logowania.");

		driver.findElement(By.id("login-user")).clear();

		
		// TestCase 4 - Show password Functionality

		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("type"), "password");

		driver.findElement(By.cssSelector("div.input-group-append")).click();

		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("type"), "text");
		
		driver.findElement(By.cssSelector("div.input-group-append")).click();
		
		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("type"), "password");

		driver.findElement(By.id("login-password")).clear();

		
		// TestCase 5 - Too short Login

		driver.findElement(By.id("login-user")).sendKeys("c");
		Assert.assertEquals(driver.findElement(By.id("login-user")).getAttribute("value"), "c");

		driver.findElement(By.id("login-password")).sendKeys("c");
		Assert.assertEquals(driver.findElement(By.id("login-password")).getAttribute("value"), "c");

		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.invalid-feedback")).getText(),
				"Nazwa użytkownika powinna składać się z co najmniej 4 znaków.");
		
		driver.close();

	}

}
