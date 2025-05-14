package com.example.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

/**
 * REST Assured test sınıfları için temel sınıf.
 * API istekleri için temel yapılandırmayı sağlar.
 */
public class RestAssuredBase {
    private static final Logger logger = LoggerFactory.getLogger(RestAssuredBase.class);

    // API temel URL'si - JSONPlaceholder API'sini kullanıyoruz
    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        logger.info("REST Assured testi başlatılıyor");
        configureRestAssured();
    }

    /**
     * REST Assured için temel yapılandırmayı ayarlar.
     */
    private void configureRestAssured() {
        RestAssured.baseURI = BASE_URL;

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Accept", ContentType.JSON.toString())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        logger.info("REST Assured yapılandırması tamamlandı");
    }
}
