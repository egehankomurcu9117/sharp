package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Giriş sayfası sınıfı.
 * Giriş sayfası ile ilgili işlemleri içerir.
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    
    /**
     * Giriş sayfası sınıfı constructor'ı.
     * 
     * @param driver WebDriver nesnesi
     */
    public LoginPage(WebDriver driver) {
        super(driver, "loginPage");
    }
    
    /**
     * Kullanıcı adını girer.
     * 
     * @param username Kullanıcı adı
     * @return LoginPage nesnesi
     */
    public LoginPage enterUsername(String username) {
        logger.info("Kullanıcı adı giriliyor: {}", username);
        sendKeys("username", username);
        return this;
    }
    
    /**
     * Şifreyi girer.
     * 
     * @param password Şifre
     * @return LoginPage nesnesi
     */
    public LoginPage enterPassword(String password) {
        logger.info("Şifre giriliyor");
        sendKeys("password", password);
        return this;
    }
    
    /**
     * Giriş butonuna tıklar.
     * 
     * @return HomePage nesnesi
     */
    public HomePage clickLoginButton() {
        logger.info("Giriş butonuna tıklanıyor");
        click("loginButton");
        return new HomePage(driver);
    }
    
    /**
     * Başarılı giriş mesajını doğrular.
     * 
     * @return LoginPage nesnesi
     */
    public LoginPage verifyLoginSuccess() {
        logger.info("Başarılı giriş mesajı doğrulanıyor");
        Assert.assertTrue(isDisplayed("loginSuccess"), "Başarılı giriş mesajı görünmüyor");
        Assert.assertEquals(getText("loginSuccess"), "Giriş başarılı", "Başarılı giriş mesajı beklendiği gibi değil");
        return this;
    }
}
