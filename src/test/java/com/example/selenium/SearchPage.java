package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

/**
 * Arama sayfası sınıfı.
 * Arama sayfası ile ilgili işlemleri içerir.
 */
public class SearchPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    
    /**
     * Arama sayfası sınıfı constructor'ı.
     * 
     * @param driver WebDriver nesnesi
     */
    public SearchPage(WebDriver driver) {
        super(driver, "searchPage");
    }
    
    /**
     * Arama kutusuna metin girer.
     * 
     * @param searchText Arama metni
     * @return SearchPage nesnesi
     */
    public SearchPage enterSearchText(String searchText) {
        logger.info("Arama kutusuna metin giriliyor: {}", searchText);
        sendKeys("searchBox", searchText);
        return this;
    }
    
    /**
     * Arama butonuna tıklar.
     * 
     * @return SearchPage nesnesi
     */
    public SearchPage clickSearchButton() {
        logger.info("Arama butonuna tıklanıyor");
        click("searchButton");
        return this;
    }
    
    /**
     * Arama sonuçlarının gösterildiğini doğrular.
     * 
     * @return SearchPage nesnesi
     */
    public SearchPage verifySearchResultsDisplayed() {
        logger.info("Arama sonuçlarının gösterildiği doğrulanıyor");
        Assert.assertTrue(isDisplayed("searchResults"), "Arama sonuçları görünmüyor");
        return this;
    }
    
    /**
     * En az bir sonuç olduğunu doğrular.
     * 
     * @return SearchPage nesnesi
     */
    public SearchPage verifyAtLeastOneResult() {
        logger.info("En az bir sonuç olduğu doğrulanıyor");
        List<WebElement> resultItems = findElements("resultItems");
        Assert.assertTrue(resultItems.size() > 0, "Arama sonuçları bulunamadı");
        return this;
    }
}
