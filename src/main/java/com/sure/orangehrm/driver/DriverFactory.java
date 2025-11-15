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

        // Required for all environments
        options.addArguments("--lang=en-US");
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-translate");
        options.addArguments("--disable-application-cache");
        options.addArguments("--disable-cache");

        // REQUIRED for Chrome 115+ (fixes SessionNotCreated)
        options.addArguments("--remote-allow-origins=*");

        if (isHeadless()) {
            System.out.println("[Headless mode] Tests are running in headless mode");

            // REQUIRED for Jenkins
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-software-rasterizer");
            options.addArguments("--disable-setuid-sandbox");

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
