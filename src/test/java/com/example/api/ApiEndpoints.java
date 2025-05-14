package com.example.api;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * API endpoint'lerini JSON formatında saklayan sınıf.
 * Bu sınıf, API endpoint'lerini ve ilgili bilgileri JSON formatında saklar.
 */
public class ApiEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ApiEndpoints.class);
    
    // Tüm API endpoint'lerini saklayan harita
    private static final Map<String, JSONObject> endpointsMap = new HashMap<>();
    
    static {
        // Kullanıcı endpoint'leri
        JSONObject userEndpoints = new JSONObject();
        userEndpoints.put("getAllUsers", new JSONObject()
                .put("method", "GET")
                .put("path", "/users")
                .put("description", "Tüm kullanıcıları listeler"));
        
        userEndpoints.put("getSingleUser", new JSONObject()
                .put("method", "GET")
                .put("path", "/users/{id}")
                .put("description", "Belirli bir kullanıcıyı getirir"));
        
        userEndpoints.put("createUser", new JSONObject()
                .put("method", "POST")
                .put("path", "/users")
                .put("description", "Yeni bir kullanıcı oluşturur"));
        
        userEndpoints.put("updateUser", new JSONObject()
                .put("method", "PUT")
                .put("path", "/users/{id}")
                .put("description", "Bir kullanıcıyı günceller"));
        
        userEndpoints.put("deleteUser", new JSONObject()
                .put("method", "DELETE")
                .put("path", "/users/{id}")
                .put("description", "Bir kullanıcıyı siler"));
        
        // Haritaya endpoint'leri ekle
        endpointsMap.put("users", userEndpoints);
    }
    
    /**
     * Belirtilen endpoint için yol döndürür.
     * 
     * @param category Kategori adı
     * @param endpointName Endpoint adı
     * @return Endpoint yolu
     */
    public static String getPath(String category, String endpointName) {
        try {
            if (!endpointsMap.containsKey(category)) {
                logger.error("Kategori bulunamadı: {}", category);
                throw new IllegalArgumentException("Kategori bulunamadı: " + category);
            }
            
            JSONObject categoryEndpoints = endpointsMap.get(category);
            
            if (!categoryEndpoints.has(endpointName)) {
                logger.error("Endpoint bulunamadı: {} kategorisinde {}", category, endpointName);
                throw new IllegalArgumentException("Endpoint bulunamadı: " + category + " kategorisinde " + endpointName);
            }
            
            JSONObject endpoint = categoryEndpoints.getJSONObject(endpointName);
            return endpoint.getString("path");
        } catch (Exception e) {
            logger.error("Endpoint yolu alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Endpoint yolu alınırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Belirtilen endpoint için HTTP metodunu döndürür.
     * 
     * @param category Kategori adı
     * @param endpointName Endpoint adı
     * @return HTTP metodu
     */
    public static String getMethod(String category, String endpointName) {
        try {
            if (!endpointsMap.containsKey(category)) {
                logger.error("Kategori bulunamadı: {}", category);
                throw new IllegalArgumentException("Kategori bulunamadı: " + category);
            }
            
            JSONObject categoryEndpoints = endpointsMap.get(category);
            
            if (!categoryEndpoints.has(endpointName)) {
                logger.error("Endpoint bulunamadı: {} kategorisinde {}", category, endpointName);
                throw new IllegalArgumentException("Endpoint bulunamadı: " + category + " kategorisinde " + endpointName);
            }
            
            JSONObject endpoint = categoryEndpoints.getJSONObject(endpointName);
            return endpoint.getString("method");
        } catch (Exception e) {
            logger.error("HTTP metodu alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("HTTP metodu alınırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Belirtilen endpoint için açıklamayı döndürür.
     * 
     * @param category Kategori adı
     * @param endpointName Endpoint adı
     * @return Endpoint açıklaması
     */
    public static String getDescription(String category, String endpointName) {
        try {
            if (!endpointsMap.containsKey(category)) {
                logger.error("Kategori bulunamadı: {}", category);
                throw new IllegalArgumentException("Kategori bulunamadı: " + category);
            }
            
            JSONObject categoryEndpoints = endpointsMap.get(category);
            
            if (!categoryEndpoints.has(endpointName)) {
                logger.error("Endpoint bulunamadı: {} kategorisinde {}", category, endpointName);
                throw new IllegalArgumentException("Endpoint bulunamadı: " + category + " kategorisinde " + endpointName);
            }
            
            JSONObject endpoint = categoryEndpoints.getJSONObject(endpointName);
            return endpoint.getString("description");
        } catch (Exception e) {
            logger.error("Endpoint açıklaması alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Endpoint açıklaması alınırken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Dinamik bir endpoint yolu oluşturur.
     * 
     * @param category Kategori adı
     * @param endpointName Endpoint adı
     * @param params Parametre haritası
     * @return Dinamik endpoint yolu
     */
    public static String getDynamicPath(String category, String endpointName, Map<String, String> params) {
        try {
            String path = getPath(category, endpointName);
            
            for (Map.Entry<String, String> entry : params.entrySet()) {
                path = path.replace("{" + entry.getKey() + "}", entry.getValue());
            }
            
            return path;
        } catch (Exception e) {
            logger.error("Dinamik endpoint yolu oluşturulurken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Dinamik endpoint yolu oluşturulurken hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /**
     * Yeni bir endpoint ekler veya mevcut bir endpoint'i günceller.
     * 
     * @param category Kategori adı
     * @param endpointName Endpoint adı
     * @param method HTTP metodu
     * @param path Endpoint yolu
     * @param description Endpoint açıklaması
     */
    public static void addOrUpdateEndpoint(String category, String endpointName, String method, String path, String description) {
        try {
            if (!endpointsMap.containsKey(category)) {
                endpointsMap.put(category, new JSONObject());
            }
            
            JSONObject categoryEndpoints = endpointsMap.get(category);
            categoryEndpoints.put(endpointName, new JSONObject()
                    .put("method", method)
                    .put("path", path)
                    .put("description", description));
            
            logger.info("Endpoint eklendi/güncellendi: {} kategorisinde {}", category, endpointName);
        } catch (Exception e) {
            logger.error("Endpoint eklenirken/güncellenirken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Endpoint eklenirken/güncellenirken hata oluştu: " + e.getMessage(), e);
        }
    }
}
