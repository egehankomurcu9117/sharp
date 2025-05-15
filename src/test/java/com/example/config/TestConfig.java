package com.example.config;

import com.example.locators.ElementLocators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test yapılandırma sınıfı.
 * Bu sınıf, test yapılandırmasını içerir.
 */
public class TestConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestConfig.class);
    
    // Lokator dosyasının yolu
    private static final String LOCATORS_FILE_PATH = "src/test/resources/locators/app_locators.json";
    
    /**
     * Test yapılandırmasını başlatır.
     */
    public static void initialize() {
        logger.info("Test yapılandırması başlatılıyor");
        
        // Lokatorları yükle
        loadLocators();
        
        // Platform türünü ayarla (android veya ios)
        setPlatform();
    }
    
    /**
     * Lokatorları yükler.
     */
    private static void loadLocators() {
        logger.info("Lokatorlar yükleniyor");
        ElementLocators.loadLocatorsFromFile(LOCATORS_FILE_PATH);
    }
    
    /**
     * Platform türünü ayarlar.
     */
    private static void setPlatform() {
        logger.info("Platform türü ayarlanıyor");
        
        // Sistem özelliklerinden platform türünü al
        String platform = System.getProperty("platform", "android");
        ElementLocators.setPlatform(platform);
        
        logger.info("Platform türü: {}", platform);
    }
}
