package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Giriş ekranı sınıfı.
 * Giriş ekranı ile ilgili işlemleri içerir.
 */
public class LoginScreen extends BaseScreen {
    private static final Logger logger = LoggerFactory.getLogger(LoginScreen.class);
    
    /**
     * Giriş ekranı sınıfı constructor'ı.
     * 
     * @param driver AndroidDriver nesnesi
     */
    public LoginScreen(AndroidDriver driver) {
        super(driver, "loginScreen");
    }
    
    /**
     * Kullanıcı adını girer.
     * 
     * @param username Kullanıcı adı
     * @return LoginScreen nesnesi
     */
    public LoginScreen enterUsername(String username) {
        logger.info("Kullanıcı adı giriliyor: {}", username);
        sendKeys("usernameField", username);
        return this;
    }
    
    /**
     * Şifreyi girer.
     * 
     * @param password Şifre
     * @return LoginScreen nesnesi
     */
    public LoginScreen enterPassword(String password) {
        logger.info("Şifre giriliyor");
        sendKeys("passwordField", password);
        return this;
    }
    
    /**
     * Giriş butonuna tıklar.
     * 
     * @return MainMenuScreen nesnesi
     */
    public MainMenuScreen clickLoginButton() {
        logger.info("Giriş butonuna tıklanıyor");
        click("loginButton");
        return new MainMenuScreen(driver);
    }
    
    /**
     * Giriş ekranının görüntülendiğini doğrular.
     * 
     * @return LoginScreen nesnesi
     */
    public LoginScreen verifyLoginScreenDisplayed() {
        logger.info("Giriş ekranının görüntülendiği doğrulanıyor");
        Assert.assertTrue(isDisplayed("loginScreen"), "Giriş ekranı görünmüyor");
        return this;
    }
}
