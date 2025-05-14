package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Selenium test sınıfları için temel sınıf.
 * Web tarayıcı sürücüsünü başlatır ve yönetir.
 */
public class SeleniumBase {
    private static final Logger logger = LoggerFactory.getLogger(SeleniumBase.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Chrome tarayıcı sürücüsünü başlatır.
     * 
     * @return Başlatılan WebDriver nesnesi
     */
    protected WebDriver initializeChromeDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            
            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            logger.info("Chrome driver başarıyla başlatıldı");
            return driver;
        } catch (Exception e) {
            logger.error("Chrome driver başlatılırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Chrome driver başlatılamadı", e);
        }
    }

    /**
     * Firefox tarayıcı sürücüsünü başlatır.
     * 
     * @return Başlatılan WebDriver nesnesi
     */
    protected WebDriver initializeFirefoxDriver() {
        try {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            
            WebDriver driver = new FirefoxDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            logger.info("Firefox driver başarıyla başlatıldı");
            return driver;
        } catch (Exception e) {
            logger.error("Firefox driver başlatılırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Firefox driver başlatılamadı", e);
        }
    }

    /**
     * Web tarayıcı sürücüsünü kapatır.
     */
    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
            logger.info("Web driver kapatıldı");
        }
    }
}
