package com.sure.orangehrm.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElementHelper {

    private ElementHelper() {
        // prevent instantiation
    }

    public static void click(WebDriver driver, By locator) {
        WaitHelper.waitForElementClickable(driver, locator).click();
    }

    public static void type(WebDriver driver, By locator, String value) {
        WebElement element = WaitHelper.waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(value);
    }

    public static String getText(WebDriver driver, By locator) {
        return WaitHelper.waitForElementVisible(driver, locator).getText();
    }

    public static boolean isDisplayed(WebDriver driver, By locator) {
        try {
            return WaitHelper.waitForElementVisible(driver, locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static List<WebElement> getVisibleElements(WebDriver driver, By locator) {
        return new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

}
