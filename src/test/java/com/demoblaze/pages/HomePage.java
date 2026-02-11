package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(className = "list-group")
    private WebElement categoryList;

    @FindBy(css = ".list-group a#itemc")
    private List<WebElement> categoryItems;

    private final By products = By.cssSelector("#tbodyid .card-title");

    public WebElement getLogo() {
        SeleniumHelper.elementVisible(driver, logo);
        return logo;
    }

    public HomePage logoClick() {
        SeleniumHelper.clickWhenVisible(driver, logo);
        return new HomePage(driver);
    }

    public WebElement getCategoryList() {
        SeleniumHelper.elementVisible(driver, categoryList);
        return categoryList;
    }

    public int getCategoryListSize() {
        SeleniumHelper.elementsVisible(driver, categoryItems);
        int size = categoryItems.size();
        logger.debug("Category list size: {}", size);
        return size;
    }

    public By getProducts() {
        return products;
    }

    public List<String> getCategoryListNames() {
        SeleniumHelper.elementsVisible(driver, categoryItems);
        List<String> names = categoryItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.debug("Category list names: {}", names);
        return names;
    }

    public HomePage categoryClick(String category) {
        String xpath = String.format("//a[contains(text(), '%s')]", category);
        SeleniumHelper.clickWhenVisible(driver, By.xpath(xpath));
        logger.info("Category click: {}", category);
        return this;
    }

    public List<String> getProductsListNames() {
        SeleniumHelper.elementsVisible(driver, products);
        List<WebElement> productElements = driver.findElements(products);
        List<String> names = productElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.debug("Product list names: {}", names);
        return names;
    }

    public WebElement getFirstProductElement() {
        return driver.findElements(products).get(0);
    }

    public ProductPage productSelect(String product) {
        String xpath = String.format("//a[contains(text(), '%s')]", product);
        SeleniumHelper.clickWhenVisible(driver, By.xpath(xpath));
        logger.info("Selecting product: {}", product);
        return new ProductPage(driver);
    }
}
