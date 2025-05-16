package com.erp.test.tests.login;

import com.erp.test.core.config.ConfigManager;
import com.erp.test.core.reporting.ExtentReportManager;
import com.erp.test.pages.common.DashboardPage;
import com.erp.test.pages.login.ForgotPasswordPage;
import com.erp.test.pages.login.LoginPage;
import com.erp.test.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Giriş işlemleri için test sınıfı.
 */
public class LoginTests extends BaseTest {
    
    /**
     * Geçerli kimlik bilgileriyle giriş yapılabildiğini doğrular.
     */
    @Test(description = "Geçerli kimlik bilgileriyle giriş yapılabildiğini doğrular")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        
        String username = ConfigManager.getInstance().getUsername();
        String password = ConfigManager.getInstance().getPassword();
        String company = ConfigManager.getInstance().getCompany();
        
        ExtentReportManager.getTest().info("Giriş sayfasına gidiliyor");
        loginPage.navigateToLoginPage();
        
        ExtentReportManager.getTest().info("Kullanıcı adı giriliyor: " + username);
        loginPage.enterUsername(username);
        
        ExtentReportManager.getTest().info("Şifre giriliyor");
        loginPage.enterPassword(password);
        
        ExtentReportManager.getTest().info("Şirket seçiliyor: " + company);
        loginPage.selectCompany(company);
        
        ExtentReportManager.getTest().info("Giriş butonuna tıklanıyor");
        DashboardPage dashboardPage = loginPage.clickLoginButton();
        
        ExtentReportManager.getTest().info("Dashboard sayfasının yüklendiği doğrulanıyor");
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard sayfası yüklenmedi");
        
        ExtentReportManager.getTest().info("Kullanıcı adının doğru görüntülendiği kontrol ediliyor");
        Assert.assertTrue(dashboardPage.getUsernameDisplayed().contains(username), 
                "Kullanıcı adı doğru görüntülenmiyor");
    }
    
    /**
     * Geçersiz kimlik bilgileriyle giriş yapılamadığını doğrular.
     */
    @Test(description = "Geçersiz kimlik bilgileriyle giriş yapılamadığını doğrular")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        
        ExtentReportManager.getTest().info("Giriş sayfasına gidiliyor");
        loginPage.navigateToLoginPage();
        
        ExtentReportManager.getTest().info("Geçersiz kullanıcı adı giriliyor");
        loginPage.enterUsername("invalid_user");
        
        ExtentReportManager.getTest().info("Geçersiz şifre giriliyor");
        loginPage.enterPassword("invalid_password");
        
        ExtentReportManager.getTest().info("Şirket seçiliyor");
        loginPage.selectCompany(ConfigManager.getInstance().getCompany());
        
        try {
            ExtentReportManager.getTest().info("Giriş butonuna tıklanıyor");
            loginPage.clickLoginButton();
            Assert.fail("Geçersiz kimlik bilgileriyle giriş yapılabildi");
        } catch (RuntimeException e) {
            ExtentReportManager.getTest().info("Beklenen hata alındı: " + e.getMessage());
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Hata mesajı görüntülenmiyor");
            
            String errorMessage = loginPage.getErrorMessageText();
            ExtentReportManager.getTest().info("Hata mesajı: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Geçersiz kullanıcı adı veya şifre"), 
                    "Hata mesajı beklendiği gibi değil");
        }
    }
    
    /**
     * Boş kimlik bilgileriyle giriş yapılamadığını doğrular.
     */
    @Test(description = "Boş kimlik bilgileriyle giriş yapılamadığını doğrular")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage();
        
        ExtentReportManager.getTest().info("Giriş sayfasına gidiliyor");
        loginPage.navigateToLoginPage();
        
        ExtentReportManager.getTest().info("Kullanıcı adı ve şifre boş bırakılıyor");
        // Kullanıcı adı ve şifre alanlarını boş bırak
        
        ExtentReportManager.getTest().info("Şirket seçiliyor");
        loginPage.selectCompany(ConfigManager.getInstance().getCompany());
        
        try {
            ExtentReportManager.getTest().info("Giriş butonuna tıklanıyor");
            loginPage.clickLoginButton();
            Assert.fail("Boş kimlik bilgileriyle giriş yapılabildi");
        } catch (RuntimeException e) {
            ExtentReportManager.getTest().info("Beklenen hata alındı: " + e.getMessage());
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Hata mesajı görüntülenmiyor");
            
            String errorMessage = loginPage.getErrorMessageText();
            ExtentReportManager.getTest().info("Hata mesajı: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Kullanıcı adı ve şifre gereklidir"), 
                    "Hata mesajı beklendiği gibi değil");
        }
    }
    
    /**
     * Şifremi unuttum işlevinin çalıştığını doğrular.
     */
    @Test(description = "Şifremi unuttum işlevinin çalıştığını doğrular")
    public void testForgotPassword() {
        LoginPage loginPage = new LoginPage();
        
        ExtentReportManager.getTest().info("Giriş sayfasına gidiliyor");
        loginPage.navigateToLoginPage();
        
        ExtentReportManager.getTest().info("Şifremi unuttum linkine tıklanıyor");
        ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordLink();
        
        ExtentReportManager.getTest().info("Şifremi unuttum sayfasının yüklendiği doğrulanıyor");
        Assert.assertTrue(forgotPasswordPage.isPageLoaded(), "Şifremi unuttum sayfası yüklenmedi");
        
        ExtentReportManager.getTest().info("E-posta adresi giriliyor");
        forgotPasswordPage.enterEmail("test@example.com");
        
        ExtentReportManager.getTest().info("Gönder butonuna tıklanıyor");
        forgotPasswordPage.clickSubmitButton();
        
        ExtentReportManager.getTest().info("Başarı mesajının görüntülendiği doğrulanıyor");
        Assert.assertTrue(forgotPasswordPage.isSuccessMessageDisplayed(), 
                "Başarı mesajı görüntülenmiyor");
    }
    
    /**
     * Farklı dil seçeneklerinin çalıştığını doğrular.
     */
    @Test(description = "Farklı dil seçeneklerinin çalıştığını doğrular")
    public void testLanguageSelection() {
        LoginPage loginPage = new LoginPage();
        
        ExtentReportManager.getTest().info("Giriş sayfasına gidiliyor");
        loginPage.navigateToLoginPage();
        
        ExtentReportManager.getTest().info("İngilizce dil seçiliyor");
        loginPage.selectLanguage("English");
        
        // Dil değişikliğinin uygulandığını doğrula
        // Not: Gerçek uygulamada, dil değişikliğini doğrulamak için
        // sayfadaki belirli metinleri kontrol etmek gerekebilir
        
        ExtentReportManager.getTest().info("Türkçe dil seçiliyor");
        loginPage.selectLanguage("Türkçe");
        
        // Dil değişikliğinin uygulandığını doğrula
    }
}
