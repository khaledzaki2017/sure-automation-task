package com.sure.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        driver.findElement(pimMenu).click();
    }

    public void searchByNameOrId(String text) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(text);
        driver.findElement(searchBtn).click();
    }

    public boolean isEmployeePresent() {
        return driver.findElement(firstResult).isDisplayed();
    }
}
