package com.example.appium;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Sunucu adresi ekranı sınıfı.
 * Sunucu adresi ekranı ile ilgili işlemleri içerir.
 */
public class ServerAddressScreen extends BaseScreen {
    private static final Logger logger = LoggerFactory.getLogger(ServerAddressScreen.class);
    
    /**
     * Sunucu adresi ekranı sınıfı constructor'ı.
     * 
     * @param driver AndroidDriver nesnesi
     */
    public ServerAddressScreen(AndroidDriver driver) {
        super(driver, "serverAddressScreen");
    }
    
    /**
     * Uygulamanın başarıyla açıldığını doğrular.
     * 
     * @return ServerAddressScreen nesnesi
     */
    public ServerAddressScreen verifyAppLaunched() {
        logger.info("Uygulamanın başarıyla açıldığı doğrulanıyor");
        Assert.assertTrue(isDisplayed("serverAddressTitle"), "Sunucu adresi başlığı görünmüyor");
        Assert.assertEquals(getText("serverAddressTitle"), "Server Address", "Sunucu adresi başlığı beklendiği gibi değil");
        return this;
    }
    
    /**
     * Sunucu adresini girer.
     * 
     * @param serverAddress Sunucu adresi
     * @return ServerAddressScreen nesnesi
     */
    public ServerAddressScreen enterServerAddress(String serverAddress) {
        logger.info("Sunucu adresi giriliyor: {}", serverAddress);
        sendKeys("serverAddressField", serverAddress);
        return this;
    }
    
    /**
     * OK butonuna tıklar.
     * 
     * @return LoginScreen nesnesi
     */
    public LoginScreen clickOkButton() {
        logger.info("OK butonuna tıklanıyor");
        click("okButton");
        return new LoginScreen(driver);
    }
    
    /**
     * CANCEL butonuna tıklar.
     * 
     * @return ServerAddressScreen nesnesi
     */
    public ServerAddressScreen clickCancelButton() {
        logger.info("CANCEL butonuna tıklanıyor");
        click("cancelButton");
        return this;
    }
}
