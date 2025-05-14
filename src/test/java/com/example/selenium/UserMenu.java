package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Kullanıcı menüsü sınıfı.
 * Kullanıcı menüsü ile ilgili işlemleri içerir.
 */
public class UserMenu extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(UserMenu.class);
    
    /**
     * Kullanıcı menüsü sınıfı constructor'ı.
     * 
     * @param driver WebDriver nesnesi
     */
    public UserMenu(WebDriver driver) {
        super(driver, "userMenu");
    }
    
    /**
     * Kullanıcı menüsünü açar.
     * 
     * @return UserMenu nesnesi
     */
    public UserMenu open() {
        logger.info("Kullanıcı menüsü açılıyor");
        click("userMenu");
        return this;
    }
    
    /**
     * Belirtilen seçeneğe tıklar.
     * 
     * @param optionName Seçenek adı
     * @return UserMenu nesnesi
     */
    public UserMenu clickOption(String optionName) {
        logger.info("Seçeneğe tıklanıyor: {}", optionName);
        String elementName = optionName.toLowerCase() + "Option";
        click(elementName);
        return this;
    }
    
    /**
     * Çıkış işleminin başarılı olduğunu doğrular.
     * 
     * @return HomePage nesnesi
     */
    public HomePage verifyLogoutSuccess() {
        logger.info("Çıkış işleminin başarılı olduğu doğrulanıyor");
        Assert.assertTrue(isDisplayed("loginLink"), "Giriş linki görünmüyor");
        return new HomePage(driver);
    }
}
