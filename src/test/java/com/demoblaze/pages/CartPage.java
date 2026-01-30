package com.demoblaze.pages;

import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#orderModal")
    private WebElement orderModal;

    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "card")
    private WebElement creditCardInput;

    @FindBy(id = "month")
    private WebElement monthInput;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(css = "button[onclick='purchaseOrder()']")
    private WebElement purchaseButton;

    @FindBy(css = ".sweet-alert h2")
    private WebElement successModal;

    public CartPage placeOrderClick() {
        SeleniumHelper.clickWhenVisible(driver, placeOrderButton);
        return this;
    }

    public CartPage formFill(String name, String country, String city, String creditCard, String month, String year) {
        SeleniumHelper.elementVisible(driver, orderModal);
        nameInput.sendKeys(name);
        countryInput.sendKeys(country);
        cityInput.sendKeys(city);
        creditCardInput.sendKeys(creditCard);
        monthInput.sendKeys(month);
        yearInput.sendKeys(year);
        return this;
    }

    public CartPage purchaseClick() {
        SeleniumHelper.clickWhenVisible(driver, purchaseButton);
        return this;
    }

    public WebElement getSuccessModal() {
        return successModal;
    }

    public WebElement getOrderModal() {
        return orderModal;
    }

}
