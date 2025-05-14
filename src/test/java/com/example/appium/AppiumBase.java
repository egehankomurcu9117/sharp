package com.example.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.time.Duration;

/**
 * Appium test sınıfları için temel sınıf.
 * Android uygulamaları için Appium sürücüsünü başlatır ve yönetir.
 */
public class AppiumBase {
    private static final Logger logger = LoggerFactory.getLogger(AppiumBase.class);
    protected AppiumDriver driver;

    // APK dosyasının varsayılan yolu
    private static final String DEFAULT_APK_PATH = "/Users/egehankomurcu/Downloads";

    /**
     * Appium sürücüsünü başlatır.
     *
     * @param appPackage Uygulamanın paket adı
     * @param appActivity Başlatılacak aktivite
     * @return Başlatılan AndroidDriver nesnesi
     */
    protected AndroidDriver initializeDriver(String appPackage, String appActivity) {
        return initializeDriver(appPackage, appActivity, null);
    }

    /**
     * Appium sürücüsünü başlatır ve belirtilen APK dosyasını kullanır.
     *
     * @param appPackage Uygulamanın paket adı
     * @param appActivity Başlatılacak aktivite
     * @param apkFileName APK dosyasının adı (null ise paket ve aktivite kullanılır)
     * @return Başlatılan AndroidDriver nesnesi
     */
    protected AndroidDriver initializeDriver(String appPackage, String appActivity, String apkFileName) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Android Device");
            capabilities.setCapability("automationName", "UiAutomator2");

            // Eğer APK dosyası belirtilmişse, onu kullan
            if (apkFileName != null && !apkFileName.isEmpty()) {
                File apkFile = new File(DEFAULT_APK_PATH, apkFileName);
                if (apkFile.exists()) {
                    capabilities.setCapability("app", apkFile.getAbsolutePath());
                    logger.info("APK dosyası kullanılıyor: {}", apkFile.getAbsolutePath());
                } else {
                    logger.warn("APK dosyası bulunamadı: {}, paket ve aktivite kullanılacak", apkFile.getAbsolutePath());
                    capabilities.setCapability("appPackage", appPackage);
                    capabilities.setCapability("appActivity", appActivity);
                }
            } else {
                // APK belirtilmemişse paket ve aktivite kullan
                capabilities.setCapability("appPackage", appPackage);
                capabilities.setCapability("appActivity", appActivity);
            }

            capabilities.setCapability("noReset", true);

            URL appiumServerURL = new URL("http://127.0.0.1:4723/wd/hub");
            AndroidDriver driver = new AndroidDriver(appiumServerURL, capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            logger.info("Appium driver başarıyla başlatıldı");
            return driver;
        } catch (Exception e) {
            logger.error("Appium driver başlatılırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Appium driver başlatılamadı", e);
        }
    }

    /**
     * Appium sürücüsünü kapatır.
     */
    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
            logger.info("Appium driver kapatıldı");
        }
    }
}
