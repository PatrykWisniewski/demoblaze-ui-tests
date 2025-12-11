package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.HomePage;
import com.demoblaze.utils.SeleniumHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class HomePageTests extends BaseTest {

    @Test
    public void logoVisibilityTest() throws IOException {
        ExtentTest test = extentReports.createTest("Logo visibility test");
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.getLogo().isDisplayed(), "Logo does not display!");
        test.log(Status.PASS, "Logo is displayed correctly.");
    }
}
