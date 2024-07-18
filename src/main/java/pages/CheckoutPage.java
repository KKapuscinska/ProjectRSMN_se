package main.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends BasePage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    //Web Declarations

    @FindBy(xpath="//li[contains(@class, 'deliveryOption')]")
    public
    List<WebElement> deliveryOptionList;

    @FindBy(xpath="//div[contains(@class, 'styles-module_cartRow')][1]/div[2]")
    public
    WebElement finalPrice;
    @FindBy(xpath="//div[contains(@class, 'styles-module_summary')]/div/div[3]/button")
    public
    WebElement orderBtn;

    @FindBy(xpath="//div[contains(@class, 'styles-module_stepError')]/span")
    public
    WebElement sectionTopBarErrorMsg;

    @FindBy(xpath = "//a[contains(@href, '/zamowienie/koszyk')]")
    WebElement BackBtn;

    public By deliverySectionBy = By.id("delivery");


    public String deliveryValidationErrorMsg = "Prosimy wybrać formę dostawy";

    //Methods related to Checkout

    public int countDeliveryOption() throws InterruptedException {
        waitForElementToAppear(deliverySectionBy);
        Thread.sleep(500);
        return deliveryOptionList.size();
    }

    public void clickOrderButton(){
        waitForElementToBeClickableWebElement(orderBtn);
        orderBtn.click();
    }

    public void clickGoBackBtn(){
        waitForElementToBeClickableWebElement(BackBtn);
        BackBtn.click();
    }

}
