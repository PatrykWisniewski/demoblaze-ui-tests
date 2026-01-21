package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".row .btn-success")
    private WebElement addToCartButton;

    @FindBy(id = "cartur")
    private WebElement cartTab;

    public ProductPage addToCart() {
        SeleniumHelper.clickWhenVisible(driver, addToCartButton);
        return this;
    }

    public ProductPage alertAccept() {
        try {
            SeleniumHelper.waitForAlert(driver);
            driver.switchTo().alert().accept();
        } catch (TimeoutException e) {
            // alert did not appear – acceptable state
        }
        return this;
    }

    public CartPage cartTabSelect() {
        SeleniumHelper.clickWhenVisible(driver, cartTab);
        return new CartPage(driver);
    }

}
