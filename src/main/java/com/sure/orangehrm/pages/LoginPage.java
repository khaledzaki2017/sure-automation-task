package com.sure.orangehrm.pages;

import com.sure.orangehrm.utils.ConfigReader;
import com.sure.orangehrm.utils.WaitHelper;
import com.sure.orangehrm.api.ApiClient;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private String sessionCookie;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ================= Locators =================
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By errorMessage  = By.cssSelector(".oxd-alert-content-text");

    // ================= Actions =================

    public void open() {
        String url = ConfigReader.get("base.url") + "web/index.php/auth/login";
        driver.get(url);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        Cookie session = driver.manage().getCookieNamed("orangehrm");
        if (session != null) {
            sessionCookie = session.getName() + "=" + session.getValue();
            System.out.println("[LoginPage] Session cookie set: " + sessionCookie);
        } else {
            System.out.println("[LoginPage] No session cookie found after login");
        }
    }
    public void setSessionCookie(String cookieValue) {
        Cookie cookie = new Cookie("orangehrm", cookieValue);
        driver.manage().addCookie(cookie);
        System.out.println("[LoginPage] Session cookie set: " + cookieValue);
    }
    public String getLoginError() {
        try {
            return WaitHelper.waitForElementVisible(driver, errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getSessionCookie() {
        if (sessionCookie == null) {
            throw new IllegalStateException("Session cookie is null");
        }
        return sessionCookie;
    }
}
