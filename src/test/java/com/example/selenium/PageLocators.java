package com.example.selenium;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Sayfa locator'larını JSON formatında saklayan sınıf.
 * Bu sınıf, sayfa elemanlarının locator'larını (id, xpath, css, vb.) JSON formatında saklar.
 */
public class PageLocators {
    private static final Logger logger = LoggerFactory.getLogger(PageLocators.class);
    
    // Tüm sayfaların locator'larını saklayan harita
    private static final Map<String, JSONObject> pageLocatorsMap = new HashMap<>();
    
    static {
        // Ana sayfa locator'ları
        JSONObject homePageLocators = new JSONObject();
        homePageLocators.put("logo", new JSONObject().put("type", "id").put("value", "logo"));
        
        // Giriş sayfası locator'ları
        JSONObject loginPageLocators = new JSONObject();
        loginPageLocators.put("username", new JSONObject().put("type", "id").put("value", "username"));
        loginPageLocators.put("password", new JSONObject().put("type", "id").put("value", "password"));
        loginPageLocators.put("loginButton", new JSONObject().put("type", "id").put("value", "login_button"));
        loginPageLocators.put("loginSuccess", new JSONObject().put("type", "id").put("value", "login_success"));
        
        // Arama sayfası locator'ları
        JSONObject searchPageLocators = new JSONObject();
        searchPageLocators.put("searchBox", new JSONObject().put("type", "id").put("value", "search_box"));
        searchPageLocators.put("searchButton", new JSONObject().put("type", "id").put("value", "search_button"));
        searchPageLocators.put("searchResults", new JSONObject().put("type", "id").put("value", "search_results"));
        searchPageLocators.put("resultItems", new JSONObject().put("type", "css").put("value", ".result-item"));
        
        // Kullanıcı menüsü locator'ları
        JSONObject userMenuLocators = new JSONObject();
        userMenuLocators.put("userMenu", new JSONObject().put("type", "id").put("value", "user_menu"));
        userMenuLocators.put("profileOption", new JSONObject().put("type", "id").put("value", "profile_option"));
        userMenuLocators.put("settingsOption", new JSONObject().put("type", "id").put("value", "settings_option"));
        userMenuLocators.put("logoutOption", new JSONObject().put("type", "id").put("value", "logout_option"));
        userMenuLocators.put("loginLink", new JSONObject().put("type", "id").put("value", "login_link"));
        
        // Haritaya locator'ları ekle
        pageLocatorsMap.put("homePage", homePageLocators);
        pageLocatorsMap.put("loginPage", loginPageLocators);
        pageLocatorsMap.put("searchPage", searchPageLocators);
        pageLocatorsMap.put("userMenu", userMenuLocators);
    }
    
    /**
     * Belirtilen sayfa ve eleman için locator'ı döndürür.
     * 
     * @param pageName Sayfa adı
     * @param elementName Eleman adı
     * @return Selenium By nesnesi
     */
    public static By getLocator(String pageName, String elementName) {
        try {
            if (!pageLocatorsMap.containsKey(pageName)) {
                logger.error("Sayfa bulunamadı: {}", pageName);
                throw new IllegalArgumentException("Sayfa bulunamadı: " + pageName);
            }
            
            JSONObject pageLocators = pageLocatorsMap.get(pageName);
            
            if (!pageLocators.has(elementName)) {
                logger.error("Eleman bulunamadı: {} sayfasında {}", pageName, elementName);
                throw new IllegalArgumentException("Eleman bulunamadı: " + pageName + " sayfasında " + elementName);
            }
            
            JSONObject elementLocator = pageLocators.getJSONObject(elementName);
            String type = elementLocator.getString("type");
            String value = elementLocator.getString("value");
            
            switch (type.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "xpath":
                    return By.xpath(value);
                case "css":
                    return By.cssSelector(value);
                case "name":
                    return By.name(value);
                case "class":
                    return By.className(value);
                case "tag":
                    return By.tagName(value);
                case "linktext":
                    return By.linkText(value);
                case "partiallinktext":
                    return By.partialLinkText(value);
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
     * @param pageName Sayfa adı
     * @param elementName Eleman adı
     * @param dynamicValue Dinamik değer
     * @return Selenium By nesnesi
     */
    public static By getDynamicLocator(String pageName, String elementName, String dynamicValue) {
        try {
            if (!pageLocatorsMap.containsKey(pageName)) {
                logger.error("Sayfa bulunamadı: {}", pageName);
                throw new IllegalArgumentException("Sayfa bulunamadı: " + pageName);
            }
            
            JSONObject pageLocators = pageLocatorsMap.get(pageName);
            
            if (!pageLocators.has(elementName)) {
                logger.error("Eleman bulunamadı: {} sayfasında {}", pageName, elementName);
                throw new IllegalArgumentException("Eleman bulunamadı: " + pageName + " sayfasında " + elementName);
            }
            
            JSONObject elementLocator = pageLocators.getJSONObject(elementName);
            String type = elementLocator.getString("type");
            String value = elementLocator.getString("value").replace("{}", dynamicValue);
            
            switch (type.toLowerCase()) {
                case "id":
                    return By.id(value);
                case "xpath":
                    return By.xpath(value);
                case "css":
                    return By.cssSelector(value);
                case "name":
                    return By.name(value);
                case "class":
                    return By.className(value);
                case "tag":
                    return By.tagName(value);
                case "linktext":
                    return By.linkText(value);
                case "partiallinktext":
                    return By.partialLinkText(value);
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
     * @param pageName Sayfa adı
     * @param elementName Eleman adı
     * @param type Locator tipi (id, xpath, css, vb.)
     * @param value Locator değeri
     */
    public static void addOrUpdateLocator(String pageName, String elementName, String type, String value) {
        try {
            if (!pageLocatorsMap.containsKey(pageName)) {
                pageLocatorsMap.put(pageName, new JSONObject());
            }
            
            JSONObject pageLocators = pageLocatorsMap.get(pageName);
            pageLocators.put(elementName, new JSONObject().put("type", type).put("value", value));
            
            logger.info("Locator eklendi/güncellendi: {} sayfasında {}", pageName, elementName);
        } catch (Exception e) {
            logger.error("Locator eklenirken/güncellenirken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Locator eklenirken/güncellenirken hata oluştu: " + e.getMessage(), e);
        }
    }
}
