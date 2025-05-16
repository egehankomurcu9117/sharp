package com.erp.test.tests;

import com.erp.test.core.driver.DriverManager;
import com.erp.test.core.reporting.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/**
 * Tüm test sınıfları için temel sınıf.
 * Test yaşam döngüsü yönetimini sağlar.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Test sınıfı başlamadan önce çalışır.
     * WebDriver'ı başlatır.
     */
    @BeforeClass
    public void setUp() {
        logger.info("Setting up test class: {}", this.getClass().getSimpleName());
        driver = DriverManager.getDriver();
    }
    
    /**
     * Her test metodu başlamadan önce çalışır.
     * Test raporlamasını başlatır.
     *
     * @param method Test metodu
     */
    @BeforeMethod
    public void beforeMethod(Method method) {
        String testName = method.getName();
        String testDescription = method.getAnnotation(org.testng.annotations.Test.class).description();
        
        if (testDescription == null || testDescription.isEmpty()) {
            testDescription = testName;
        }
        
        logger.info("Starting test: {}", testName);
        ExtentReportManager.createTest(testName, testDescription);
    }
    
    /**
     * Her test metodu bittikten sonra çalışır.
     * Test sonucunu rapora ekler.
     *
     * @param result Test sonucu
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test passed: {}", testName);
            ExtentReportManager.getTest().pass("Test passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", testName, result.getThrowable());
            ExtentReportManager.getTest().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test skipped: {}", testName);
            ExtentReportManager.getTest().skip(result.getThrowable());
        }
    }
    
    /**
     * Test sınıfı bittikten sonra çalışır.
     * WebDriver'ı kapatır ve raporları kaydeder.
     */
    @AfterClass
    public void tearDown() {
        logger.info("Tearing down test class: {}", this.getClass().getSimpleName());
        DriverManager.quitDriver();
        ExtentReportManager.flushReports();
    }
}
