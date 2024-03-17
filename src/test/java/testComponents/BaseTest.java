package test.java.testComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.pages.HomePage;

public class BaseTest {

	public WebDriver driver;
	
	public WebDriver initializeDriver() throws IOException {
		
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\globalData.properties");
		prop.load(file);
		String browserName = prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		
		return driver;
	}
	
	@BeforeTest
	public HomePage launchApplication() throws IOException {
		
		driver = initializeDriver();
		HomePage homePage = new HomePage(driver);
		homePage.goToHomePage();
		
		return homePage;
	}
	
	@AfterTest
	public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
	
	
}
