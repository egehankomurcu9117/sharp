package com.erp.test.core.listeners;

import com.erp.test.core.driver.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.UUID;

/**
 * Allure raporlama için TestNG listener sınıfı.
 * Test sonuçlarını dinler ve Allure raporuna ekler.
 */
public class AllureListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(AllureListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finishing test suite: {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
        logger.error("Error: {}", result.getThrowable().getMessage());
        
        // Ekran görüntüsü al
        captureScreenshot(result.getName());
        
        // Hata mesajını ekle
        saveTextLog(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test failed but within success percentage: {}", result.getName());
    }

    /**
     * Ekran görüntüsü alır ve Allure raporuna ekler.
     *
     * @param testName Test adı
     * @return Ekran görüntüsü bayt dizisi
     */
    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] captureScreenshot(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                logger.info("Taking screenshot for test: {}", testName);
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } else {
                logger.warn("Driver is null, cannot take screenshot for test: {}", testName);
                return new byte[0];
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for test: {}", testName, e);
            return new byte[0];
        }
    }

    /**
     * Metin logunu Allure raporuna ekler.
     *
     * @param message Eklenecek mesaj
     * @return Mesaj
     */
    @Attachment(value = "Error details", type = "text/plain")
    private String saveTextLog(String message) {
        return message;
    }
    
    /**
     * Test verilerini Allure raporuna ekler.
     *
     * @param data Eklenecek veri
     * @return Veri
     */
    @Attachment(value = "Test data", type = "text/plain")
    public static String saveTestData(String data) {
        return data;
    }
    
    /**
     * HTTP isteğini Allure raporuna ekler.
     *
     * @param request HTTP isteği
     * @return İstek
     */
    @Attachment(value = "HTTP Request", type = "text/plain")
    public static String saveHttpRequest(String request) {
        return request;
    }
    
    /**
     * HTTP yanıtını Allure raporuna ekler.
     *
     * @param response HTTP yanıtı
     * @return Yanıt
     */
    @Attachment(value = "HTTP Response", type = "text/plain")
    public static String saveHttpResponse(String response) {
        return response;
    }
    
    /**
     * Benzersiz bir test ID'si oluşturur.
     *
     * @return Benzersiz test ID'si
     */
    public static String generateUniqueTestId() {
        return UUID.randomUUID().toString();
    }
}
