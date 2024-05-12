package main.java.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import main.java.pageObject.PageObject;

public class ProductCatalogue extends PageObject{

	WebDriver driver;

	Actions actions;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver=driver;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	//WebElements declarations
	
	//Product Catalogue
	@FindBy(xpath="//div[starts-with(@data-testid, 'product-tile')]")
	public
	List<WebElement> productList;
	
	@FindBy(css=".tile-product__name")
	public
	List<WebElement> productNameList;
	
	@FindBy(css=".h3")
	public
	WebElement headerElement;
	
	@FindBy(css=".nav-user__quantity")
	public
	WebElement shoppingCartQuantityIcon;
	
	@FindBy(css=".Toastify__toast-body")
	public
	WebElement toastElement;
	
	public By productListBy = By.xpath("//div[starts-with(@data-testid, 'product-tile')]");
	By availableToSellProductListBy = By.cssSelector(".product-list__col--thirds .nav-user__icon");
	public By shoppingCartIconOnProductBy = By.cssSelector(".tile-product__add-list-plus");
	public By productNameVisibleInProductCatalogueBy = By.cssSelector(".tile-product__name strong");
	By theLowestPriceInfoBy = By.cssSelector(".tile-product__lowest-price");
	By productTitleInSearchPageBy = By.cssSelector(".tile-product__name strong");
	By shoppingCartDropdownBy = By.id("dropdown-mini_basket");
	By shoppingCartQuantityIconBy = By.cssSelector(".nav-user__quantity");
	
	//Product Page
	@FindBy(css = ".product-info__name > h1.h1")
	public
    WebElement productPageNameElement;
	
	@FindBy(css = ".product-price .h2")
	public
    WebElement productPagePriceElement;
	
	
	//ShoppingCart
	@FindBy(className="cart-product")
	public
	List<WebElement> cartProductsList;
	
	@FindBy(css = ".cart-product__name")
	public
    WebElement cartProductNameElement;
	
	@FindBy(css = ".price-details__value-total")
	public
    WebElement cartTotalValue;
	
	@FindBy(css = ".sri-select__selected")
	public
    WebElement quantityOfProductInCart;
	
	@FindBy(css = ".cart-product__quantity")
	public
    WebElement quantityDropdownElement;
	
	@FindBy(css = ".sri-select__item")
	public
	List<WebElement> quantityDropdownElements;
	
	By RemoveBtnBy = By.cssSelector(".btn-del");
	By cartProductNameBy = By.cssSelector(".cart-product__name strong");
	By cartProductPriceBy = By.cssSelector(".cart-product__price");
	By cartProductListBy = By.cssSelector(".cart-product");
	public By cartQuantityDropdownBy = By.cssSelector(".sri-select__items-container");
	
	//Recommendations filters
	@FindBy(css = "button[data-testid='recommended-select-open-btn']")
	public
    WebElement recommendedProductsSelect;
	
	@FindBy(css = "button[data-testid='recommended-filter-dropdown-select-submit-btn']")
	public
    WebElement submitRecommendedBtn;
	
	By recommendedDropdownBy = By.cssSelector("div[data-testid='recommended-filter-dropdown-select-content']");
	
	//FeelAtmosphereFilter
	@FindBy(css = "input[data-testid='brands-select-checkbox-feelAtmosphere'] + span.checkbox")
	public
    WebElement feelAtmosphereFilterCheckbox;
	
	@FindBy(css = "button[data-testid='filters-chip-feelAtmosphere']")
	public
    WebElement feelAtmosphereFilterChip;
	
	@FindBy(css = "span[data-testid='filter-chip-close-feelAtmosphere']")
	public
    WebElement closeButtonOnFeelAtmosphereFilterLabel;
	
	By closeButtonOnFeelAtmosphereFilterLabelBy = By.cssSelector("span[data-testid='filter-chip-close-feelAtmosphere']");
	By feelAtmosphereChipBy = By.cssSelector("button[data-testid='filters-chip-feelAtmosphere']");
	By feelAtmosphereLabelBy = By.xpath("//a[@aria-label='Czujesz Klimat']");
	

