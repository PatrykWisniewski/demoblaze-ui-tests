package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(xpath = "//a[text()='Contact']")
    private WebElement contactButton;

    @FindBy(id = "recipient-email")
    private WebElement contactEmailField;

    @FindBy(id = "recipient-name")
    private WebElement contactNameField;

    @FindBy(id = "message-text")
    private WebElement contactMessageField;

    @FindBy(xpath = "//button[text()='Send message']")
    private WebElement contactSendMessageButton;


    private final By products = By.cssSelector("#tbodyid .card-title");

    private final By images = By.cssSelector(".card-img-top.img-fluid");


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

    public void contactButtonClick() {
        SeleniumHelper.clickWhenVisible(driver, contactButton);
    }

    public int getCategoryListSize() {
        SeleniumHelper.elementsVisible(driver, categoryItems);
        int size = categoryItems.size();
        logger.debug("Category list size: {}", size);
        return size;
    }

    public By getProductsLocator() {
        return products;
    }

    public By getImagesLocator() {
        return images;
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

    public List<WebElement> getProductsImages() {
        SeleniumHelper.elementsVisible(driver, images);
        return driver.findElements(images);
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

    public boolean areAllImagesLoaded(List<WebElement> list) {
        int index = 1;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement image : list) {
            boolean loaded = (Boolean) js.executeScript("return arguments[0].complete && arguments[0].naturalWidth > 0", image);
            logger.debug("Image {} is loaded: {}", index++, loaded);
            if (!loaded) {
                return false;
            }
        }
        return true;
    }

    public void contactFormFill(String email, String contactName, String message) {
        SeleniumHelper.elementVisible(driver, contactEmailField);
        contactEmailField.sendKeys(email);
        contactNameField.sendKeys(contactName);
        contactMessageField.sendKeys(message);
    }

    public void contactFormSend() {
        SeleniumHelper.clickWhenVisible(driver, contactSendMessageButton);
    }

}
