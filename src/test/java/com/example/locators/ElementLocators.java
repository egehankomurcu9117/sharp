package com.example.locators;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Element lokatorlarını JSON formatında saklayan sınıf.
 * Bu sınıf, hem Android hem de iOS için lokatorları JSON formatında saklar.
 */
public class ElementLocators {
    private static final Logger logger = LoggerFactory.getLogger(ElementLocators.class);
    
    // Lokatorları saklayan harita
    private static final Map<String, JSONObject> locatorsMap = new HashMap<>();
    
    // Platform türü (android veya ios)
    private static String platform = "android"; // Varsayılan olarak Android
    
    static {
        // JSON formatında lokatorları tanımla
        JSONArray locatorsArray = new JSONArray();
        
        // Login ekranı lokatorları
        locatorsArray.put(new JSONObject()
                .put("key", "login")
                .put("androidValue", "com.vektortelekom.android.vservice.test:id/button_login")
                .put("androidType", "id")
                .put("iosValue", "**/XCUIElementTypeStaticText[`label == \"Giriş Yap\"`]")
                .put("iosType", "classChain"));
        
        locatorsArray.put(new JSONObject()
                .put("key", "email")
                .put("androidValue", "com.vektortelekom.android.vservice.test:id/edit_text_email")
                .put("androidType", "id")
                .put("iosValue", "//XCUIElementTypeOther/XCUIElementTypeTextField")
                .put("iosType", "xpath"));
        
        // Ana menü lokatorları
        locatorsArray.put(new JSONObject()
                .put("key", "flexiShuttleButton")
                .put("androidValue", "com.vektortelekom.android.vservice.test:id/button_flexi_shuttle")
                .put("androidType", "id")
                .put("iosValue", "**/XCUIElementTypeButton[`label == \"Flexi Shuttle\"`]")
                .put("iosType", "classChain"));
        
        locatorsArray.put(new JSONObject()
                .put("key", "profileButton")
                .put("androidValue", "com.vektortelekom.android.vservice.test:id/button_profile")
                .put("androidType", "id")
                .put("iosValue", "**/XCUIElementTypeButton[`label == \"Profil\"`]")
                .put("iosType", "classChain"));
        
        // Lokatorları haritaya ekle
        for (int i = 0; i < locatorsArray.length(); i++) {
            JSONObject locator = locatorsArray.getJSONObject(i);
            locatorsMap.put(locator.getString("key"), locator);
        }
    }
    
    /**
     * JSON dosyasından lokatorları yükler.
     * 
     * @param filePath JSON dosyasının yolu
     */
    public static void loadLocatorsFromFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray locatorsArray = new JSONArray(content);
            
            // Lokatorları haritaya ekle
            for (int i = 0; i < locatorsArray.length(); i++) {
                JSONObject locator = locatorsArray.getJSONObject(i);
                locatorsMap.put(locator.getString("key"), locator);
            }
            
            logger.info("Lokatorlar başarıyla yüklendi: {}", filePath);
        } catch (IOException e) {
            logger.error("Lokatorlar yüklenirken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Lokatorlar yüklenirken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Platform türünü ayarlar.
     * 
     * @param platformName Platform adı (android veya ios)
     */
    public static void setPlatform(String platformName) {
        platform = platformName.toLowerCase();
        logger.info("Platform türü ayarlandı: {}", platform);
    }
    
    /**
     * Belirtilen key için lokator nesnesini döndürür.
     * 
     * @param key Lokator anahtarı
     * @return JSONObject Lokator nesnesi
     */
    public static JSONObject getLocator(String key) {
        if (!locatorsMap.containsKey(key)) {
            logger.error("Lokator bulunamadı: {}", key);
            throw new IllegalArgumentException("Lokator bulunamadı: " + key);
        }
        
        return locatorsMap.get(key);
    }
    
    /**
     * Belirtilen key için Selenium By nesnesini döndürür.
     * 
     * @param key Lokator anahtarı
     * @return By Selenium By nesnesi
     */
    public static By getBy(String key) {
        JSONObject locator = getLocator(key);
        
        String type = platform.equals("android") ? 
                locator.getString("androidType") : 
                locator.getString("iosType");
        
        String value = platform.equals("android") ? 
                locator.getString("androidValue") : 
                locator.getString("iosValue");
        
        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "xpath":
                return By.xpath(value);
            case "classchain":
                // iOS için classChain, Appium'da özel bir lokator tipidir
                // Android'de kullanılmaz, bu yüzden iOS için özel bir durum
                if (platform.equals("ios")) {
                    return By.xpath(value); // Basitleştirmek için xpath olarak kullanıyoruz
                }
                throw new IllegalArgumentException("ClassChain lokator tipi Android'de desteklenmiyor");
            case "class":
                return By.className(value);
            case "name":
                return By.name(value);
            case "css":
                return By.cssSelector(value);
            case "linktext":
                return By.linkText(value);
            case "partiallinktext":
                return By.partialLinkText(value);
            default:
                logger.error("Geçersiz lokator tipi: {}", type);
                throw new IllegalArgumentException("Geçersiz lokator tipi: " + type);
        }
    }
    
    /**
     * Yeni bir lokator ekler veya mevcut bir lokator'ı günceller.
     * 
     * @param key Lokator anahtarı
     * @param androidValue Android için lokator değeri
     * @param androidType Android için lokator tipi
     * @param iosValue iOS için lokator değeri
     * @param iosType iOS için lokator tipi
     */
    public static void addOrUpdateLocator(String key, String androidValue, String androidType, String iosValue, String iosType) {
        JSONObject locator = new JSONObject()
                .put("key", key)
                .put("androidValue", androidValue)
                .put("androidType", androidType)
                .put("iosValue", iosValue)
                .put("iosType", iosType);
        
        locatorsMap.put(key, locator);
        logger.info("Lokator eklendi/güncellendi: {}", key);
    }
}