	//Promotions filters
	@FindBy(css = "button[data-testid='promotions-select-open-btn']")
	public
    WebElement promotionsProductsSelect;
	
	@FindBy(css = "button[data-testid='promotions-filter-dropdown-select-submit-btn']")
	public
    WebElement submitPromotionsBtn;
	
	By promotionsDropdownBy = By.cssSelector("div[data-testid='promotions-filter-dropdown-select-content']");
	
	//MegaFilter
	@FindBy(css = "input[data-testid='brands-select-checkbox-mega'] + span.checkbox")
	public
    WebElement megaFilterCheckbox;
	
	@FindBy(css = "button[data-testid='filters-chip-mega']")
	public
    WebElement megaFilterChip;
	
	@FindBy(css = "span[data-testid='filter-chip-close-mega']")
	public
    WebElement closeButtonOnMegaFilterLabel;
	
	By closeButtonOnMegaFilterLabelBy = By.cssSelector("span[data-testid='filter-chip-close-mega']");
	By megaChipBy = By.cssSelector("button[data-testid='filters-chip-mega']");
	
	public String badgeSelectorMega = ".tile-product__badge.mega";
	
	//PromotionFilter
	@FindBy(css = "input[data-testid='brands-select-checkbox-promotion'] + span.checkbox")
	public
    WebElement promotionFilterCheckbox;
	
	@FindBy(css = "button[data-testid='filters-chip-promotion']")
	public
    WebElement promotionFilterChip;
	
	@FindBy(css = "span[data-testid='filter-chip-close-promotion']")
	public
    WebElement closeButtonOnPromotionFilterLabel;
	
	By closeButtonOnPromotionFilterLabelBy = By.cssSelector("span[data-testid='filter-chip-close-promotion']");
	By promotionChipBy = By.cssSelector("button[data-testid='filters-chip-promotion']");
	
	
	//Methods related to filtering
	
	//Recommendations filters methods
	public void openRecommendedSelect() {
		recommendedProductsSelect.click();
		waitForElementToAppear(recommendedDropdownBy);
	}
	
	public void submitRecommendedFilters() {
		submitRecommendedBtn.click();
		waitForElementToDisappear(recommendedDropdownBy);
	}
	
	//FeelAtmosphereFilter
	public void selectFeelAtmosphereFilter() {
		openRecommendedSelect();
		feelAtmosphereFilterCheckbox.click();
		submitRecommendedFilters();	
	}
	
	public String getFeelAtmosphereFilterChipText() {
		return feelAtmosphereFilterChip.getText();
	}
	
	public void clickCloseButtonOnFeelAtmosphereFilterLabel() throws InterruptedException {
		Thread.sleep(1000);
		scrollToTopOfPage();
		waitForElementToBeClicable(closeButtonOnFeelAtmosphereFilterLabelBy);
		closeButtonOnFeelAtmosphereFilterLabel.click();
	}
	
	public boolean isFeelAtmosphereChipVisibleOnProductCatalogue() {
	    List<WebElement> elements = driver.findElements(feelAtmosphereChipBy);
	    return !elements.isEmpty();
	}
	
