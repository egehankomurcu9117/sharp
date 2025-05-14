import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class SimpleAppiumTest {
    public static void main(String[] args) {
        try {
            // APK dosyasının yolu
            String apkPath = "/Users/egehankomurcu/Downloads/Logo WMS_1.61.00.00_APKPure.apk";
            File apkFile = new File(apkPath);
            
            // Appium sunucusu URL'si
            URL appiumServerURL = new URL("http://127.0.0.1:4723/wd/hub");
            
            // Desired capabilities
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Android Device");
            capabilities.setCapability("automationName", "UiAutomator2");
            
            // APK dosyasını kullan
            if (apkFile.exists()) {
                capabilities.setCapability("app", apkFile.getAbsolutePath());
                System.out.println("APK dosyası kullanılıyor: " + apkFile.getAbsolutePath());
            } else {
                // APK bulunamazsa paket ve aktivite kullan
                capabilities.setCapability("appPackage", "com.logo.wms");
                capabilities.setCapability("appActivity", "com.logo.wms.features.splash.SplashActivity");
                System.out.println("APK dosyası bulunamadı, paket ve aktivite kullanılıyor");
            }
            
            capabilities.setCapability("noReset", true);
            
            // AndroidDriver oluştur
            AndroidDriver driver = new AndroidDriver(appiumServerURL, capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            System.out.println("Appium driver başarıyla başlatıldı");
            
            // Uygulamanın başarıyla açıldığını doğrula
            WebElement serverAddressTitle = driver.findElement(By.id("com.logo.wms:id/alertTitle"));
            if (serverAddressTitle.isDisplayed()) {
                System.out.println("Uygulama başarıyla açıldı: " + serverAddressTitle.getText());
            } else {
                System.out.println("Uygulama başlığı görünmüyor");
            }
            
            // Sunucu adresi gir
            WebElement serverAddressField = driver.findElement(By.id("com.logo.wms:id/dialog_editText"));
            serverAddressField.clear();
            serverAddressField.sendKeys("https://wms-server.logo.com.tr");
            System.out.println("Sunucu adresi girildi");
            
            // OK butonuna tıkla
            WebElement okButton = driver.findElement(By.id("com.logo.wms:id/dialog_positiveButton"));
            okButton.click();
            System.out.println("OK butonuna tıklandı");
            
            // Birkaç saniye bekle
            Thread.sleep(5000);
            
            // Driver'ı kapat
            driver.quit();
            System.out.println("Test başarıyla tamamlandı");
            
        } catch (Exception e) {
            System.err.println("Test sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
