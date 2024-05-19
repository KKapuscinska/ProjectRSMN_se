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

import main.java.pageobject.PageObject;

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
	
	//ProductCatalogue
	@FindBy(xpath="//section/div/div/div/section")
	public
	List<WebElement> productList;
	
	public By productListBy = By.xpath("//section/div/div/div/section");
	By availableToSellProductListBy = By.cssSelector(".nav-user__icon");
	By theLowestPriceInfoBy = By.xpath("//div[contains(@class, 'tile-product__lowest-price')][2]");
	By productTitleInSearchPageBy = By.xpath("//a/div/strong");
	By shoppingCartQuantityIconBy = By.xpath("//div[contains(@class, 'StatusBadge-module_badge')]");
	
	//ProductPage
	@FindBy(xpath = "//h1[contains(@class, 'styles-module_titleBrand')]")
	public
    WebElement productPageNameElement;
	
	@FindBy(css = ".product-price .h2")
	public
    WebElement productPagePriceElement;
	
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
	By feelAtmosphereLabelBy = By.xpath("//span[@data-testid='feel-atmosphere-badge']");

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
	public String badgeSelectorMegaXPath = "//span[contains(text(),'MEGA!')]";
	
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
		Thread.sleep(1000);
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
		Thread.sleep(1000);
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
		Thread.sleep(1000);
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
	
	public void clickToRandomProductOnPage() {
		
		Random random = new Random();
        int randomIndex = random.nextInt(productList.size());
        WebElement randomProduct = productList.get(randomIndex);
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
	
	public List<String> getProductsMissingBadge(String badgeSelectorXPath) {
        List<String> productsWithoutBadge = new ArrayList<>();

        for (WebElement product : productList) {
            if (product.findElements(By.xpath(badgeSelectorXPath)).isEmpty()) {
                productsWithoutBadge.add(product.findElement(productTitleInSearchPageBy).getText());
            }
        }

        return productsWithoutBadge;
    }
	

	
}
	



