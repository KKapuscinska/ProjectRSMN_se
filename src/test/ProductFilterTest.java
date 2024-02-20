package test;

import jdk.jfr.Description;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class ProductFilterTest {

	WebDriver driver;
	
	@BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
		driver.manage().window().maximize();
		
        driver.get("https://www.rossmann.pl/szukaj");
        
        // Cookies approval
     	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
     	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ot-sdk-row")));
     	wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
     	driver.findElement(By.id("onetrust-accept-btn-handler")).click();
     	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ot-sdk-row")));
    }
	
	@Test
	@Description("Verify that selecting the 'Feel Atmosphere' checkbox enables the filter.")
	public void verifyFeelAtmosphereFilter() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		driver.findElement(By.xpath("(//label[@class='checkbox'])[1]")).click();
		
		driver.findElement(By.cssSelector(".filters__btns")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn-tag.m-1")));
		
		Assert.assertEquals(driver.findElement(By.cssSelector(".btn-tag.m-1")).getText(), "CZUJESZ KLIMAT?");
	}

	
	@Description("Verify that the 'promotion' filter displays only products that are on promotion.")
	public void verifyPromotionsFilter() {
	}
	
	@Description("Verify that the 'New Arrivals' filter displays only newly arrived products.")
	public void verifyNewArrivalsFilter() {
	  
	}

	@AfterTest
	public void tearDown() {
		
	    if (driver != null) {
	    	
	        driver.quit();
	    }
	}	
	
}
