package main.java.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pageobject.PageObject;

public class CartPage extends PageObject{

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
	List<WebElement> cartProductsList;
		
	@FindBy(xpath = "//span[contains(@class, 'styles-module_brand')]")
	public
    WebElement cartProductNameElement;
		
	@FindBy(xpath = "//span[2][contains(@class, 'styles-module_totalAmount')]")
	public
    WebElement cartTotalValue;
		
	@FindBy(xpath = "//input[contains(@data-testid, 'input')]")
	public
    WebElement selectedQuantityOfProductInCart;
	
	@FindBy(css=".h3")
	public
	WebElement headerElement;
		
	By RemoveBtnBy = By.xpath("//button[contains(@class, 'styles-module_trashButton')]");
	By cartProductNameBy = By.xpath("//span[contains(@class, 'styles-module_brand')]");
	By cartProductPriceBy = By.xpath("//span[contains(@class, 'styles-module_pricePerUnit')]");
	By cartProductsListBy = By.xpath("//li[contains(@class, 'styles-module_listItem')]");
	By increaseBtnBy = By.xpath("//button[contains(@data-testid, 'plus-button')]"); 
	public By cartIconWithQuantityBy = By.xpath("//div[contains(@class, 'StatusBadge-module_badge')]");	
	
	//Methods related to shopping cart
	
	public void clickRemoveButtonInCartForAllProducts() {
	    List<WebElement> removeButtons = driver.findElements(RemoveBtnBy);
	    for (WebElement button : removeButtons) {
	        button.click();
	    }
	    waitForElementToPresentNumberOfElements(cartProductsListBy, 0);
	 }
		
	public void clearCart() throws InterruptedException {
		goToShoppingCart();
		clickRemoveButtonInCartForAllProducts();
	}
		
	public String getTextFromHeaderElement() {
	    return headerElement.getText();
	}
	
	public List<String> getCartProductNames() {
	    List<String> productNames = new ArrayList<>();
	    List<WebElement> cartProductElements = driver.findElements(cartProductNameBy);
	    for (WebElement cartProductElement : cartProductElements) {
	        productNames.add(cartProductElement.getText());
	    }
	    return productNames;
	}
	
	public List<String> getCartProductPrices() {
	    List<String> productPrices = new ArrayList<>();
	    List<WebElement> cartProductPriceElements = driver.findElements(cartProductPriceBy);
	    for (WebElement cartProductPriceElement : cartProductPriceElements) {
	        String fullText = cartProductPriceElement.getText();
	        String priceOnly = fullText.replace("Cena za szt.:", "").trim();
	        productPrices.add(priceOnly);
	    }
	    return productPrices;
	}
	
	public String getShoppingCartValue() {
		String cartValue = cartTotalValue.getText();
		return cartValue;
	}

	public String getProductQuantityInCartFromCounter() {
		WebElement inputElement = selectedQuantityOfProductInCart;
		String selectedQuantityOfProductInCartValue = inputElement.getAttribute("value");
		return selectedQuantityOfProductInCartValue;
	}
	
	public String getProductQuantityInCartFromIcon() {
		return shoppingCartQuantityIcon.getText();
	}
	
	public int getNumberOfUniqueProductsInCart() {
		return cartProductsList.size();
	}
	
	public void increaseNumberOfProductInCart(int quantity) {
		 WebElement addButton = driver.findElement(increaseBtnBy);
		    for (int i = 0; i < quantity; i++) {
		        addButton.click();
		    }
       }
	
	
	
	
}
