package com.sure.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    private final By dashboardTitle = By.cssSelector("h6.oxd-text--h6");

    public boolean isAtDashboard() {
        return isDisplayed(dashboardTitle);
    }
}
