package com.example.gauge;

import com.example.selenium.*;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.AfterScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web uygulaması için Gauge adım implementasyonları.
 * Page Object Model (POM) desenini kullanarak sayfa sınıflarını çağırır.
 */
public class WebSteps extends SeleniumBase {
    private static final Logger logger = LoggerFactory.getLogger(WebSteps.class);

    // Sayfa nesneleri
    private HomePage homePage;
    private LoginPage loginPage;
    private SearchPage searchPage;
    private UserMenu userMenu;

    @BeforeScenario
    public void setUp() {
        logger.info("Web senaryosu başlatılıyor");
        driver = initializeChromeDriver();

        // Sayfa nesnelerini oluştur
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        userMenu = new UserMenu(driver);
    }

    @Step("Web sitesini aç")
    public void openWebsite() {
        logger.info("Web sitesi açılıyor");
        homePage.open();
    }

    @Step("Ana sayfanın yüklendiğini doğrula")
    public void verifyHomePageLoaded() {
        logger.info("Ana sayfanın yüklendiği doğrulanıyor");
        homePage.verifyPageLoaded();
    }

    @Step("Giriş sayfasına git")
    public void goToLoginPage() {
        logger.info("Giriş sayfasına gidiliyor");
        homePage.goToLoginPage();
    }

    @Step("Kullanıcı adı olarak <username> gir")
    public void enterUsername(String username) {
        logger.info("Kullanıcı adı giriliyor: {}", username);
        loginPage.enterUsername(username);
    }

    @Step("Şifre olarak <password> gir")
    public void enterPassword(String password) {
        logger.info("Şifre giriliyor");
        loginPage.enterPassword(password);
    }

    @Step("Giriş butonuna tıkla")
    public void clickLoginButton() {
        logger.info("Giriş butonuna tıklanıyor");
        loginPage.clickLoginButton();
    }

    @Step("Başarılı giriş mesajını doğrula")
    public void verifyLoginSuccess() {
        logger.info("Başarılı giriş mesajı doğrulanıyor");
        loginPage.verifyLoginSuccess();
    }

    @Step("Arama kutusuna <searchText> yaz")
    public void enterSearchText(String searchText) {
        logger.info("Arama kutusuna metin giriliyor: {}", searchText);
        searchPage.enterSearchText(searchText);
    }

    @Step("Arama butonuna tıkla")
    public void clickSearchButton() {
        logger.info("Arama butonuna tıklanıyor");
        searchPage.clickSearchButton();
    }

    @Step("Arama sonuçlarının gösterildiğini doğrula")
    public void verifySearchResultsDisplayed() {
        logger.info("Arama sonuçlarının gösterildiği doğrulanıyor");
        searchPage.verifySearchResultsDisplayed();
    }

    @Step("En az bir sonuç olduğunu doğrula")
    public void verifyAtLeastOneResult() {
        logger.info("En az bir sonuç olduğu doğrulanıyor");
        searchPage.verifyAtLeastOneResult();
    }

    @Step("Kullanıcı menüsünü aç")
    public void openUserMenu() {
        logger.info("Kullanıcı menüsü açılıyor");
        userMenu.open();
    }

    @Step("<optionName> seçeneğine tıkla")
    public void clickOption(String optionName) {
        logger.info("Seçeneğe tıklanıyor: {}", optionName);
        userMenu.clickOption(optionName);
    }

    @Step("Çıkış işleminin başarılı olduğunu doğrula")
    public void verifyLogoutSuccess() {
        logger.info("Çıkış işleminin başarılı olduğu doğrulanıyor");
        userMenu.verifyLogoutSuccess();
    }

    @AfterScenario
    public void tearDown() {
        logger.info("Web senaryosu sonlandırılıyor");
        quitDriver();
    }
}
