package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Ana sayfa sınıfı.
 * Ana sayfa ile ilgili işlemleri içerir.
 */
public class HomePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    
    private static final String BASE_URL = "https://example.com";
    
    /**
     * Ana sayfa sınıfı constructor'ı.
     * 
     * @param driver WebDriver nesnesi
     */
    public HomePage(WebDriver driver) {
        super(driver, "homePage");
    }
    
    /**
     * Ana sayfayı açar.
     * 
     * @return HomePage nesnesi
     */
    public HomePage open() {
        logger.info("Ana sayfa açılıyor");
        navigateTo(BASE_URL);
        return this;
    }
    
    /**
     * Ana sayfanın yüklendiğini doğrular.
     * 
     * @return HomePage nesnesi
     */
    public HomePage verifyPageLoaded() {
        logger.info("Ana sayfanın yüklendiği doğrulanıyor");
        Assert.assertTrue(isDisplayed("logo"), "Logo görünmüyor");
        
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Example Domain", "Sayfa başlığı beklendiği gibi değil");
        
        return this;
    }
    
    /**
     * Giriş sayfasına gider.
     * 
     * @return LoginPage nesnesi
     */
    public LoginPage goToLoginPage() {
        logger.info("Giriş sayfasına gidiliyor");
        navigateTo(BASE_URL + "/login");
        return new LoginPage(driver);
    }
}
