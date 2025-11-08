package com.sure.orangehrm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class DashboardPage {
    private WebDriver driver;
    private By dashboardTitle = By.xpath("//h6[text()='Dashboard']");

    public DashboardPage(WebDriver driver) { this.driver = driver; }

    public boolean isAtDashboard() {
        try {
            return driver.findElement(dashboardTitle).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
