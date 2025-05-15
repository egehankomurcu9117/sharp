package com.example.steps;

import com.example.base.BasePage;
import com.example.base.DriverFactory;
import com.example.config.TestConfig;
import com.example.locators.ElementLocators;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.AfterScenario;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uygulama için Gauge adım tanımlamaları.
 * Bu sınıf, Gauge senaryolarında kullanılan adımları içerir.
 */
public class AppSteps extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(AppSteps.class);

    /**
     * AppSteps sınıfı constructor'ı.
     */
    public AppSteps() {
        super(null); // Driver BeforeScenario'da başlatılacak
    }

    @BeforeScenario
    public void setUp() {
        logger.info("Senaryo başlatılıyor");

        // Test yapılandırmasını başlat
        TestConfig.initialize();

        // Platform türünü al
        String platform = System.getProperty("platform", "android");

        // Driver'ı başlat
        driver = DriverFactory.createDriver(platform);

        // Wait nesnesini güncelle
        wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    /**
     * Belirtilen key için elemente tıklar, eğer element görünürse.
     *
     * @param key Lokator anahtarı
     */
    @Step({"<key> elementine tikla", "Click element by <key> if exist"})
    public void existTapByKey(String key) {
        if (findElementByKey(key).isDisplayed()) {
            findElementByKey(key).click();
            logger.info("{} elementine tıklandı", key);
        } else {
            Assertions.fail(key + " Elementi Bulunamadi.");
        }
    }

    /**
     * Belirtilen key için elemente metin girer.
     *
     * @param key Lokator anahtarı
     * @param text Girilecek metin
     */
    @Step({"<key> elementine <text> metnini yaz", "Type <text> to element by <key>"})
    public void typeTextByKey(String key, String text) {
        sendKeysByKey(key, text);
        logger.info("{} elementine '{}' metni yazıldı", key, text);
    }

    /**
     * Belirtilen key için elementin görünür olduğunu doğrular.
     *
     * @param key Lokator anahtarı
     */
    @Step({"<key> elementinin gorunur oldugunu dogrula", "Verify element by <key> is visible"})
    public void verifyElementVisibleByKey(String key) {
        boolean isVisible = isDisplayedByKey(key);
        Assertions.assertTrue(isVisible, key + " elementi görünür değil");
        logger.info("{} elementinin görünür olduğu doğrulandı", key);
    }

    /**
     * Belirtilen key için elementin metnini doğrular.
     *
     * @param key Lokator anahtarı
     * @param expectedText Beklenen metin
     */
    @Step({"<key> elementinin metninin <expectedText> oldugunu dogrula", "Verify element by <key> has text <expectedText>"})
    public void verifyElementTextByKey(String key, String expectedText) {
        String actualText = getTextByKey(key);
        Assertions.assertEquals(expectedText, actualText, key + " elementinin metni beklenen değil");
        logger.info("{} elementinin metninin '{}' olduğu doğrulandı", key, expectedText);
    }

    /**
     * Belirtilen key için elementin var olduğunu doğrular.
     *
     * @param key Lokator anahtarı
     */
    @Step({"<key> elementinin var oldugunu dogrula", "Verify element by <key> exists"})
    public void verifyElementExistsByKey(String key) {
        boolean exists = isElementExistByKey(key);
        Assertions.assertTrue(exists, key + " elementi bulunamadı");
        logger.info("{} elementinin var olduğu doğrulandı", key);
    }

    /**
     * Belirtilen key için elementin var olmadığını doğrular.
     *
     * @param key Lokator anahtarı
     */
    @Step({"<key> elementinin var olmadigini dogrula", "Verify element by <key> does not exist"})
    public void verifyElementNotExistsByKey(String key) {
        boolean exists = isElementExistByKey(key);
        Assertions.assertFalse(exists, key + " elementi hala var");
        logger.info("{} elementinin var olmadığı doğrulandı", key);
    }

    /**
     * Belirtilen süre kadar bekler.
     *
     * @param seconds Beklenecek süre (saniye)
     */
    @Step({"<seconds> saniye bekle", "Wait for <seconds> seconds"})
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.info("{} saniye beklendi", seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Bekleme sırasında hata oluştu: {}", e.getMessage());
        }
    }

    @AfterScenario
    public void tearDown() {
        logger.info("Senaryo sonlandırılıyor");
        // Driver'ı kapat
        if (driver != null) {
            driver.quit();
        }
    }
}
