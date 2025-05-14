package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Ana menü ekranı sınıfı.
 * Ana menü ekranı ile ilgili işlemleri içerir.
 */
public class MainMenuScreen extends BaseScreen {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuScreen.class);
    
    /**
     * Ana menü ekranı sınıfı constructor'ı.
     * 
     * @param driver AndroidDriver nesnesi
     */
    public MainMenuScreen(AndroidDriver driver) {
        super(driver, "mainMenu");
    }
    
    /**
     * Ana menüye gider.
     * 
     * @return MainMenuScreen nesnesi
     */
    public MainMenuScreen goToMainMenu() {
        logger.info("Ana menüye gidiliyor");
        click("menuButton");
        return this;
    }
    
    /**
     * Menü öğelerinin görünür olduğunu doğrular.
     * 
     * @return MainMenuScreen nesnesi
     */
    public MainMenuScreen verifyMenuItemsVisible() {
        logger.info("Menü öğelerinin görünür olduğu doğrulanıyor");
        Assert.assertTrue(isDisplayed("settingsMenuItem"), "Ayarlar menü öğesi görünmüyor");
        return this;
    }
    
    /**
     * Belirtilen menü öğesine tıklar.
     * 
     * @param menuItem Menü öğesi adı
     * @return SettingsScreen nesnesi
     */
    public SettingsScreen clickMenuItem(String menuItem) {
        logger.info("Menü öğesine tıklanıyor: {}", menuItem);
        String elementName = menuItem.toLowerCase() + "MenuItem";
        click(elementName);
        return new SettingsScreen(driver);
    }
}
