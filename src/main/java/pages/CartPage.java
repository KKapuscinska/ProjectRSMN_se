package main.java.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {

	WebDriver driver;

	Actions actions;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	//WebElements declarations
	
	@FindBy(xpath="//li[contains(@class, 'styles-module_listItem')]")
	public
	List<WebElement> productsList;
		
	@FindBy(xpath = "//span[contains(@class, 'styles-module_brand')]")
	public
    WebElement productNameElement;
		
	@FindBy(xpath = "//span[2][contains(@class, 'styles-module_totalAmount')]")
	public
    WebElement totalValue;

	@FindBy(xpath = "//input[contains(@data-testid, 'input')]")
	public
    WebElement selectedQuantityOfProduct;
	
	@FindBy(xpath="//div[@class='h3 pb-3']")
	public
	WebElement emptyCartHeader;

	@FindBy(xpath="//div[contains(@class,'summaryContainer')]/div/button")
	public
	WebElement summaryBtn;

	@FindBy(xpath = "//div[@class='login-form__bypass']/a")
	public
	WebElement buyWithoutRegistrationBtn;



	By RemoveBtnBy = By.xpath("//button[contains(@class, 'styles-module_trashButton')]");
	public By productNameBy = By.xpath("//span[contains(@class, 'styles-module_brand')]");
	By productPriceBy = By.xpath("//span[contains(@class, 'styles-module_pricePerUnit')]");
	By productsListBy = By.xpath("//li[contains(@class, 'styles-module_listItem')]");
	By increaseBtnBy = By.xpath("//button[contains(@data-testid, 'plus-button')]");
	public String emptyCartHeaderText = "Twój koszyk jest pusty";
	By checkoutSummarySectionBy = By.xpath("//div[contains(@class, 'styles-module_summary')]");

	//Methods related to shopping cart
	
	public void clickRemoveButtonForAllProducts() throws InterruptedException {
	    List<WebElement> removeButtons = driver.findElements(RemoveBtnBy);
	    for (WebElement button : removeButtons) {
			waitForElementToBeClickableWebElement(button);
			Thread.sleep(1000);
	        button.click();
	    }
	    waitForElementToPresentNumberOfElements(productsListBy, 0);
	 }
		
	public void clearCart() throws InterruptedException {
		goToShoppingCart();
		clickRemoveButtonForAllProducts();
	}
		
	public String getTextFromHeaderElement() {
	    return emptyCartHeader.getText();
	}
	
	public List<String> getCartProductNames() {
	    List<String> productNames = new ArrayList<>();
	    List<WebElement> cartProductElements = driver.findElements(productNameBy);
	    for (WebElement cartProductElement : cartProductElements) {
	        productNames.add(cartProductElement.getText());
	    }
	    return productNames;
	}
	
	public List<String> getCartProductPrices() {
	    List<String> productPrices = new ArrayList<>();
	    List<WebElement> cartProductPriceElements = driver.findElements(productPriceBy);
	    for (WebElement cartProductPriceElement : cartProductPriceElements) {
	        String fullText = cartProductPriceElement.getText();
	        String priceOnly = fullText.replace("Cena za szt.:", "").trim();
	        productPrices.add(priceOnly);
	    }
	    return productPrices;
	}
	
	public String getCartValue() {
		String cartValue = totalValue.getText();
		return cartValue;
	}

	public String getProductQuantityFromCounter() {
		WebElement inputElement = selectedQuantityOfProduct;
		String selectedQuantityOfProductInCartValue = inputElement.getAttribute("value");
		return selectedQuantityOfProductInCartValue;
	}

	public int getNumberOfUniqueProductsInCart() {
		return productsList.size();
	}
	
	public void increaseNumberOfProduct(int quantity) {
		 WebElement addButton = driver.findElement(increaseBtnBy);
		    for (int i = 0; i < quantity; i++) {
		        addButton.click();
		    }
       }

	public void clickSummaryButton(){
		waitForElementToBeClickableWebElement(summaryBtn);
		summaryBtn.click();
		waitForElementToAppearWebElement(summaryBtn);
	}

	public void clickBuyWithoutRegistration() {
		clickSummaryButton();
		waitForElementToAppear(loginPopupBy);
		waitForElementToBeClickableWebElement(buyWithoutRegistrationBtn);
		buyWithoutRegistrationBtn.click();
		waitForElementToAppear(checkoutSummarySectionBy);
	}


}
