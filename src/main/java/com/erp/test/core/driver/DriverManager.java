package com.erp.test.core.driver;

import com.erp.test.core.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * WebDriver'ı yöneten sınıf.
 * Thread-safe olması için ThreadLocal kullanılmıştır.
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Utility class, no instances
    }

    /**
     * Mevcut thread için WebDriver'ı döndürür.
     * Eğer driver henüz oluşturulmamışsa, yeni bir driver oluşturur.
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initDriver();
        }
        return driverThreadLocal.get();
    }

    /**
     * Yeni bir WebDriver oluşturur ve yapılandırır.
     */
    public static void initDriver() {
        WebDriver driver;
        String browser = ConfigManager.getInstance().getBrowser().toLowerCase();
        boolean headless = ConfigManager.getInstance().isHeadless();
        
        logger.info("Initializing WebDriver for browser: {}, headless: {}", browser, headless);
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            case "safari":
                driver = new SafariDriver();
                break;
                
            default:
                logger.error("Unsupported browser: {}", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        
        // Configure timeouts
        int implicitWait = ConfigManager.getInstance().getImplicitWait();
        int pageLoadTimeout = ConfigManager.getInstance().getPageLoadTimeout();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        
        if (!headless) {
            driver.manage().window().maximize();
        }
        
        driverThreadLocal.set(driver);
        logger.info("WebDriver initialized successfully");
    }

    /**
     * Mevcut WebDriver'ı kapatır ve ThreadLocal'dan temizler.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
