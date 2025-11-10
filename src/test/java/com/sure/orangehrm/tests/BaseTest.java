
package com.sure.orangehrm.tests;

import com.sure.orangehrm.driver.DriverFactory;
import com.sure.orangehrm.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        // Load config.properties once
        try {
            ConfigReader.load("config.properties");
            System.out.println("[BaseTest] Config file loaded successfully.");
        } catch (Exception e) {
            throw new RuntimeException("[BaseTest] Failed to load config.properties", e);
        }
    }


    protected void initDriverForUI() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
            String baseUrl = ConfigReader.get("base.url");
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new RuntimeException("[BaseTest] Missing key 'base.url' in config.properties");
            }
            driver.get(baseUrl);
            System.out.println("[BaseTest] Navigated to: " + baseUrl);
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            System.out.println("[BaseTest] WebDriver closed successfully");
            driver = null;
        }
    }
}
