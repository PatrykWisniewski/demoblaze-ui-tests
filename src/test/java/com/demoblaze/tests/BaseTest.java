package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.demoblaze.utils.DriverFactory;
import com.demoblaze.utils.PropertiesLoader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extentReports;

    @BeforeSuite
    public void beforeSuite() {
        htmlReporter = new ExtentSparkReporter("index.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void setup() throws IOException {
        driver = DriverFactory.getDriver();
        driver.get(PropertiesLoader.loadProperty("browser.url"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
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
