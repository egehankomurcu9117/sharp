package com.example.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * API için test sınıfı.
 * REST Assured kullanarak API işlevlerini test eder.
 * OOP kurallarına uygun, JSON formatında endpoint'leri kullanan yapı.
 */
public class RestAssuredTest extends RestAssuredBase {
    private static final Logger logger = LoggerFactory.getLogger(RestAssuredTest.class);
    private UserApi userApi;
    private int userId;

    @BeforeClass
    public void setUp() {
        logger.info("API testi başlatılıyor");
        userApi = new UserApi(requestSpec);
    }

    @Test(priority = 1)
    public void testGetUsers() {
        logger.info("Kullanıcı listesi alma testi çalıştırılıyor");

        // Tüm kullanıcıları listele ve kullanıcı listesinin boş olmadığını doğrula
        Response response = userApi.getAllUsers();
        userApi.verifyUserListNotEmpty(response);
    }

    @Test(priority = 2)
    public void testGetSingleUser() {
        logger.info("Tek kullanıcı alma testi çalıştırılıyor");

        // Belirli bir kullanıcıyı getir ve kullanıcı adının null olmadığını doğrula
        Response response = userApi.getSingleUser("1");
        userApi.verifyUserNameNotNull(response);
    }

    @Test(priority = 3)
    public void testCreateUser() {
        logger.info("Kullanıcı oluşturma testi çalıştırılıyor");

        // Yeni bir kullanıcı oluştur
        Response response = userApi.createUser("Test User", "QA Engineer");

        // Kullanıcı ID'sinin null olmadığını doğrula
        userId = userApi.verifyUserIdNotNull(response);

        // Kullanıcı adının beklendiği gibi olduğunu doğrula
        userApi.verifyUserName(response, "Test User");
    }

    @Test(priority = 4)
    public void testUpdateUser() {
        logger.info("Kullanıcı güncelleme testi çalıştırılıyor");

        // Kullanıcıyı güncelle
        Response response = userApi.updateUser("1", "Updated User", "Senior QA Engineer");

        // Kullanıcı adının beklendiği gibi olduğunu doğrula
        userApi.verifyUserName(response, "Updated User");
    }

    @Test(priority = 5)
    public void testDeleteUser() {
        logger.info("Kullanıcı silme testi çalıştırılıyor");

        // Kullanıcıyı sil
        userApi.deleteUser("1");
    }
}
