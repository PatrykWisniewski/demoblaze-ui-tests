package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.demoblaze.utils.DriverFactory;
import com.demoblaze.utils.PropertiesLoader;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

@Listeners(com.demoblaze.listeners.TestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extentReports;
    public static ExtentTest extentTest;

    @BeforeSuite
    public void beforeSuite() {
        htmlReporter = new ExtentSparkReporter("index.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void setup(ITestContext context, Method method) throws IOException {
        String env = PropertiesLoader.loadProperty("env");
        driver = DriverFactory.getDriver();
        driver.get(PropertiesLoader.getBaseUrl());
        driver.manage().window().maximize();

        context.setAttribute("driver", driver);

        extentTest = extentReports.createTest(method.getName());

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void afterSuite() {
        extentReports.flush();
    }
}
