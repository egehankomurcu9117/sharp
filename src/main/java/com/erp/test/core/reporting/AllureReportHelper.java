package com.erp.test.core.reporting;

import com.erp.test.core.driver.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Allure raporlama için yardımcı sınıf.
 * Ekran görüntüsü alma, adım ekleme gibi işlevleri sağlar.
 */
public class AllureReportHelper {
    private static final Logger logger = LoggerFactory.getLogger(AllureReportHelper.class);

    private AllureReportHelper() {
        // Utility class, no instances
    }

    /**
     * Allure raporuna adım ekler.
     *
     * @param stepName Adım adı
     */
    @Step("{stepName}")
    public static void logStep(String stepName) {
        logger.info("Step: {}", stepName);
    }

    /**
     * Allure raporuna ekran görüntüsü ekler.
     *
     * @param name Ekran görüntüsü adı
     */
    public static void takeScreenshot(String name) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            logger.info("Taking screenshot: {}", name);
            try {
                Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
            } catch (Exception e) {
                logger.error("Failed to take screenshot: {}", name, e);
            }
        } else {
            logger.warn("Driver is null, cannot take screenshot: {}", name);
        }
    }

    /**
     * Allure raporuna ekran görüntüsü ekler ve bayt dizisi olarak döndürür.
     *
     * @param name Ekran görüntüsü adı
     * @return Ekran görüntüsü bayt dizisi
     */
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] takeScreenshotAsAttachment(String name) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            logger.info("Taking screenshot as attachment: {}", name);
            try {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } catch (Exception e) {
                logger.error("Failed to take screenshot as attachment: {}", name, e);
            }
        } else {
            logger.warn("Driver is null, cannot take screenshot as attachment: {}", name);
        }
        return new byte[0];
    }

    /**
     * Allure raporuna metin ekler.
     *
     * @param name Metin adı
     * @param content Metin içeriği
     */
    public static void attachText(String name, String content) {
        logger.info("Attaching text: {}", name);
        Allure.addAttachment(name, "text/plain", content);
    }

    /**
     * Allure raporuna JSON ekler.
     *
     * @param name JSON adı
     * @param json JSON içeriği
     */
    public static void attachJson(String name, String json) {
        logger.info("Attaching JSON: {}", name);
        Allure.addAttachment(name, "application/json", json);
    }

    /**
     * Allure raporuna XML ekler.
     *
     * @param name XML adı
     * @param xml XML içeriği
     */
    public static void attachXml(String name, String xml) {
        logger.info("Attaching XML: {}", name);
        Allure.addAttachment(name, "application/xml", xml);
    }

    /**
     * Allure raporuna dosya ekler.
     *
     * @param name Dosya adı
     * @param filePath Dosya yolu
     */
    public static void attachFile(String name, String filePath) {
        logger.info("Attaching file: {}, path: {}", name, filePath);
        try {
            Path path = Paths.get(filePath);
            Allure.addAttachment(name, Files.newInputStream(path));
        } catch (IOException e) {
            logger.error("Failed to attach file: {}, path: {}", name, filePath, e);
        }
    }

    /**
     * Allure raporuna HTML ekler.
     *
     * @param name HTML adı
     * @param html HTML içeriği
     */
    public static void attachHtml(String name, String html) {
        logger.info("Attaching HTML: {}", name);
        Allure.addAttachment(name, "text/html", html);
    }

    /**
     * Allure raporuna sayfa kaynağı ekler.
     */
    public static void attachPageSource() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            logger.info("Attaching page source");
            try {
                String pageSource = driver.getPageSource();
                Allure.addAttachment("Page Source", "text/html", pageSource);
            } catch (Exception e) {
                logger.error("Failed to attach page source", e);
            }
        } else {
            logger.warn("Driver is null, cannot attach page source");
        }
    }

    /**
     * Allure raporuna çevre bilgilerini ekler.
     */
    public static void attachEnvironmentInfo() {
        logger.info("Attaching environment info");
        attachText("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        attachText("Java Version", System.getProperty("java.version"));
        attachText("User", System.getProperty("user.name"));
    }
}
