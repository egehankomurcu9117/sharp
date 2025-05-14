package com.example.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Kullanıcı API'si için özel sınıf.
 * Bu sınıf, kullanıcı API'si ile ilgili işlemleri içerir.
 */
public class UserApi extends ApiRequest {
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);
    
    /**
     * Kullanıcı API'si sınıfı constructor'ı.
     * 
     * @param requestSpec RequestSpecification nesnesi
     */
    public UserApi(RequestSpecification requestSpec) {
        super(requestSpec, "users");
    }
    
    /**
     * Tüm kullanıcıları listeler.
     * 
     * @return Response nesnesi
     */
    public Response getAllUsers() {
        logger.info("Tüm kullanıcılar listeleniyor");
        Response response = get("getAllUsers");
        response.then().statusCode(200).body("size()", greaterThan(0));
        return response;
    }
    
    /**
     * Belirli bir kullanıcıyı getirir.
     * 
     * @param userId Kullanıcı ID'si
     * @return Response nesnesi
     */
    public Response getSingleUser(String userId) {
        logger.info("Kullanıcı getiriliyor: {}", userId);
        Map<String, String> params = createIdParam(userId);
        Response response = get("getSingleUser", params);
        response.then().statusCode(200).body("id", equalTo(Integer.parseInt(userId)));
        return response;
    }
    
    /**
     * Yeni bir kullanıcı oluşturur.
     * 
     * @param name Kullanıcı adı
     * @param job Kullanıcı mesleği
     * @return Response nesnesi
     */
    public Response createUser(String name, String job) {
        logger.info("Kullanıcı oluşturuluyor: {}, {}", name, job);
        JSONObject userJson = new JSONObject();
        userJson.put("name", name);
        userJson.put("job", job);
        
        Response response = post("createUser", userJson);
        response.then().statusCode(201);
        return response;
    }
    
    /**
     * Bir kullanıcıyı günceller.
     * 
     * @param userId Kullanıcı ID'si
     * @param name Yeni kullanıcı adı
     * @param job Yeni kullanıcı mesleği
     * @return Response nesnesi
     */
    public Response updateUser(String userId, String name, String job) {
        logger.info("Kullanıcı güncelleniyor: {}, {}, {}", userId, name, job);
        Map<String, String> params = createIdParam(userId);
        JSONObject updateJson = new JSONObject();
        updateJson.put("name", name);
        updateJson.put("job", job);
        
        Response response = put("updateUser", params, updateJson);
        response.then().statusCode(200);
        return response;
    }
    
    /**
     * Bir kullanıcıyı siler.
     * 
     * @param userId Kullanıcı ID'si
     * @return Response nesnesi
     */
    public Response deleteUser(String userId) {
        logger.info("Kullanıcı siliniyor: {}", userId);
        Map<String, String> params = createIdParam(userId);
        Response response = delete("deleteUser", params);
        response.then().statusCode(200);
        return response;
    }
    
    /**
     * Kullanıcı listesinin boş olmadığını doğrular.
     * 
     * @param response Response nesnesi
     * @return Kullanıcı sayısı
     */
    public int verifyUserListNotEmpty(Response response) {
        logger.info("Kullanıcı listesinin boş olmadığı doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        List<Object> users = jsonPath.getList("");
        
        Assert.assertFalse(users.isEmpty(), "Kullanıcı listesi boş");
        logger.info("Kullanıcı listesi başarıyla alındı, {} kullanıcı bulundu", users.size());
        return users.size();
    }
    
    /**
     * Kullanıcı adının null olmadığını doğrular.
     * 
     * @param response Response nesnesi
     * @return Kullanıcı adı
     */
    public String verifyUserNameNotNull(Response response) {
        logger.info("Kullanıcı adının null olmadığı doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        
        Assert.assertNotNull(name, "Kullanıcı adı null");
        logger.info("Kullanıcı başarıyla alındı, adı: {}", name);
        return name;
    }
    
    /**
     * Kullanıcı ID'sinin null olmadığını doğrular.
     * 
     * @param response Response nesnesi
     * @return Kullanıcı ID'si
     */
    public int verifyUserIdNotNull(Response response) {
        logger.info("Kullanıcı ID'sinin null olmadığı doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        int userId = jsonPath.getInt("id");
        
        Assert.assertNotNull(userId, "Kullanıcı ID'si null");
        logger.info("Kullanıcı başarıyla oluşturuldu, ID: {}", userId);
        return userId;
    }
    
    /**
     * Kullanıcı adının beklendiği gibi olduğunu doğrular.
     * 
     * @param response Response nesnesi
     * @param expectedName Beklenen kullanıcı adı
     */
    public void verifyUserName(Response response, String expectedName) {
        logger.info("Kullanıcı adının beklendiği gibi olduğu doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        
        Assert.assertEquals(name, expectedName, "Kullanıcı adı beklendiği gibi değil");
        logger.info("Kullanıcı adı doğrulandı: {}", name);
    }
}
