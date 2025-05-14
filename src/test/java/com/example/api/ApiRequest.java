package com.example.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * API istekleri için temel sınıf.
 * Bu sınıf, API isteklerini göndermek için kullanılır.
 */
public class ApiRequest {
    private static final Logger logger = LoggerFactory.getLogger(ApiRequest.class);
    
    private RequestSpecification requestSpec;
    private String category;
    
    /**
     * API isteği sınıfı constructor'ı.
     * 
     * @param requestSpec RequestSpecification nesnesi
     * @param category Kategori adı
     */
    public ApiRequest(RequestSpecification requestSpec, String category) {
        this.requestSpec = requestSpec;
        this.category = category;
    }
    
    /**
     * GET isteği gönderir.
     * 
     * @param endpointName Endpoint adı
     * @return Response nesnesi
     */
    public Response get(String endpointName) {
        logger.info("GET isteği gönderiliyor: {} - {}", category, endpointName);
        String path = ApiEndpoints.getPath(category, endpointName);
        
        return given()
                .spec(requestSpec)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }
    
    /**
     * Parametreli GET isteği gönderir.
     * 
     * @param endpointName Endpoint adı
     * @param params Parametre haritası
     * @return Response nesnesi
     */
    public Response get(String endpointName, Map<String, String> params) {
        logger.info("Parametreli GET isteği gönderiliyor: {} - {}", category, endpointName);
        String path = ApiEndpoints.getDynamicPath(category, endpointName, params);
        
        return given()
                .spec(requestSpec)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }
    
    /**
     * POST isteği gönderir.
     * 
     * @param endpointName Endpoint adı
     * @param body İstek gövdesi
     * @return Response nesnesi
     */
    public Response post(String endpointName, JSONObject body) {
        logger.info("POST isteği gönderiliyor: {} - {}", category, endpointName);
        String path = ApiEndpoints.getPath(category, endpointName);
        
        return given()
                .spec(requestSpec)
                .body(body.toString())
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }
    
    /**
     * PUT isteği gönderir.
     * 
     * @param endpointName Endpoint adı
     * @param params Parametre haritası
     * @param body İstek gövdesi
     * @return Response nesnesi
     */
    public Response put(String endpointName, Map<String, String> params, JSONObject body) {
        logger.info("PUT isteği gönderiliyor: {} - {}", category, endpointName);
        String path = ApiEndpoints.getDynamicPath(category, endpointName, params);
        
        return given()
                .spec(requestSpec)
                .body(body.toString())
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }
    
    /**
     * DELETE isteği gönderir.
     * 
     * @param endpointName Endpoint adı
     * @param params Parametre haritası
     * @return Response nesnesi
     */
    public Response delete(String endpointName, Map<String, String> params) {
        logger.info("DELETE isteği gönderiliyor: {} - {}", category, endpointName);
        String path = ApiEndpoints.getDynamicPath(category, endpointName, params);
        
        return given()
                .spec(requestSpec)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }
    
    /**
     * ID parametresi için harita oluşturur.
     * 
     * @param id ID değeri
     * @return Parametre haritası
     */
    public static Map<String, String> createIdParam(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return params;
    }
}
