package main.java.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import main.java.pageObject.PageObject;

public class ProductCatalogue extends PageObject{

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css=".product-list__col--thirds")
	public
	List<WebElement> productList;
	
	
	//Filters sections
	@FindBy(css=".filter__tags div")
	List<WebElement> filters;
	
	@FindBy(css = ".btn-tag.m-1")
	public
    WebElement filterTag;
	
	@FindBy(css = ".filters__btns")
    WebElement showResultsButtonInFiltersSections;
	
	//Sorting, change number products per page
	@FindBy(css = "div[name='select-container1'] .sri-select__item")
    List<WebElement> perPageOptions;
	
	@FindBy(css = "div[name='select-container1']")
    WebElement perPageDropdown;
    
    @FindBy(css = "div[name='select-container1'] .sri-select__selected")
    public
    WebElement selectedPerPageOption;
		
	
	By filtersBy = By.cssSelector(".filter__tags div");
	By filterTagBy = By.cssSelector(".btn-tag.m-1");
	By checkboxInputBy = By.cssSelector("input[type='checkbox']");
	By checkboxSpanBy = By.cssSelector("span[class='checkbox']");
	By theLowestPriceInfoBy = By.cssSelector(".tile-product__lowest-price");
	By productTitleInSearchPageBy = By.cssSelector(".tile-product__name strong");
	By productSortingOptionsContainerBy = By.cssSelector(".ReactVirtualized__Grid__innerScrollContainer");


	
	
	
	public String badgeSelectorMega = ".tile-product__badge.mega";
	public String badgeSelectorOnlyOnline = ".tile-product__badge.period";
	
	
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
	
	//Filter
	public List<WebElement> getFilterList()
	{
		waitForElementToAppear(filtersBy);
		return filters;
	}
	
	public WebElement getFilterByName(String filterName)
	{
		WebElement filter = getFilterList().stream().filter(filtr -> 
		filtr.findElement(checkboxInputBy)
		.getAttribute("name")
		.equals(filterName))
		.findFirst()
		.orElse(null);
		
		return filter;
	}
	
	
	public void selectFilter(String filterName)
	{
		WebElement filter = getFilterByName(filterName);
		filter.findElement(checkboxSpanBy).click();
	}
	
	public boolean isFilterSelected(String filterName) {
	    WebElement filter = getFilterByName(filterName);
	    return filter.findElement(checkboxInputBy).isSelected();
	}
	
	public String getFilterTagText()
	{
		return filterTag.getText();
	}
	//
	public void clickShowResultsBtn()
	{
		showResultsButtonInFiltersSections.click();
		scrollToTopOfPage();
	}
	
	//Change number of products per page option
    public void changeNumberOfProductsPerPage(String optionText) {
        perPageDropdown.click();
        waitForElementToAppear(productSortingOptionsContainerBy);
       
        WebElement optionElement = perPageOptions.stream()
                .filter(element -> element
                .getText()
                .equals(optionText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such element as: " + optionText));
        optionElement.click();
        waitForElementToDisappear(productSortingOptionsContainerBy);
    }
	

}
