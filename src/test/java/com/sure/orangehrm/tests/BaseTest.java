package com.sure.orangehrm.tests;

import com.sure.orangehrm.driver.DriverFactory;
import com.sure.orangehrm.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        // Load config.properties once before any test class runs
        try {
            ConfigReader.load("config.properties");
            System.out.println("[BaseTest] Config file loaded successfully.");
        } catch (Exception e) {
            throw new RuntimeException("[BaseTest] Failed to load config.properties", e);
        }
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        try {
            String headless = ConfigReader.get("headless");
            if (headless == null) headless = "false";
            System.setProperty("headless", headless);
            System.out.println("[BaseTest] Headless mode: " + headless);
        } catch (Exception e) {
            throw new RuntimeException("[BaseTest] Failed to set headless mode", e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();

        String baseUrl = ConfigReader.get("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("[BaseTest] Missing key 'base.url' in config.properties");
        }

        driver.get(baseUrl);
        System.out.println("[BaseTest] Navigated to: " + baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            System.out.println("[BaseTest] WebDriver closed successfully");
        }
    }
}
