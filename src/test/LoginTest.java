package test;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;

public class LoginTest {

WebDriver driver;
	
	@BeforeTest(groups = {"smoketests"})
    public void setup() {
        WebDriverManager.chromedriver().setup();
        
        driver = new ChromeDriver();
        
		driver.manage().window().maximize();
		
		driver.get("https://www.rossmann.pl/");
    }
	
	@Test(priority = 0, groups = {"smoketests"})
	@Description("User can successfully log in via a popup login window.")
	public void successfulLoginViaPopup() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.cssSelector("a[title='Konto użytkownika']")))
			.build()
			.perform();	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='Konto użytkownika']+.nav-user__dropdown")));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//button[text()='Zaloguj się']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login-form")));
	    
		driver.findElement(By.id("login-user")).sendKeys("automation224@gmail.com");
		driver.findElement(By.id("login-password")).sendKeys("Tester123");
		
		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("login-form")));
		
		driver.findElement(By.cssSelector("a[title='Konto użytkownika']")).click();
		
		wait.until(ExpectedConditions.urlContains("/profil/ustawienia-konta"));
		
	}
	
	@Test(priority = 2)
	@Description("User can successfully log in via the login page.")
	public void successfulLoginViaPage() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		driver.findElement(By.cssSelector("a[title='Konto użytkownika']")).click();
		
		wait.until(ExpectedConditions.urlContains("/logowanie"));
		
		driver.findElement(By.id("login-user")).sendKeys("automation224@gmail.com");

		driver.findElement(By.id("login-password")).sendKeys("Tester123");
		
		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();
		
		wait.until(ExpectedConditions.urlContains("/profil/ustawienia-konta"));
	}
	
	
	@Test(priority = 1, dependsOnMethods={"successfulLoginViaPopup()"}, groups = {"smoketests"})
	@Description("User can successfully log out using the logout button in Profile.")
	public void successfulLogout() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.cssSelector("a[title='Konto użytkownika']")))
			.build()
			.perform();			
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='Konto użytkownika']+.nav-user__dropdown")));
		
		driver.findElement(By.xpath("//button[text()='Wyloguj']")).click();
		
		Thread.sleep(1500);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.rossmann.pl/");
		
		driver.findElement(By.cssSelector("a[title='Konto użytkownika']")).click();
		
		Thread.sleep(1500);
		
		Assert.assertEquals(driver.getTitle(), "Zaloguj się | Drogeria Rossmann.pl");
		
	}
	
	@Test
	@Description("Verify password visibility functionality.")
	public void verifyPasswordVisibilityFunctionality() throws InterruptedException {

		driver.get("https://www.rossmann.pl/logowanie");
		
		driver.findElement(By.id("login-password")).sendKeys("randomText");
		
		Assert.assertEquals(driver.findElement(By.id("login-password"))
				.getAttribute("type"), "password");
		
		driver.findElement(By.cssSelector(".input-group-text")).click();
		
		Thread.sleep(1500);

		Assert.assertEquals(driver.findElement(By.id("login-password"))
				.getAttribute("type"), "text");
		

	}
	
	@AfterTest(groups = {"smoketests"})
	public void tearDown() {
		
	    if (driver != null) {
	    	
	        driver.quit();
	    }
	}	
}
