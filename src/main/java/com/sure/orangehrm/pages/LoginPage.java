package com.sure.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.sure.orangehrm.utils.WaitHelper;

public class LoginPage {
    private WebDriver driver;
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(com.sure.orangehrm.utils.ConfigReader.get("base.url") + "web/index.php/auth/login");
    }

    public void login(String user, String pass) {
        WaitHelper.waitForElementClickable(driver, username).sendKeys(user);
        WaitHelper.waitForElementVisible(driver, password).sendKeys(pass);
        WaitHelper.waitForElementClickable(driver, loginBtn).click();
    }
}
