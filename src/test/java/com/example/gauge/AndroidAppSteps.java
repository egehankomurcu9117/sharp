package com.example.gauge;

import com.example.appium.*;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.AfterScenario;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Android uygulaması için Gauge adım implementasyonları.
 * Page Object Model (POM) desenini kullanarak ekran sınıflarını çağırır.
 */
public class AndroidAppSteps extends AppiumBase {
    private static final Logger logger = LoggerFactory.getLogger(AndroidAppSteps.class);
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

    @BeforeScenario
    public void setUp() {
        logger.info("Android uygulama senaryosu başlatılıyor");
        // APK dosyasını kullanarak driver'ı başlat
        driver = initializeDriver(APP_PACKAGE, APP_ACTIVITY, APK_FILE_NAME);

        // Ekran nesnelerini oluştur
        serverAddressScreen = new ServerAddressScreen(driver);
        loginScreen = new LoginScreen(driver);
        mainMenuScreen = new MainMenuScreen(driver);
        settingsScreen = new SettingsScreen(driver);
    }

    @Step("Uygulamayı başlat")
    public void launchApp() {
        logger.info("Uygulama başlatılıyor");
        // Uygulama zaten BeforeScenario'da başlatıldı
    }

    @Step("Uygulamanın başarıyla açıldığını doğrula")
    public void verifyAppLaunched() {
        logger.info("Uygulamanın başarıyla açıldığı doğrulanıyor");
        serverAddressScreen.verifyAppLaunched();
    }

    @Step("Sunucu adresi olarak <serverAddress> gir")
    public void enterServerAddress(String serverAddress) {
        logger.info("Sunucu adresi giriliyor: {}", serverAddress);
        serverAddressScreen.enterServerAddress(serverAddress);
    }

    @Step("OK butonuna tıkla")
    public void clickOkButton() {
        logger.info("OK butonuna tıklanıyor");
        loginScreen = serverAddressScreen.clickOkButton();
    }

    @Step("CANCEL butonuna tıkla")
    public void clickCancelButton() {
        logger.info("CANCEL butonuna tıklanıyor");
        serverAddressScreen.clickCancelButton();
    }

    @Step("Kullanıcı adı olarak <username> gir")
    public void enterUsername(String username) {
        logger.info("Kullanıcı adı giriliyor: {}", username);
        loginScreen.enterUsername(username);
    }

    @Step("Şifre olarak <password> gir")
    public void enterPassword(String password) {
        logger.info("Şifre giriliyor: {}", password);
        loginScreen.enterPassword(password);
    }

    @Step("Giriş butonuna tıkla")
    public void clickLoginButton() {
        logger.info("Giriş butonuna tıklanıyor");
        mainMenuScreen = loginScreen.clickLoginButton();
    }

    @Step("Ana menüye git")
    public void goToMainMenu() {
        logger.info("Ana menüye gidiliyor");
        mainMenuScreen.goToMainMenu();
    }

    @Step("Menü öğelerinin görünür olduğunu doğrula")
    public void verifyMenuItemsVisible() {
        logger.info("Menü öğelerinin görünür olduğu doğrulanıyor");
        mainMenuScreen.verifyMenuItemsVisible();
    }

    @Step("<menuItem> menü öğesine tıkla")
    public void clickMenuItem(String menuItem) {
        logger.info("Menü öğesine tıklanıyor: {}", menuItem);
        settingsScreen = mainMenuScreen.clickMenuItem(menuItem);
    }

    @Step("Ayarlar sayfasının açıldığını doğrula")
    public void verifySettingsPageOpened() {
        logger.info("Ayarlar sayfasının açıldığı doğrulanıyor");
        settingsScreen.verifySettingsPageOpened();
    }

    @Step("<buttonName> butonuna tıkla")
    public void clickButton(String buttonName) {
        logger.info("Butona tıklanıyor: {}", buttonName);
        if (buttonName.equalsIgnoreCase("logout")) {
            loginScreen = settingsScreen.clickLogoutButton();
        } else if (buttonName.equalsIgnoreCase("back")) {
            mainMenuScreen = settingsScreen.clickBackButton();
        } else {
            settingsScreen.clickButton(buttonName);
        }
    }

    @Step("Uygulamanın kapandığını doğrula")
    public void verifyAppClosed() {
        logger.info("Uygulamanın kapandığı doğrulanıyor");
        loginScreen.verifyLoginScreenDisplayed();
    }

    @AfterScenario
    public void tearDown() {
        logger.info("Android uygulama senaryosu sonlandırılıyor");
        quitDriver();
    }
}
