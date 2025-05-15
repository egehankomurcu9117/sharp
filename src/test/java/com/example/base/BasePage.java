package com.example.base;

import com.example.locators.ElementLocators;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Tüm sayfa/ekran sınıfları için temel sınıf.
 * Bu sınıf, tüm sayfa/ekran sınıflarının ortak işlevlerini içerir.
 */
public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Temel sayfa/ekran sınıfı constructor'ı.
     * 
     * @param driver AppiumDriver nesnesi
     */
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * Belirtilen key için WebElement nesnesini bulur.
     * 
     * @param key Lokator anahtarı
     * @return WebElement nesnesi
     */
    public WebElement findElementByKey(String key) {
        By by = ElementLocators.getBy(key);
        return driver.findElement(by);
    }
    
    /**
     * Belirtilen key için WebElement listesini bulur.
     * 
     * @param key Lokator anahtarı
     * @return WebElement listesi
     */
    public List<WebElement> findElementsByKey(String key) {
        By by = ElementLocators.getBy(key);
        return driver.findElements(by);
    }
    
    /**
     * Belirtilen key için WebElement'in görünür olmasını bekler.
     * 
     * @param key Lokator anahtarı
     * @return WebElement nesnesi
     */
    public WebElement waitForVisibilityByKey(String key) {
        By by = ElementLocators.getBy(key);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    /**
     * Belirtilen key için WebElement'in tıklanabilir olmasını bekler.
     * 
     * @param key Lokator anahtarı
     * @return WebElement nesnesi
     */
    public WebElement waitForClickabilityByKey(String key) {
        By by = ElementLocators.getBy(key);
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    
    /**
     * Belirtilen key için WebElement'e tıklar.
     * 
     * @param key Lokator anahtarı
     */
    public void clickByKey(String key) {
        logger.info("{} elementine tıklanıyor", key);
        waitForClickabilityByKey(key).click();
    }
    
    /**
     * Belirtilen key için WebElement'e metin girer.
     * 
     * @param key Lokator anahtarı
     * @param text Girilecek metin
     */
    public void sendKeysByKey(String key, String text) {
        logger.info("{} elementine '{}' metni giriliyor", key, text);
        WebElement element = waitForVisibilityByKey(key);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Belirtilen key için WebElement'in metnini döndürür.
     * 
     * @param key Lokator anahtarı
     * @return Elemanın metni
     */
    public String getTextByKey(String key) {
        logger.info("{} elementinin metni alınıyor", key);
        return waitForVisibilityByKey(key).getText();
    }
    
    /**
     * Belirtilen key için WebElement'in görünür olup olmadığını kontrol eder.
     * 
     * @param key Lokator anahtarı
     * @return Eleman görünür ise true, değilse false
     */
    public boolean isDisplayedByKey(String key) {
        try {
            return waitForVisibilityByKey(key).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Belirtilen key için WebElement'in var olup olmadığını kontrol eder.
     * 
     * @param key Lokator anahtarı
     * @return Eleman var ise true, değilse false
     */
    public boolean isElementExistByKey(String key) {
        try {
            return !findElementsByKey(key).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
