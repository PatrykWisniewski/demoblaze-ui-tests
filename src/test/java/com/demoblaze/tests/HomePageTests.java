package com.demoblaze.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demoblaze.pages.HomePage;
import com.demoblaze.utils.SeleniumHelper;
import org.openqa.selenium.Alert;
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
        Assert.assertTrue(categories.contains("FailTest")); // Monitor
        test.log(Status.PASS, "Category list contains expected category names");
    }

    @Test
    public void categoryListChangeTest() {
        ExtentTest test = extentReports.createTest("Category list change verification");
        HomePage homePage = new HomePage(driver);
        List<String> currentProductsList = homePage.getProductsListNames();


        homePage.categoryClick("Phones");
        SeleniumHelper.waitForStringListToChange(driver, currentProductsList, homePage.getProductsLocator()); // Wait for AJAX-loaded list to be replaced in the DOM
        List<String> phonesProducts = homePage.getProductsListNames();

        homePage.categoryClick("Laptops");
        SeleniumHelper.waitForStringListToChange(driver, phonesProducts, homePage.getProductsLocator());
        List<String> laptopsProducts = homePage.getProductsListNames();

        Assert.assertNotEquals(phonesProducts, laptopsProducts);
        Assert.assertTrue(laptopsProducts.contains("Sony vaio i5"));
        Assert.assertFalse(laptopsProducts.contains("Samsung galaxy s6"));
        test.log(Status.PASS, "The product list has changed");
    }

    @Test
    public void productImagesAppearance() {
        ExtentTest test = extentReports.createTest("Product images appearance verification");
        HomePage homePage = new HomePage(driver);
        String[] categories = {"Phones", "Laptops", "Monitors"};

        List<WebElement> defaultProductImages = homePage.getProductsImages();
        Assert.assertFalse(defaultProductImages.isEmpty(), "No images found for default category list");
        Assert.assertTrue(homePage.areAllImagesLoaded(defaultProductImages), "Images are not fully loaded for default category list");

        for (String category : categories) {
            List<String> previousProductsList = homePage.getProductsListNames();

            homePage.categoryClick(category);
            SeleniumHelper.waitForStringListToChange(driver, previousProductsList, homePage.getProductsLocator());
            List<WebElement> images = homePage.getProductsImages();
            Assert.assertFalse(images.isEmpty(), "No images found for category: " + category);
            Assert.assertTrue(homePage.areAllImagesLoaded(images), "Images are not fully loaded for category: " + category);
        }
        test.log(Status.PASS, "Product images display correctly");
    }

    @Test
    public void contactFormFunctionality() {
        ExtentTest test = extentReports.createTest("Contact form functionality verification");

        HomePage homePage = new HomePage(driver);
        homePage.contactButtonClick();
        homePage.contactFormFill("example@email.com", "Jan", "example message");
        homePage.contactFormSend();

        Alert confirmationAlert = SeleniumHelper.waitForAndGetAlert(driver);

        Assert.assertEquals(confirmationAlert.getText(), "Thanks for the message!!", "Incorrect alert message displayed");

        confirmationAlert.accept();

        test.log(Status.PASS, "Contact Form functionality works as expected and displays proper message");

    }
}
