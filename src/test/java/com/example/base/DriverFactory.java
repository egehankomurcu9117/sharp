package com.example.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;

/**
 * AppiumDriver'ı başlatmak için fabrika sınıfı.
 * Bu sınıf, Android ve iOS için AppiumDriver nesnelerini oluşturur.
 */
public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    
    // APK dosyasının varsayılan yolu
    private static final String DEFAULT_APK_PATH = "/Users/egehankomurcu/Downloads";
    
    /**
     * Android için AppiumDriver nesnesini oluşturur.
     * 
     * @param appPackage Uygulama paketi
     * @param appActivity Uygulama aktivitesi
     * @param apkFileName APK dosyasının adı (null ise paket ve aktivite kullanılır)
     * @return AndroidDriver nesnesi
     */
    public static AndroidDriver createAndroidDriver(String appPackage, String appActivity, String apkFileName) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Android Device");
            capabilities.setCapability("automationName", "UiAutomator2");
            
            // Eğer APK dosyası belirtilmişse, onu kullan
            if (apkFileName != null && !apkFileName.isEmpty()) {
                String apkPath = DEFAULT_APK_PATH + "/" + apkFileName;
                capabilities.setCapability("app", apkPath);
                logger.info("APK dosyası kullanılıyor: {}", apkPath);
            } else {
                // APK belirtilmemişse paket ve aktivite kullan
                capabilities.setCapability("appPackage", appPackage);
                capabilities.setCapability("appActivity", appActivity);
            }
            
            capabilities.setCapability("noReset", true);
            
            URL appiumServerURL = new URL("http://127.0.0.1:4723/wd/hub");
            AndroidDriver driver = new AndroidDriver(appiumServerURL, capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            logger.info("Android driver başarıyla başlatıldı");
            return driver;
        } catch (Exception e) {
            logger.error("Android driver başlatılırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Android driver başlatılırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * iOS için AppiumDriver nesnesini oluşturur.
     * 
     * @param bundleId Uygulama bundle ID'si
     * @return IOSDriver nesnesi
     */
    public static IOSDriver createIOSDriver(String bundleId) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone");
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability("bundleId", bundleId);
            capabilities.setCapability("noReset", true);
            
            URL appiumServerURL = new URL("http://127.0.0.1:4723/wd/hub");
            IOSDriver driver = new IOSDriver(appiumServerURL, capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            logger.info("iOS driver başarıyla başlatıldı");
            return driver;
        } catch (Exception e) {
            logger.error("iOS driver başlatılırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("iOS driver başlatılırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Platform türüne göre AppiumDriver nesnesini oluşturur.
     * 
     * @param platform Platform türü (android veya ios)
     * @return AppiumDriver nesnesi
     */
    public static AppiumDriver createDriver(String platform) {
        if (platform.equalsIgnoreCase("android")) {
            return createAndroidDriver("com.vektortelekom.android.vservice.test", "com.vektortelekom.android.vservice.test.MainActivity", null);
        } else if (platform.equalsIgnoreCase("ios")) {
            return createIOSDriver("com.vektortelekom.ios.vservice.test");
        } else {
            throw new IllegalArgumentException("Geçersiz platform türü: " + platform);
        }
    }
}
