package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ProductPage.class);

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nava")
    private WebElement logo;

    @FindBy(css = ".row .btn-success")
    private WebElement addToCartButton;

    @FindBy(id = "cartur")
    private WebElement cartTab;

    @FindBy(className = "price-container")
    private WebElement productPrice;

    public ProductPage addToCart() {
        SeleniumHelper.clickWhenVisible(driver, addToCartButton);
        logger.info("Adding product to cart");
        return this;
    }

    public ProductPage alertAccept() {
        try {
            SeleniumHelper.waitForAlert(driver);
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
           logger.warn("Expected add-to-cart alert did not appear"); // acceptable state
        }
        return this;
    }

    public CartPage cartTabSelect() {
        logger.info("Selecting Cart tab");
        SeleniumHelper.clickWhenVisible(driver, cartTab);
        return new CartPage(driver);
    }

    public int getProductPrice() {
        SeleniumHelper.elementVisible(driver, productPrice);
        String priceText = productPrice.getText();
        String clearPrice = priceText.replaceAll("[^0-9]", "");
        logger.debug("Product price: {}", clearPrice);
        return Integer.parseInt(clearPrice);
    }

    public HomePage logoClick() {
        SeleniumHelper.clickWhenVisible(driver, logo);
        return new HomePage(driver);
    }

}
