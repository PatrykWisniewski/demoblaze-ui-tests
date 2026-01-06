package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class HomePageTests extends BaseTest {

    @Test
    public void logoVisibilityTest() throws IOException {
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
        test.log(Status.PASS, "Category list contains expected number of 3 elements");
        List<String> categories = homePage.getCategoryListNames();
        Assert.assertTrue(categories.contains("Phones"));
        Assert.assertTrue(categories.contains("Laptops"));
        Assert.assertTrue(categories.contains("Monitors"));
        test.log(Status.PASS, "Category list contains expected category names");
    }
}
