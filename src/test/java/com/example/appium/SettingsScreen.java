package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Ayarlar ekranı sınıfı.
 * Ayarlar ekranı ile ilgili işlemleri içerir.
 */
public class SettingsScreen extends BaseScreen {
    private static final Logger logger = LoggerFactory.getLogger(SettingsScreen.class);
    
    /**
     * Ayarlar ekranı sınıfı constructor'ı.
     * 
     * @param driver AndroidDriver nesnesi
     */
    public SettingsScreen(AndroidDriver driver) {
        super(driver, "settingsScreen");
    }
    
    /**
     * Ayarlar sayfasının açıldığını doğrular.
     * 
     * @return SettingsScreen nesnesi
     */
    public SettingsScreen verifySettingsPageOpened() {
        logger.info("Ayarlar sayfasının açıldığı doğrulanıyor");
        Assert.assertTrue(isDisplayed("settingsTitle"), "Ayarlar başlığı görünmüyor");
        Assert.assertEquals(getText("settingsTitle"), "Ayarlar", "Ayarlar başlığı beklendiği gibi değil");
        return this;
    }
    
    /**
     * Belirtilen butona tıklar.
     * 
     * @param buttonName Buton adı
     * @return SettingsScreen nesnesi
     */
    public SettingsScreen clickButton(String buttonName) {
        logger.info("Butona tıklanıyor: {}", buttonName);
        String elementName = buttonName.toLowerCase() + "Button";
        click(elementName);
        return this;
    }
    
    /**
     * Çıkış butonuna tıklar.
     * 
     * @return LoginScreen nesnesi
     */
    public LoginScreen clickLogoutButton() {
        logger.info("Çıkış butonuna tıklanıyor");
        click("logoutButton");
        return new LoginScreen(driver);
    }
    
    /**
     * Geri butonuna tıklar.
     * 
     * @return MainMenuScreen nesnesi
     */
    public MainMenuScreen clickBackButton() {
        logger.info("Geri butonuna tıklanıyor");
        click("backButton");
        return new MainMenuScreen(driver);
    }
}
