package com.erp.test.pages.login;

import com.erp.test.core.config.ConfigManager;
import com.erp.test.core.page.BasePage;
import com.erp.test.pages.common.DashboardPage;
import org.openqa.selenium.By;

/**
 * ERP sistemi giriş sayfası.
 * Kullanıcı girişi işlemlerini yönetir.
 */
public class LoginPage extends BasePage {
    // Locators
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("loginBtn");
    private final By errorMessage = By.id("errorMsg");
    private final By forgotPasswordLink = By.linkText("Şifremi Unuttum");
    private final By companyDropdown = By.id("companySelect");
    private final By rememberMeCheckbox = By.id("rememberMe");
    private final By languageDropdown = By.id("languageSelect");
    
    /**
     * Giriş sayfası constructor'ı.
     */
    public LoginPage() {
        super("Login");
    }
    
    /**
     * Giriş sayfasına gider.
     *
     * @return LoginPage instance
     */
    public LoginPage navigateToLoginPage() {
        String loginUrl = ConfigManager.getInstance().getBaseUrl() + "/login";
        logger.info("Navigating to login page: {}", loginUrl);
        driver.get(loginUrl);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Kullanıcı adını girer.
     *
     * @param username Kullanıcı adı
     * @return LoginPage instance
     */
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: {}", username);
        sendKeys(usernameInput, username);
        return this;
    }
    
    /**
     * Şifreyi girer.
     *
     * @param password Şifre
     * @return LoginPage instance
     */
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        sendKeys(passwordInput, password);
        return this;
    }
    
    /**
     * Şirket seçer.
     *
     * @param companyName Şirket adı
     * @return LoginPage instance
     */
    public LoginPage selectCompany(String companyName) {
        logger.info("Selecting company: {}", companyName);
        selectByVisibleText(companyDropdown, companyName);
        return this;
    }
    
    /**
     * Dil seçer.
     *
     * @param language Dil
     * @return LoginPage instance
     */
    public LoginPage selectLanguage(String language) {
        logger.info("Selecting language: {}", language);
        selectByVisibleText(languageDropdown, language);
        return this;
    }
    
    /**
     * "Beni Hatırla" seçeneğini ayarlar.
     *
     * @param remember Hatırlanacaksa true, değilse false
     * @return LoginPage instance
     */
    public LoginPage setRememberMe(boolean remember) {
        logger.info("Setting remember me to: {}", remember);
        WebElement checkbox = driver.findElement(rememberMeCheckbox);
        boolean isSelected = checkbox.isSelected();
        
        if (remember != isSelected) {
            click(rememberMeCheckbox);
        }
        
        return this;
    }
    
    /**
     * Giriş butonuna tıklar.
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);
        waitForPageLoad();
        
        // Check if login was successful by looking for error message
        if (isElementDisplayed(errorMessage)) {
            String error = getText(errorMessage);
            logger.error("Login failed: {}", error);
            throw new RuntimeException("Login failed: " + error);
        }
        
        logger.info("Login successful");
        return new DashboardPage();
    }
    
    /**
     * "Şifremi Unuttum" linkine tıklar.
     *
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage clickForgotPasswordLink() {
        logger.info("Clicking forgot password link");
        click(forgotPasswordLink);
        return new ForgotPasswordPage();
    }
    
    /**
     * Hata mesajının görüntülenip görüntülenmediğini kontrol eder.
     *
     * @return Hata mesajı görüntüleniyorsa true, değilse false
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isElementDisplayed(errorMessage);
    }
    
    /**
     * Hata mesajı metnini döndürür.
     *
     * @return Hata mesajı metni
     */
    public String getErrorMessageText() {
        logger.info("Getting error message text");
        return getText(errorMessage);
    }
    
    /**
     * Kullanıcı girişi yapar.
     *
     * @param username Kullanıcı adı
     * @param password Şifre
     * @param company Şirket adı
     * @return DashboardPage instance
     */
    public DashboardPage login(String username, String password, String company) {
        return navigateToLoginPage()
                .enterUsername(username)
                .enterPassword(password)
                .selectCompany(company)
                .clickLoginButton();
    }
    
    /**
     * Yapılandırma dosyasındaki bilgilerle kullanıcı girişi yapar.
     *
     * @return DashboardPage instance
     */
    public DashboardPage loginWithConfigCredentials() {
        String username = ConfigManager.getInstance().getUsername();
        String password = ConfigManager.getInstance().getPassword();
        String company = ConfigManager.getInstance().getCompany();
        
        logger.info("Logging in with config credentials. Username: {}, Company: {}", username, company);
        return login(username, password, company);
    }
}
