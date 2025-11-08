package com.sure.orangehrm.tests;

import com.sure.orangehrm.driver.DriverFactory;
import com.sure.orangehrm.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        try {
            String headless = ConfigReader.get("headless");
            System.setProperty("headless", headless);
            System.out.println("[BaseTest] Running in headless mode: " + headless);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set headless mode", e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        System.out.println("[BaseTest] WebDriver started successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            System.out.println("[BaseTest] WebDriver closed successfully");
        }
    }
}
