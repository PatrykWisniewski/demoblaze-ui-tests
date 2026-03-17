package com.demoblaze.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumHelper {

    private static final Logger logger = LogManager.getLogger(SeleniumHelper.class);

    public static void elementVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void elementsVisible(WebDriver driver, List<WebElement> list) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(list));
    }

    public static void elementsVisible(WebDriver driver, WebElement... elements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public static void elementsVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    public static void clickWhenVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public static void clickWhenVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public static void waitForStaleness(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public static void waitForAnyElementWithText(WebDriver driver, By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> {
            try {
                return d.findElements(locator).stream()
                        .anyMatch(el -> el.getText().toLowerCase().contains(text));
            } catch (StaleElementReferenceException e) {
                logger.debug("DOM is rebuilding, retry, retrying..");
                return false;
            }
        });
    }

    public static void waitForListToChange(WebDriver driver, List<String> oldList, By locator) {
        // Waits until product list changes after AJAX category update
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(driver1 -> {
           try {
               List<String> newList = driver.findElements(locator)
                       .stream()
                       .map(WebElement::getText)
                       .toList();
               return !(oldList.equals(newList));
           } catch (StaleElementReferenceException e) {
               logger.debug("Dom is rebuilding, retrying..");
               return false;
           }
        });
    }

    public static void waitForAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public static Alert waitForAndGetAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }

    public static String getScreenshot(WebDriver driver) throws IOException {
        String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
        String path = "screenshots/" + fileName;

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("target/" + path));

        return path;
    }

}
