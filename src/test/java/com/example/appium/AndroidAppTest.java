package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Android masaüstü uygulaması için test sınıfı.
 * Temel işlevleri test eder.
 * OOP kurallarına uygun, JSON formatında locator'ları kullanan yapı.
 */
public class AndroidAppTest extends AppiumBase {
    private static final Logger logger = LoggerFactory.getLogger(AndroidAppTest.class);
    private AndroidDriver driver;

    // Uygulama bilgileri - Logo WMS uygulaması için
    private static final String APP_PACKAGE = "com.logo.wms";
    private static final String APP_ACTIVITY = "com.logo.wms.features.splash.SplashActivity";

    // APK dosyasının adı - indirilen APK dosyasının adını buraya yazın
    private static final String APK_FILE_NAME = "Logo WMS_1.61.00.00_APKPure.apk"; // Gerçek APK dosya adı

    // Ekran nesneleri
    private ServerAddressScreen serverAddressScreen;
    private LoginScreen loginScreen;
    private MainMenuScreen mainMenuScreen;
    private SettingsScreen settingsScreen;

    @BeforeClass
    public void setUp() {
        logger.info("Android uygulama testi başlatılıyor");
        // APK dosyasını kullanarak driver'ı başlat
        driver = initializeDriver(APP_PACKAGE, APP_ACTIVITY, APK_FILE_NAME);

        // Ekran nesnelerini oluştur
        serverAddressScreen = new ServerAddressScreen(driver);
        loginScreen = new LoginScreen(driver);
        mainMenuScreen = new MainMenuScreen(driver);
        settingsScreen = new SettingsScreen(driver);
    }

    @Test(priority = 1)
    public void testAppLaunch() {
        logger.info("Uygulama başlatma testi çalıştırılıyor");

        // Uygulamanın başarıyla başlatıldığını doğrula
        serverAddressScreen.verifyAppLaunched();

        // Sunucu adresini gir ve OK butonuna tıkla
        serverAddressScreen
            .enterServerAddress("https://wms-server.logo.com.tr")
            .clickOkButton();
    }

    @Test(priority = 2, dependsOnMethods = "testAppLaunch")
    public void testLogin() {
        logger.info("Giriş yapma testi çalıştırılıyor");

        // Kullanıcı adı ve şifre gir, giriş butonuna tıkla
        loginScreen
            .enterUsername("testuser")
            .enterPassword("password123")
            .clickLoginButton();
    }

    @Test(priority = 3, dependsOnMethods = "testLogin")
    public void testMainMenu() {
        logger.info("Ana menü testi çalıştırılıyor");

        // Ana menüye git ve menü öğelerinin görünür olduğunu doğrula
        mainMenuScreen
            .goToMainMenu()
            .verifyMenuItemsVisible();

        // Ayarlar menü öğesine tıkla ve ayarlar sayfasının açıldığını doğrula
        mainMenuScreen
            .clickMenuItem("settings")
            .verifySettingsPageOpened();
    }

    @Test(priority = 4, dependsOnMethods = "testMainMenu")
    public void testLogout() {
        logger.info("Çıkış yapma testi çalıştırılıyor");

        // Çıkış butonuna tıkla
        settingsScreen.clickLogoutButton();

        // Çıkış yapıldığını doğrula
        loginScreen.verifyLoginScreenDisplayed();
    }

    @AfterClass
    public void tearDown() {
        logger.info("Android uygulama testi sonlandırılıyor");
        quitDriver();
    }
}
