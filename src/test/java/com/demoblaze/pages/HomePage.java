package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(className = "list-group")
    private WebElement categoryList;

    @FindBy(xpath = "//div[@class='list-group']/a[@id='itemc']")
    private List<WebElement> categoryItems;

    private final By products = By.xpath("//div[@id='tbodyid']//a[@class='hrefch']");

    public WebElement getLogo() {
        SeleniumHelper.elementVisible(driver, logo);
        return logo;
    }

    public WebElement getCategoryList() {
        SeleniumHelper.elementVisible(driver, categoryList);
        return categoryList;
    }

    public int getCategoryListSize() {
        SeleniumHelper.elementsVisible(driver, categoryItems);
        return categoryItems.size();
    }

    public List<String> getCategoryListNames() {
        SeleniumHelper.elementsVisible(driver, categoryItems);
        return categoryItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void categoryClick(String category) {
        String xpath = String.format("//a[contains(text(), '%s')]", category);
        SeleniumHelper.click(driver, By.xpath(xpath));
    }

    public List<String> getProductsListNames() {
        SeleniumHelper.elementsVisible(driver, products);
        List<WebElement> productElements = driver.findElements(products);
        return productElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public WebElement getFirstProductElement() {
        return driver.findElements(products).get(0);
    }
}