	public boolean isFeelAtmosphereLabelVisibleOnProductPage() {
        try {
            WebElement feelAtmosphereLabel = waitForElementToAppearWebElement(feelAtmosphereLabelBy);
            return feelAtmosphereLabel.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
	
	//Promotions filters methods
	public void openPromotionsSelect() {
		promotionsProductsSelect.click();
		waitForElementToAppear(promotionsDropdownBy);
	}
	
	public void submitPromotionsFilters() {
		submitPromotionsBtn.click();
		waitForElementToDisappear(promotionsDropdownBy);
	}
	
	//MegaFilter
	public void selectMegaFilter() {
		openPromotionsSelect();
		megaFilterCheckbox.click();
		submitPromotionsFilters();	
	}
	
	public String getMegaFilterChipText() {
		return megaFilterChip.getText();
	}
	
	public void clickCloseButtonOnMegaFilterLabel() throws InterruptedException {
		Thread.sleep(1000);
		scrollToTopOfPage();
		waitForElementToBeClicable(closeButtonOnMegaFilterLabelBy);
		closeButtonOnMegaFilterLabel.click();
	}
	
	public boolean isMegaChipVisibleOnProductCatalogue() {
	    List<WebElement> elements = driver.findElements(megaChipBy);
	    return !elements.isEmpty();
	}
	
	//PromotionsFilter
	public void selectPromotionsFilter() {
		openPromotionsSelect();
		promotionFilterCheckbox.click();
		submitPromotionsFilters();	
	}
	
	public String getPromotionFilterChipText() {
		return promotionFilterChip.getText();
	}
	
	public void clickCloseButtonOnPromotionFilterLabel() throws InterruptedException {
		Thread.sleep(1000);
		scrollToTopOfPage();
		waitForElementToBeClicable(closeButtonOnPromotionFilterLabelBy);
		closeButtonOnPromotionFilterLabel.click();
	}
	
	public boolean isPromotionChipVisibleOnProductCatalogue() {
	    List<WebElement> elements = driver.findElements(promotionChipBy);
	    return !elements.isEmpty();
	}

	//Methods related to products

	public List<WebElement> getProductList() {
        return driver.findElements(productListBy);
    }
	
	public List<WebElement> getAvailableToSellProductList() {
        return driver.findElements(availableToSellProductListBy);
    }
	
	public List<WebElement> getShoppingCartIconList() {
        return driver.findElements(shoppingCartIconOnProductBy);
    }
	
	public void clickToRandomProductOnPage() {
		
		Random random = new Random();
        int randomIndex = random.nextInt(productNameList.size());
        WebElement randomProduct = productNameList.get(randomIndex);
        randomProduct.click();
	}
	
	public void addToShoppingCartFirstAvailableProductOnProductCatalogue() {
		waitForElementToAppear(productListBy);
		List<WebElement> products = getAvailableToSellProductList();
			if (products.size() > 0) {
				WebElement firstProduct = products.get(0);
				firstProduct.click();
			}	
			waitForElementToAppear(shoppingCartQuantityIconBy);
	}

	
	public List<String> getProductsMissingTheLowestPriceInformation() {
        List<String> productsWithoutTheLowestPriceInfo = new ArrayList<>();

        for (WebElement product : productList) {
            if (product.findElements(theLowestPriceInfoBy).isEmpty()) {
                if (product.findElements(productTitleInSearchPageBy).isEmpty()) {
                    continue;
                }
                productsWithoutTheLowestPriceInfo.add(product.findElement(productTitleInSearchPageBy).getText());
            }
        }

        return productsWithoutTheLowestPriceInfo;
    }
	
	public List<String> getProductsMissingBadge(String badgeSelector) {
        List<String> productsWithoutBadge = new ArrayList<>();

        for (WebElement product : productList) {
            if (product.findElements(By.cssSelector(badgeSelector)).isEmpty()) {
                productsWithoutBadge.add(product.findElement(productTitleInSearchPageBy).getText());
            }
        }

        return productsWithoutBadge;
    }
	
	//Methods related to shopping cart
	
	public void clickRemoveButtonInCartForAllProducts() {
	    List<WebElement> removeButtons = driver.findElements(RemoveBtnBy);
	    for (WebElement button : removeButtons) {
	        button.click();
	    }
	    waitForElementToPresentNumberOfElements(cartProductListBy, 0);
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
	        productPrices.add(cartProductPriceElement.getText());
	    }
	    return productPrices;
	}
	
	public String getShoppingCartValue() {
		String cartValue = cartTotalValue.getText();
		return cartValue;
	}

	public String getProductQuantityInCartFromCounter() {
		return quantityOfProductInCart.getText();
	}
	
	public String getProductQuantityInCartFromIcon() {
		return shoppingCartQuantityIcon.getText();
	}
	
	public int getNumberOfUniqueProductsInCart() {
		return cartProductsList.size();
	}
	
	public void openQuantityProductDropdownInCart() {
		waitForElementToAppear(cartProductListBy);
		quantityDropdownElement.click();
		waitForElementToAppear(cartQuantityDropdownBy);
	}

}
