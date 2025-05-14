package com.example.gauge;

import com.example.api.RestAssuredBase;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.BeforeScenario;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * API için Gauge adım implementasyonları.
 */
public class ApiSteps extends RestAssuredBase {
    private static final Logger logger = LoggerFactory.getLogger(ApiSteps.class);
    private Response response;
    private JSONObject requestBody;
    private Integer userId;

    @BeforeScenario
    public void setUp() {
        super.setUp();
        logger.info("API senaryosu başlatılıyor");
    }

    @Step("Kullanıcı listesi API'sine GET isteği gönder")
    public void sendGetRequestToUsersApi() {
        logger.info("Kullanıcı listesi API'sine GET isteği gönderiliyor");
        response = given()
                .spec(requestSpec)
                .when()
                .get("/users");
    }

    @Step("Yanıt kodunun <statusCode> olduğunu doğrula")
    public void verifyStatusCode(int statusCode) {
        logger.info("Yanıt kodu doğrulanıyor: {}", statusCode);
        Assert.assertEquals(response.getStatusCode(), statusCode, "Yanıt kodu beklendiği gibi değil");
    }

    @Step("Yanıtın geçerli bir JSON olduğunu doğrula")
    public void verifyValidJson() {
        logger.info("Yanıtın geçerli bir JSON olduğu doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        Assert.assertNotNull(jsonPath, "Yanıt geçerli bir JSON değil");
    }

    @Step("Kullanıcı listesinin boş olmadığını doğrula")
    public void verifyUserListNotEmpty() {
        logger.info("Kullanıcı listesinin boş olmadığı doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        List<Object> users = jsonPath.getList("data");
        Assert.assertFalse(users.isEmpty(), "Kullanıcı listesi boş");
    }

    @Step("Yeni kullanıcı verilerini hazırla")
    public void prepareNewUserData() {
        logger.info("Yeni kullanıcı verileri hazırlanıyor");
        requestBody = new JSONObject();
        requestBody.put("name", "Test User");
        requestBody.put("job", "QA Engineer");
    }

    @Step("Kullanıcı oluşturma API'sine POST isteği gönder")
    public void sendPostRequestToCreateUserApi() {
        logger.info("Kullanıcı oluşturma API'sine POST isteği gönderiliyor");
        response = given()
                .spec(requestSpec)
                .body(requestBody.toString())
                .when()
                .post("/users");
    }

    @Step("Oluşturulan kullanıcı ID'sini al")
    public void getCreatedUserId() {
        logger.info("Oluşturulan kullanıcı ID'si alınıyor");
        JsonPath jsonPath = response.jsonPath();
        userId = jsonPath.getInt("id");
        Assert.assertNotNull(userId, "Kullanıcı ID'si null");
    }

    @Step("Kullanıcının başarıyla oluşturulduğunu doğrula")
    public void verifyUserCreatedSuccessfully() {
        logger.info("Kullanıcının başarıyla oluşturulduğu doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");
        
        Assert.assertEquals(name, "Test User", "Kullanıcı adı beklendiği gibi değil");
        Assert.assertEquals(job, "QA Engineer", "Kullanıcı işi beklendiği gibi değil");
    }

    @Step("Kullanıcı güncelleme verilerini hazırla")
    public void prepareUpdateUserData() {
        logger.info("Kullanıcı güncelleme verileri hazırlanıyor");
        requestBody = new JSONObject();
        requestBody.put("name", "Updated User");
        requestBody.put("job", "Senior QA Engineer");
    }

    @Step("Kullanıcı güncelleme API'sine PUT isteği gönder")
    public void sendPutRequestToUpdateUserApi() {
        logger.info("Kullanıcı güncelleme API'sine PUT isteği gönderiliyor");
        response = given()
                .spec(requestSpec)
                .body(requestBody.toString())
                .when()
                .put("/users/" + userId);
    }

    @Step("Kullanıcının başarıyla güncellendiğini doğrula")
    public void verifyUserUpdatedSuccessfully() {
        logger.info("Kullanıcının başarıyla güncellendiği doğrulanıyor");
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");
        
        Assert.assertEquals(name, "Updated User", "Kullanıcı adı beklendiği gibi değil");
        Assert.assertEquals(job, "Senior QA Engineer", "Kullanıcı işi beklendiği gibi değil");
    }

    @Step("Kullanıcı silme API'sine DELETE isteği gönder")
    public void sendDeleteRequestToDeleteUserApi() {
        logger.info("Kullanıcı silme API'sine DELETE isteği gönderiliyor");
        response = given()
                .spec(requestSpec)
                .when()
                .delete("/users/" + userId);
    }

    @Step("Kullanıcının başarıyla silindiğini doğrula")
    public void verifyUserDeletedSuccessfully() {
        logger.info("Kullanıcının başarıyla silindiği doğrulanıyor");
        // Silinen kullanıcıyı kontrol et
        Response checkResponse = given()
                .spec(requestSpec)
                .when()
                .get("/users/" + userId);
        
        Assert.assertEquals(checkResponse.getStatusCode(), 404, "Silinen kullanıcı hala mevcut");
    }
}
