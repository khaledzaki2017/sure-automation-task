package com.sure.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.sure.orangehrm.utils.WaitHelper;
import org.openqa.selenium.WebElement;
public class PIMPage {
    private WebDriver driver;

    private By pimMenu = By.xpath("//a[contains(@href,'pim')]");
    private By searchInput = By.cssSelector("input[placeholder='Type for hints...']");
    private By searchBtn = By.xpath("//button[contains(.,'Search')]");
    private By firstResult = By.cssSelector(".oxd-table-body .oxd-table-card");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        WebElement pim = WaitHelper.waitForElementClickable(driver, pimMenu);
        pim.click();
    }

    public void searchByNameOrId(String text) {
        WebElement input = WaitHelper.waitForElementVisible(driver, searchInput);
        input.clear();
        input.sendKeys(text);
        WaitHelper.waitForElementClickable(driver, searchBtn).click();
    }

    public boolean isEmployeePresent() {
        try {
            return WaitHelper.waitForElementVisible(driver, firstResult).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
