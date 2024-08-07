package test.java.tests;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import jdk.jfr.Description;
import main.java.pages.HomePage;
import main.java.pages.LoginPage;
import main.java.pages.ProfilePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BrokenLinksTest extends BaseTest {
    LoginPage loginPage;
    ProfilePage profilePage;

    @BeforeClass(alwaysRun = true)
    public void setup() throws IOException {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        profilePage = PageFactory.initElements(driver, ProfilePage.class);
        loginPage.acceptCookiesInCookieBar();
    }

    @Test(dataProvider = "getLoginData", groups = "smoke")
    @Description("Check functionality of profile tab links.")
    public void checkProfileTabLinks(HashMap<String, String> input) throws IOException, InterruptedException {

        loginPage.goToLoginPage();
        loginPage.loginByCorrectCredentials(input.get("username"), input.get("password"));
        loginPage.clickAccountButtonIcon();

        SoftAssert a = new SoftAssert();

        for (WebElement link : profilePage.profileLinkList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
        loginPage.logout();
    }

    @Test(groups = "smoke")
    @Description("Check functionality of user buttons - profile, favorite products and shopping cart.")
    public void checkUserButtonsLinks() throws MalformedURLException, IOException, InterruptedException {

        SoftAssert a = new SoftAssert();

        for (WebElement link : loginPage.navUserDropdownButtonsList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();

        for (WebElement link : loginPage.navUserButtonsList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
    }

    @Test(groups = "smoke")
    @Description("Check functionality of top bar category, Promotions and newspaper links.")
    public void checkCategoryLinks() throws MalformedURLException, IOException, InterruptedException {

        SoftAssert a = new SoftAssert();

        for (WebElement link : loginPage.topBarCategoryLinkList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
    }

    @Test(groups = "smoke")
    @Description("Check functionality of top bar products capsule links.")
    public void checkCapsuleLinks() throws MalformedURLException, IOException, InterruptedException {

        SoftAssert a = new SoftAssert();

        for (WebElement link : loginPage.capsuleLinkList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
    }

    @Test(groups = "smoke")
    @Description("Check functionality of top bar Campain links.")
    public void checkCampainLinks() throws MalformedURLException, IOException, InterruptedException {

        SoftAssert a = new SoftAssert();

        for (WebElement link : loginPage.campainLinkList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
    }

    @Test(groups = "smoke")
    @Description("Check functionality of footer links.")
    public void checkFooterLinks() throws MalformedURLException, IOException, InterruptedException {

        SoftAssert a = new SoftAssert();

        for (WebElement link : loginPage.footerLinkList) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            //System.out.println(respCode);

            a.assertTrue(respCode < 400, "The link with text: " + link.getText() + " is broken with code: " + respCode);
        }

        a.assertAll();
    }

    @DataProvider
    public Object[][] getLoginData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\main\\resources\\testdata\\login\\ValidData.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}, {data.get(2)}};
    }

}