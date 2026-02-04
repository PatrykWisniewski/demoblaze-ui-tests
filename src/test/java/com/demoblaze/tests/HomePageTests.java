package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.HomePage;
import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTests extends BaseTest {

    @Test
    public void logoVisibilityTest() {
        ExtentTest test = extentReports.createTest("Logo visibility test");
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.getLogo().isDisplayed(), "Logo does not display!");
        test.log(Status.PASS, "Logo is displayed correctly.");
    }

    @Test
    public void categoryListTest() {
        ExtentTest test = extentReports.createTest("Category list verification");
        int expectedCategoryListSize = 3;
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.getCategoryList().isDisplayed(), "Category list is not displayed!");
        test.log(Status.PASS, "Category list is displayed");

        Assert.assertEquals(homePage.getCategoryListSize(), expectedCategoryListSize);
        test.log(Status.PASS, String.format("Category list contains expected number of %s elements", expectedCategoryListSize));

        List<String> categories = homePage.getCategoryListNames();

        Assert.assertTrue(categories.contains("Phones"));
        Assert.assertTrue(categories.contains("Laptops"));
        Assert.assertTrue(categories.contains("Monitors"));
        test.log(Status.PASS, "Category list contains expected category names");
    }

    @Test
    public void categoryListChangeTest() {
        ExtentTest test = extentReports.createTest("Category list change verification");
        HomePage homePage = new HomePage(driver);

        homePage.categoryClick("Phones");
        List<String> phonesProducts = homePage.getProductsListNames();

        WebElement oldProduct = homePage.getFirstProductElement();

        homePage.categoryClick("Laptops");
        SeleniumHelper.waitForStaleness(driver, oldProduct); // Wait for AJAX-loaded product list to be replaced in the DOM
        SeleniumHelper.waitForAnyElementWithText(driver, homePage.getProducts(), "sony");
        List<String> laptopsProducts = homePage.getProductsListNames();

        Assert.assertNotEquals(phonesProducts, laptopsProducts);
        Assert.assertTrue(laptopsProducts.contains("Sony vaio i5"));
        Assert.assertFalse(laptopsProducts.contains("Samsung galaxy s6"));
        test.log(Status.PASS, "The product list has changed");
    }
}
