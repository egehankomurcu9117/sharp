package com.example.appium;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Appium locator'larını JSON formatında saklayan sınıf.
 * Bu sınıf, mobil uygulama elemanlarının locator'larını (id, xpath, accessibility id, vb.) JSON formatında saklar.
 */
public class AppiumLocators {
    private static final Logger logger = LoggerFactory.getLogger(AppiumLocators.class);
    
    // Tüm ekranların locator'larını saklayan harita
    private static final Map<String, JSONObject> screenLocatorsMap = new HashMap<>();
    
    static {
        // Sunucu adresi ekranı locator'ları
        JSONObject serverAddressScreenLocators = new JSONObject();
        serverAddressScreenLocators.put("serverAddressTitle", new JSONObject().put("type", "id").put("value", "com.logo.wms:id/alertTitle"));
        serverAddressScreenLocators.put("serverAddressField", new JSONObject().put("type", "id").put("value", "com.logo.wms:id/dialog_editText"));
        serverAddressScreenLocators.put("okButton", new JSONObject().put("type", "id").put("value", "com.logo.wms:id/dialog_positiveButton"));
        serverAddressScreenLocators.put("cancelButton", new JSONObject().put("type", "id").put("value", "com.logo.wms:id/dialog_negativeButton"));
        
        // Giriş ekranı locator'ları
        JSONObject loginScreenLocators = new JSONObject();
        loginScreenLocators.put("usernameField", new JSONObject().put("type", "id").put("value", "username_field"));
        loginScreenLocators.put("passwordField", new JSONObject().put("type", "id").put("value", "password_field"));
        loginScreenLocators.put("loginButton", new JSONObject().put("type", "id").put("value", "login_button"));
        loginScreenLocators.put("loginScreen", new JSONObject().put("type", "id").put("value", "login_screen"));
        
        // Ana menü locator'ları
        JSONObject mainMenuLocators = new JSONObject();
        mainMenuLocators.put("menuButton", new JSONObject().put("type", "id").put("value", "menu_button"));
        mainMenuLocators.put("settingsMenuItem", new JSONObject().put("type", "id").put("value", "settings_menu_item"));
        mainMenuLocators.put("logoutMenuItem", new JSONObject().put("type", "id").put("value", "logout_menu_item"));
        mainMenuLocators.put("profileMenuItem", new JSONObject().put("type", "id").put("value", "profile_menu_item"));
        
        // Ayarlar ekranı locator'ları
        JSONObject settingsScreenLocators = new JSONObject();
        settingsScreenLocators.put("settingsTitle", new JSONObject().put("type", "id").put("value", "settings_title"));
        settingsScreenLocators.put("logoutButton", new JSONObject().put("type", "id").put("value", "logout_button"));
        settingsScreenLocators.put("backButton", new JSONObject().put("type", "id").put("value", "back_button"));
        
        // Haritaya locator'ları ekle
        screenLocatorsMap.put("serverAddressScreen", serverAddressScreenLocators);
        screenLocatorsMap.put("loginScreen", loginScreenLocators);
        screenLocatorsMap.put("mainMenu", mainMenuLocators);
        screenLocatorsMap.put("settingsScreen", settingsScreenLocators);
    }
    
    /**
     * Belirtilen ekran ve eleman için locator'ı döndürür.
     * 
     * @param screenName Ekran adı
     * @param elementName Eleman adı
     * @return Selenium By nesnesi
     */
    public static By getLocator(String screenName, String elementName) {
        try {
            if (!screenLocatorsMap.containsKey(screenName)) {
                logger.error("Ekran bulunamadı: {}", screenName);
                throw new IllegalArgumentException("Ekran bulunamadı: " + screenName);
            }
            
            JSONObject screenLocators = screenLocatorsMap.get(screenName);
            
            if (!screenLocators.has(elementName)) {
                logger.error("Eleman bulunamadı: {} ekranında {}", screenName, elementName);
                throw new IllegalArgumentException("Eleman bulunamadı: " + screenName + " ekranında " + elementName);
            }
            
            JSONObject elementLocator = screenLocators.getJSONObject(elementName);
            String type = elementLocator.getString("type");
            String value = elementLocator.getString("value");
            
            switch (type.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "xpath":
                    return By.xpath(value);
                case "accessibility id":
                    return By.xpath("//*[@content-desc='" + value + "']");
                case "class":
                    return By.className(value);
                case "name":
                    return By.name(value);
                case "css":
                    return By.cssSelector(value);
                default:
                    logger.error("Geçersiz locator tipi: {}", type);
                    throw new IllegalArgumentException("Geçersiz locator tipi: " + type);
            }
        } catch (Exception e) {
            logger.error("Locator alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Locator alınırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Dinamik bir locator oluşturur.
     * 
     * @param screenName Ekran adı
     * @param elementName Eleman adı
     * @param dynamicValue Dinamik değer
     * @return Selenium By nesnesi
     */
    public static By getDynamicLocator(String screenName, String elementName, String dynamicValue) {
        try {
            if (!screenLocatorsMap.containsKey(screenName)) {
                logger.error("Ekran bulunamadı: {}", screenName);
                throw new IllegalArgumentException("Ekran bulunamadı: " + screenName);
            }
            
            JSONObject screenLocators = screenLocatorsMap.get(screenName);
            
            if (!screenLocators.has(elementName)) {
                logger.error("Eleman bulunamadı: {} ekranında {}", screenName, elementName);
                throw new IllegalArgumentException("Eleman bulunamadı: " + screenName + " ekranında " + elementName);
            }
            
            JSONObject elementLocator = screenLocators.getJSONObject(elementName);
            String type = elementLocator.getString("type");
            String value = elementLocator.getString("value").replace("{}", dynamicValue);
            
            switch (type.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "xpath":
                    return By.xpath(value);
                case "accessibility id":
                    return By.xpath("//*[@content-desc='" + value + "']");
                case "class":
                    return By.className(value);
                case "name":
                    return By.name(value);
                case "css":
                    return By.cssSelector(value);
                default:
                    logger.error("Geçersiz locator tipi: {}", type);
                    throw new IllegalArgumentException("Geçersiz locator tipi: " + type);
            }
        } catch (Exception e) {
            logger.error("Dinamik locator alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Dinamik locator alınırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Yeni bir locator ekler veya mevcut bir locator'ı günceller.
     * 
     * @param screenName Ekran adı
     * @param elementName Eleman adı
     * @param type Locator tipi (id, xpath, accessibility id, vb.)
     * @param value Locator değeri
     */
    public static void addOrUpdateLocator(String screenName, String elementName, String type, String value) {
        try {
            if (!screenLocatorsMap.containsKey(screenName)) {
                screenLocatorsMap.put(screenName, new JSONObject());
            }
            
            JSONObject screenLocators = screenLocatorsMap.get(screenName);
            screenLocators.put(elementName, new JSONObject().put("type", type).put("value", value));
            
            logger.info("Locator eklendi/güncellendi: {} ekranında {}", screenName, elementName);
        } catch (Exception e) {
            logger.error("Locator eklenirken/güncellenirken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Locator eklenirken/güncellenirken hata oluştu: " + e.getMessage(), e);
        }
    }
}
