package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.HomePage;
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
        Assert.assertTrue(successModal.getText().contains("Thank you for your purchase!"), "Success modal displays incorrect text!");
        test.log(Status.PASS, "Success modal displays proper 'Thank you for your purchase!' text.");


    }

}
