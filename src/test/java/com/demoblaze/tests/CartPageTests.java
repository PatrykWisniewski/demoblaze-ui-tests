package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.ProductPage;
import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTests extends BaseTest {

    @Test
    public void productPurchaseE2eTest() {
        ExtentTest test = extentReports.createTest("Product purchase - E2E test");

        HomePage homePage = new HomePage(driver);

        WebElement successModal = homePage
                .categoryClick("Laptops")
                .productSelect("MacBook Pro")
                .addToCart()
                .alertAccept()
                .cartTabSelect()
                .placeOrderClick()
                .formFill("Janek", "Poland", "Warsaw", "123456789", "05", "27")
                .purchaseClick()
                .getSuccessModal();

        Assert.assertTrue(successModal.isDisplayed(), "Success modal does not display!");

        test.log(Status.PASS, "Success modal is displayed");

        Assert.assertTrue(successModal.getText().contains("Thank you for your purchase!"),
                                            "Success modal displays incorrect text!");

        test.log(Status.PASS, "Success modal displays proper 'Thank you for your purchase!' text.");
    }

    @Test
    public void emptyFormModalValidation() {
        ExtentTest test = extentReports.createTest("Product purchase - Empty Form Validation");

        HomePage homePage = new HomePage(driver);

                 homePage
                .categoryClick("Laptops")
                .productSelect("MacBook Pro")
                .addToCart()
                .alertAccept()
                .cartTabSelect()
                .placeOrderClick()
                .purchaseClick();

        Alert errorAlert = SeleniumHelper.waitForAndGetAlert(driver);

        test.log(Status.PASS, "Error alert displays");

        Assert.assertEquals(errorAlert.getText(),
                "Please fill out Name and Creditcard.",
                "Error displays incorrect text!");
        test.log(Status.PASS, "Empty alert displays correct text");

        errorAlert.accept();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.getOrderModal().isDisplayed(), "Order modal disappeared!");

        test.log(Status.PASS, "Order modal is still displayed as expected");
    }

    @Test
    public void totalPriceValidation() {
        HomePage homePage = new HomePage(driver);
        ProductPage firstProduct = homePage.productSelect("Samsung galaxy s6");

        int firstProductPrice = firstProduct.getProductPrice();

        firstProduct.addToCart();
        firstProduct.alertAccept();

        homePage = firstProduct.logoClick();

        ProductPage secondProduct = homePage.productSelect("Nokia lumia 1520");

        int secondProductPrice = secondProduct.getProductPrice();
        int expectedTotal = firstProductPrice+secondProductPrice;

        secondProduct.addToCart();
        secondProduct.alertAccept();

        CartPage cartPage = secondProduct.cartTabSelect();

        Assert.assertEquals(cartPage.getTotalPrice(), expectedTotal, "The Cart displays incorrect total price!");

    }

}
