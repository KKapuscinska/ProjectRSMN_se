package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

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
	
	@Test(dataProvider="getData")
	@Description("User cannot log in.")
	public void failedLogin(String username, String password, String invalidFeedback) throws InterruptedException {

		driver.get("https://www.rossmann.pl/logowanie");
		
		driver.findElement(By.id("login-user")).sendKeys(username);

		driver.findElement(By.id("login-password")).sendKeys(password);
		
		driver.findElement(By.xpath("//span[text()='Zaloguj się']")).click();

		Thread.sleep(1500);

		Assert.assertEquals(driver
				.findElement(with(By.cssSelector(".invalid-feedback")).below(driver.findElement(By.id("login-user"))))
				.getText(), invalidFeedback);

	}

	@Test(enabled=false)
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

	@Test(enabled=false)
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

	@Test(enabled=false)
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
	
	@DataProvider
	public Object[][] getData()
	{
		Object[][] data= new Object[4][3];
		data[0][0]= "";
		data[0][1]= "";
		data[0][2]= "Proszę wpisać poprawny adres e-mail lub nazwę użytkownika.";
		
		data[1][0]= "cytest123";
		data[1][1]= "randomText";
		data[1][2]= "Niepoprawne dane logowania.";
		
		data[2][0]= "r";
		data[2][1]= "randomText";
		data[2][2]= "Nazwa użytkownika powinna składać się z co najmniej 4 znaków.";
		
		data[3][0]= "Loremipsumdolorsitamet.consectetueradipiscingelit.Aeneancommodoligulaegetdolor.Aeneanmassa.Cumsociiss";
		data[3][1]= "randomText";
		data[3][2]= "Nazwa użytkownika powinna składać się z maksymalnie 100 znaków.";
		
		return data;
	}

}
