package com.demoblaze.listeners;

import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        WebDriver driver = (WebDriver)
                result.getTestContext().getAttribute("driver");

        try {
            String screenshotPath = SeleniumHelper.getScreenshot(driver);

            BaseTest.extentTest
                    .fail(result.getThrowable())
                    .addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {
            BaseTest.extentTest
                    .fail("Failed to capture screenshot");
        }
    }
}