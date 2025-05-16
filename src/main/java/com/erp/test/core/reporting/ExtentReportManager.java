package com.erp.test.core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.erp.test.core.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports için yönetici sınıf.
 * Singleton tasarım deseni kullanılarak oluşturulmuştur.
 */
public class ExtentReportManager {
    private static final Logger logger = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    private ExtentReportManager() {
        // Utility class, no instances
    }

    /**
     * ExtentReports instance'ını döndürür.
     * Eğer henüz oluşturulmamışsa, yeni bir instance oluşturur.
     *
     * @return ExtentReports instance
     */
    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            initExtentReports();
        }
        return extentReports;
    }

    /**
     * ExtentReports'u başlatır ve yapılandırır.
     */
    private static void initExtentReports() {
        String reportPath = ConfigManager.getInstance().getReportPath();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportFilePath = reportPath + "/ERP_Test_Report_" + timestamp + ".html";
        
        // Ensure directory exists
        File reportDir = new File(reportPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        logger.info("Initializing ExtentReports at: {}", reportFilePath);
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        sparkReporter.config().setDocumentTitle("ERP Test Automation Report");
        sparkReporter.config().setReportName("ERP Test Results");
        
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        
        // Set system info
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser", ConfigManager.getInstance().getBrowser());
        extentReports.setSystemInfo("Environment", ConfigManager.getInstance().getEnvironment());
        extentReports.setSystemInfo("Base URL", ConfigManager.getInstance().getBaseUrl());
        
        logger.info("ExtentReports initialized successfully");
    }

    /**
     * Yeni bir test oluşturur ve ThreadLocal'a kaydeder.
     *
     * @param testName Test adı
     * @param description Test açıklaması
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = getExtentReports().createTest(testName, description);
        extentTestThreadLocal.set(test);
        return test;
    }

    /**
     * Mevcut thread için ExtentTest instance'ını döndürür.
     *
     * @return ExtentTest instance
     */
    public static synchronized ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }

    /**
     * Tüm raporları kaydeder ve kaynakları temizler.
     */
    public static synchronized void flushReports() {
        if (extentReports != null) {
            logger.info("Flushing ExtentReports");
            extentReports.flush();
        }
    }
}
