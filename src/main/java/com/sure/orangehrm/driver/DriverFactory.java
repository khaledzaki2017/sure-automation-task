package com.sure.orangehrm.driver;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;


public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

//    public static void setDriver(WebDriver driver) {
//        DRIVER.set(driver);
//    }

    public static WebDriver getDriver() {
        if( DRIVER.get()==null){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options=new ChromeOptions();
            options.addArguments("--lang=en-US");
            options.addArguments("--accept-lang=en-US");
            options.addArguments("--disable-features=TranslateUI");
            options.addArguments("--disable-translate");

            // Clear cache and cookies
            options.addArguments("--disable-application-cache");
            options.addArguments("--disable-cache");

            if (isHeadless()) options.addArguments("--headless=new");
            options.addArguments("--start-maximized");
            DRIVER.set(new ChromeDriver(options));
        }
        return DRIVER.get();
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
