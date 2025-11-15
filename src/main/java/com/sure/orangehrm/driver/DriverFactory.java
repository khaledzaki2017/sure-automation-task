package com.sure.orangehrm.driver;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = createChromeOptions();
            DRIVER.set(new ChromeDriver(options));
        }
        return DRIVER.get();
    }

    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // General settings (common for headless + normal)
        options.addArguments("--lang=en-US");
        options.addArguments("--accept-lang=en-US");
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-translate");
        options.addArguments("--disable-application-cache");
        options.addArguments("--disable-cache");

        if (isHeadless()) {
            System.out.println("[Headless mode] Tests are running in headless mode");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }

        return options;
    }

    public static void quitDriver() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }

    private static boolean isHeadless() {
        return "true".equalsIgnoreCase(System.getProperty("headless", "false"));
    }
}
