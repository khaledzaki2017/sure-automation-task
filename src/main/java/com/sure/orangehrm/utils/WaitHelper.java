package com.sure.orangehrm.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class WaitHelper {

    private static final int DEFAULT_TIMEOUT = ConfigReader.getInt("explicit.wait", 20);

    private WaitHelper() {
        // prevent instantiation
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForTextPresent(WebDriver driver, By locator, String text) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static void waitForPageTitle(WebDriver driver, String title) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.titleContains(title));
    }
}
