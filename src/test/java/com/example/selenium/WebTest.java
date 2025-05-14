package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Web uygulaması için test sınıfı.
 * Temel işlevleri test eder.
 */
public class WebTest extends SeleniumBase {
    private static final Logger logger = LoggerFactory.getLogger(WebTest.class);
    
    // Web sitesi URL'si - gerçek web sitenize göre değiştirin
    private static final String BASE_URL = "https://example.com";

    @BeforeClass
    public void setUp() {
        logger.info("Web testi başlatılıyor");
        driver = initializeChromeDriver();
    }

    @Test(priority = 1)
    public void testWebsiteLoad() {
        logger.info("Web sitesi yükleme testi çalıştırılıyor");
        // Web sitesini aç
        driver.get(BASE_URL);

        // Ana sayfanın yüklendiğini doğrula
        WebElement logo = driver.findElement(By.id("logo"));
        Assert.assertTrue(logo.isDisplayed(), "Logo görünmüyor");
        
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Example Domain", "Sayfa başlığı beklendiği gibi değil");
    }

    @Test(priority = 2)
    public void testLogin() {
        logger.info("Giriş yapma testi çalıştırılıyor");
        // Giriş sayfasına git
        driver.get(BASE_URL + "/login");

        // Kullanıcı adı ve şifre gir
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login_button"));

        usernameField.sendKeys("webuser");
        passwordField.sendKeys("webpass123");
        loginButton.click();

        // Başarılı giriş mesajını doğrula
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_success")));
        Assert.assertTrue(successMessage.isDisplayed(), "Başarılı giriş mesajı görünmüyor");
        Assert.assertEquals(successMessage.getText(), "Giriş başarılı", "Başarılı giriş mesajı beklendiği gibi değil");
    }

    @Test(priority = 3)
    public void testSearch() {
        logger.info("Arama testi çalıştırılıyor");
        // Arama kutusuna metin gir
        WebElement searchBox = driver.findElement(By.id("search_box"));
        WebElement searchButton = driver.findElement(By.id("search_button"));

        searchBox.sendKeys("Android");
        searchButton.click();

        // Arama sonuçlarının gösterildiğini doğrula
        WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_results")));
        Assert.assertTrue(searchResults.isDisplayed(), "Arama sonuçları görünmüyor");

        // En az bir sonuç olduğunu doğrula
        List<WebElement> resultItems = driver.findElements(By.cssSelector(".result-item"));
        Assert.assertTrue(resultItems.size() > 0, "Arama sonuçları bulunamadı");
    }

    @Test(priority = 4)
    public void testLogout() {
        logger.info("Çıkış yapma testi çalıştırılıyor");
        // Kullanıcı menüsünü aç
        WebElement userMenu = driver.findElement(By.id("user_menu"));
        userMenu.click();

        // Çıkış seçeneğine tıkla
        WebElement logoutOption = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_option")));
        logoutOption.click();

        // Çıkış yapıldığını doğrula
        WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_link")));
        Assert.assertTrue(loginLink.isDisplayed(), "Giriş linki görünmüyor");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Web testi sonlandırılıyor");
        quitDriver();
    }
}
