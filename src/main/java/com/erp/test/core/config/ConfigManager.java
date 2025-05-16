package com.erp.test.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Test yapılandırmasını yöneten sınıf.
 * Singleton tasarım deseni kullanılarak oluşturulmuştur.
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE = "src/test/resources/config/config.properties";
    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration from {}: {}", CONFIG_FILE, e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse property '{}' as integer: {}", key, e.getMessage());
            throw new RuntimeException("Failed to parse property as integer", e);
        }
    }

    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    // Convenience methods for common properties
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless");
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait");
    }

    public int getExplicitWait() {
        return getIntProperty("explicit.wait");
    }

    public int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout");
    }

    public String getUsername() {
        return getProperty("username");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getCompany() {
        return getProperty("company");
    }

    public String getEnvironment() {
        return getProperty("environment", "test");
    }

    public String getTestDataPath() {
        return getProperty("test.data.path");
    }

    public String getReportPath() {
        return getProperty("report.path");
    }

    public String getScreenshotPath() {
        return getProperty("screenshot.path");
    }
}
