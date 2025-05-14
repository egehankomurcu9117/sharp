package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Tüm ekran sınıfları için temel sınıf.
 * Bu sınıf, tüm ekran sınıflarının ortak işlevlerini içerir.
 */
public abstract class BaseScreen {
    private static final Logger logger = LoggerFactory.getLogger(BaseScreen.class);
    
    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected String screenName;
    
    /**
     * Temel ekran sınıfı constructor'ı.
     * 
     * @param driver AndroidDriver nesnesi
     * @param screenName Ekran adı
     */
    public BaseScreen(AndroidDriver driver, String screenName) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.screenName = screenName;
    }
    
    /**
     * Belirtilen eleman için locator'ı döndürür.
     * 
     * @param elementName Eleman adı
     * @return Selenium By nesnesi
     */
    protected By getLocator(String elementName) {
        return AppiumLocators.getLocator(screenName, elementName);
    }
    
    /**
     * Dinamik bir locator oluşturur.
     * 
     * @param elementName Eleman adı
     * @param dynamicValue Dinamik değer
     * @return Selenium By nesnesi
     */
    protected By getDynamicLocator(String elementName, String dynamicValue) {
        return AppiumLocators.getDynamicLocator(screenName, elementName, dynamicValue);
    }
    
    /**
     * Belirtilen elemanı bulur.
     * 
     * @param elementName Eleman adı
     * @return WebElement nesnesi
     */
    protected WebElement findElement(String elementName) {
        By locator = getLocator(elementName);
        return driver.findElement(locator);
    }
    
    /**
     * Belirtilen elemanları bulur.
     * 
     * @param elementName Eleman adı
     * @return WebElement listesi
     */
    protected List<WebElement> findElements(String elementName) {
        By locator = getLocator(elementName);
        return driver.findElements(locator);
    }
    
    /**
     * Belirtilen elemanın görünür olmasını bekler.
     * 
     * @param elementName Eleman adı
     * @return WebElement nesnesi
     */
    protected WebElement waitForVisibility(String elementName) {
        By locator = getLocator(elementName);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Belirtilen elemanın tıklanabilir olmasını bekler.
     * 
     * @param elementName Eleman adı
     * @return WebElement nesnesi
     */
    protected WebElement waitForClickability(String elementName) {
        By locator = getLocator(elementName);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Belirtilen elemana tıklar.
     * 
     * @param elementName Eleman adı
     */
    protected void click(String elementName) {
        logger.info("{} elemanına tıklanıyor", elementName);
        waitForClickability(elementName).click();
    }
    
    /**
     * Belirtilen elemana metin girer.
     * 
     * @param elementName Eleman adı
     * @param text Girilecek metin
     */
    protected void sendKeys(String elementName, String text) {
        logger.info("{} elemanına '{}' metni giriliyor", elementName, text);
        WebElement element = waitForVisibility(elementName);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Belirtilen elemanın metnini döndürür.
     * 
     * @param elementName Eleman adı
     * @return Elemanın metni
     */
    protected String getText(String elementName) {
        logger.info("{} elemanının metni alınıyor", elementName);
        return waitForVisibility(elementName).getText();
    }
    
    /**
     * Belirtilen elemanın görünür olup olmadığını kontrol eder.
     * 
     * @param elementName Eleman adı
     * @return Eleman görünür ise true, değilse false
     */
    protected boolean isDisplayed(String elementName) {
        try {
            return waitForVisibility(elementName).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
