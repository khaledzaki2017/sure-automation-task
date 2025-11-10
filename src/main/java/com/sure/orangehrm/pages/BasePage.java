package com.sure.orangehrm.pages;

import com.sure.orangehrm.driver.DriverFactory;
import com.sure.orangehrm.utils.ElementHelper;
import com.sure.orangehrm.utils.WaitHelper;
import org.openqa.selenium.*;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }


    // ========= Common Reusable Actions ========= //

    protected void click(By locator) {
        ElementHelper.click(driver, locator);
    }

    protected void type(By locator, String value) {
        ElementHelper.type(driver, locator, value);
    }

    protected String getText(By locator) {
        return ElementHelper.getText(driver, locator);
    }

    protected boolean isDisplayed(By locator) {
        return ElementHelper.isDisplayed(driver, locator);
    }

    protected List<WebElement> getVisibleElements(By locator) {
        return ElementHelper.getVisibleElements(driver, locator);
    }

    protected void waitForTitle(String title) {
        WaitHelper.waitForPageTitle(driver, title);
    }
}
