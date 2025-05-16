package com.erp.test.core.page;

import com.erp.test.core.config.ConfigManager;
import com.erp.test.core.driver.DriverManager;
import com.erp.test.core.reporting.ExtentReportManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Tüm sayfa sınıfları için temel sınıf.
 * Sayfa nesneleri için ortak işlevleri içerir.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String pageName;

    /**
     * Temel sayfa sınıfı constructor'ı.
     *
     * @param pageName Sayfa adı (loglama ve raporlama için)
     */
    public BasePage(String pageName) {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getInstance().getExplicitWait()));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.pageName = pageName;
        logger.info("Initializing {} page", pageName);
    }

    /**
     * Elementin görünür olmasını bekler.
     *
     * @param locator Element locator'ı
     * @return WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible within timeout: {}", locator);
            takeScreenshot("ElementNotVisible_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * Elementin tıklanabilir olmasını bekler.
     *
     * @param locator Element locator'ı
     * @return WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable within timeout: {}", locator);
            takeScreenshot("ElementNotClickable_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * Elemente tıklar.
     *
     * @param locator Element locator'ı
     */
    protected void click(By locator) {
        logger.debug("Clicking on element: {}", locator);
        try {
            waitForElementClickable(locator).click();
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            takeScreenshot("ClickFailed_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * JavaScript ile elemente tıklar.
     * Normal tıklama çalışmadığında kullanılabilir.
     *
     * @param locator Element locator'ı
     */
    protected void clickWithJS(By locator) {
        logger.debug("Clicking on element with JavaScript: {}", locator);
        WebElement element = waitForElementVisible(locator);
        try {
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("Failed to click element with JavaScript: {}", locator, e);
            takeScreenshot("JSClickFailed_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * Elemente metin girer.
     *
     * @param locator Element locator'ı
     * @param text Girilecek metin
     */
    protected void sendKeys(By locator, String text) {
        logger.debug("Sending text '{}' to element: {}", text, locator);
        WebElement element = waitForElementVisible(locator);
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}", locator, e);
            takeScreenshot("SendKeysFailed_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * Elementin metnini döndürür.
     *
     * @param locator Element locator'ı
     * @return Elementin metni
     */
    protected String getText(By locator) {
        logger.debug("Getting text from element: {}", locator);
        return waitForElementVisible(locator).getText();
    }

    /**
     * Elementin görünür olup olmadığını kontrol eder.
     *
     * @param locator Element locator'ı
     * @return Element görünür ise true, değilse false
     */
    protected boolean isElementDisplayed(By locator) {
        logger.debug("Checking if element is displayed: {}", locator);
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.debug("Element is not displayed: {}", locator);
            return false;
        }
    }

    /**
     * Dropdown'dan görünür metin ile seçim yapar.
     *
     * @param locator Dropdown locator'ı
     * @param visibleText Seçilecek görünür metin
     */
    protected void selectByVisibleText(By locator, String visibleText) {
        logger.debug("Selecting option '{}' from dropdown: {}", visibleText, locator);
        Select select = new Select(waitForElementVisible(locator));
        try {
            select.selectByVisibleText(visibleText);
        } catch (Exception e) {
            logger.error("Failed to select option '{}' from dropdown: {}", visibleText, locator, e);
            takeScreenshot("SelectFailed_" + System.currentTimeMillis());
            throw e;
        }
    }

    /**
     * Elemente kadar sayfayı kaydırır.
     *
     * @param locator Element locator'ı
     */
    protected void scrollToElement(By locator) {
        logger.debug("Scrolling to element: {}", locator);
        WebElement element = waitForElementVisible(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Sayfanın yüklenmesini bekler.
     */
    protected void waitForPageLoad() {
        logger.debug("Waiting for page to load");
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Ekran görüntüsü alır.
     *
     * @param name Ekran görüntüsü adı
     * @return Ekran görüntüsü dosyasının yolu
     */
    protected String takeScreenshot(String name) {
        logger.debug("Taking screenshot: {}", name);
        String screenshotPath = ConfigManager.getInstance().getScreenshotPath();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = name + "_" + timestamp + ".png";
        String filePath = screenshotPath + "/" + fileName;
        
        // Ensure directory exists
        File screenshotDir = new File(screenshotPath);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            logger.info("Screenshot saved to: {}", filePath);
            
            // Add screenshot to extent report if available
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().addScreenCaptureFromPath(filePath);
            }
            
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
            return null;
        }
    }

    /**
     * Belirli bir süre bekler.
     *
     * @param seconds Beklenecek süre (saniye)
     */
    protected void sleep(int seconds) {
        logger.debug("Sleeping for {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted", e);
        }
    }

    /**
     * Elementin var olup olmadığını kontrol eder.
     *
     * @param locator Element locator'ı
     * @return Element var ise true, değilse false
     */
    protected boolean isElementPresent(By locator) {
        logger.debug("Checking if element is present: {}", locator);
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Belirtilen locator'a sahip tüm elementleri bulur.
     *
     * @param locator Element locator'ı
     * @return WebElement listesi
     */
    protected List<WebElement> findElements(By locator) {
        logger.debug("Finding elements: {}", locator);
        return driver.findElements(locator);
    }
}
