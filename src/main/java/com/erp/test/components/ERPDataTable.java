package com.erp.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ERP sistemlerindeki karmaşık veri tabloları için özel bileşen.
 * Tablo işlemlerini kolaylaştırmak için tasarlanmıştır.
 */
public class ERPDataTable {
    private static final Logger logger = LoggerFactory.getLogger(ERPDataTable.class);
    
    private final WebDriver driver;
    private final By tableLocator;
    private WebElement tableElement;
    private List<String> headerTexts;
    
    /**
     * ERP veri tablosu constructor'ı.
     *
     * @param driver WebDriver instance
     * @param tableLocator Tablo locator'ı
     */
    public ERPDataTable(WebDriver driver, By tableLocator) {
        this.driver = driver;
        this.tableLocator = tableLocator;
        initTable();
    }
    
    /**
     * Tabloyu başlatır ve başlık metinlerini alır.
     */
    private void initTable() {
        logger.debug("Initializing ERP data table: {}", tableLocator);
        tableElement = driver.findElement(tableLocator);
        headerTexts = getHeaderTexts();
    }
    
    /**
     * Tablo başlıklarının metinlerini alır.
     *
     * @return Başlık metinleri listesi
     */
    private List<String> getHeaderTexts() {
        List<String> headers = new ArrayList<>();
        List<WebElement> headerElements = tableElement.findElements(By.xpath(".//th"));
        
        for (WebElement header : headerElements) {
            headers.add(header.getText().trim());
        }
        
        logger.debug("Table headers: {}", headers);
        return headers;
    }
    
    /**
     * Belirtilen sütun adına göre sütun indeksini bulur.
     *
     * @param columnName Sütun adı
     * @return Sütun indeksi (0-tabanlı)
     */
    private int getColumnIndex(String columnName) {
        for (int i = 0; i < headerTexts.size(); i++) {
            if (headerTexts.get(i).equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        logger.error("Column not found: {}", columnName);
        throw new IllegalArgumentException("Column not found: " + columnName);
    }
    
    /**
     * Belirtilen satır ve sütundaki hücreye tıklar.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnIndex Sütun indeksi (0-tabanlı)
     */
    public void clickCell(int rowIndex, int columnIndex) {
        logger.debug("Clicking cell at row {}, column {}", rowIndex, columnIndex);
        WebElement cell = getCell(rowIndex, columnIndex);
        cell.click();
    }
    
    /**
     * Belirtilen satır ve sütun adındaki hücreye tıklar.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnName Sütun adı
     */
    public void clickCell(int rowIndex, String columnName) {
        int columnIndex = getColumnIndex(columnName);
        clickCell(rowIndex, columnIndex);
    }
    
    /**
     * Belirtilen satır ve sütundaki hücreyi döndürür.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnIndex Sütun indeksi (0-tabanlı)
     * @return Hücre elementi
     */
    public WebElement getCell(int rowIndex, int columnIndex) {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tbody/tr"));
        
        if (rowIndex >= rows.size()) {
            logger.error("Row index out of bounds: {}, max: {}", rowIndex, rows.size() - 1);
            throw new IndexOutOfBoundsException("Row index out of bounds: " + rowIndex);
        }
        
        WebElement row = rows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.xpath(".//td"));
        
        if (columnIndex >= cells.size()) {
            logger.error("Column index out of bounds: {}, max: {}", columnIndex, cells.size() - 1);
            throw new IndexOutOfBoundsException("Column index out of bounds: " + columnIndex);
        }
        
        return cells.get(columnIndex);
    }
    
    /**
     * Belirtilen satır ve sütundaki hücre metnini döndürür.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnIndex Sütun indeksi (0-tabanlı)
     * @return Hücre metni
     */
    public String getCellText(int rowIndex, int columnIndex) {
        WebElement cell = getCell(rowIndex, columnIndex);
        return cell.getText().trim();
    }
    
    /**
     * Belirtilen satır ve sütun adındaki hücre metnini döndürür.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnName Sütun adı
     * @return Hücre metni
     */
    public String getCellText(int rowIndex, String columnName) {
        int columnIndex = getColumnIndex(columnName);
        return getCellText(rowIndex, columnIndex);
    }
    
    /**
     * Belirtilen sütunda belirtilen metni içeren satırı bulur.
     *
     * @param columnName Sütun adı
     * @param text Aranacak metin
     * @return Satır indeksi, bulunamazsa -1
     */
    public int findRowIndexByColumnValue(String columnName, String text) {
        int columnIndex = getColumnIndex(columnName);
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tbody/tr"));
        
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            
            if (columnIndex < cells.size()) {
                String cellText = cells.get(columnIndex).getText().trim();
                if (cellText.equals(text)) {
                    return i;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Belirtilen satırın tüm hücre metinlerini bir harita olarak döndürür.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @return Sütun adı -> Hücre metni haritası
     */
    public Map<String, String> getRowData(int rowIndex) {
        Map<String, String> rowData = new HashMap<>();
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tbody/tr"));
        
        if (rowIndex >= rows.size()) {
            logger.error("Row index out of bounds: {}, max: {}", rowIndex, rows.size() - 1);
            throw new IndexOutOfBoundsException("Row index out of bounds: " + rowIndex);
        }
        
        WebElement row = rows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.xpath(".//td"));
        
        for (int i = 0; i < Math.min(cells.size(), headerTexts.size()); i++) {
            rowData.put(headerTexts.get(i), cells.get(i).getText().trim());
        }
        
        return rowData;
    }
    
    /**
     * Tablodaki satır sayısını döndürür.
     *
     * @return Satır sayısı
     */
    public int getRowCount() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tbody/tr"));
        return rows.size();
    }
    
    /**
     * Belirtilen sütuna göre tabloyu sıralar.
     *
     * @param columnName Sütun adı
     */
    public void sortByColumn(String columnName) {
        logger.debug("Sorting table by column: {}", columnName);
        int columnIndex = getColumnIndex(columnName);
        WebElement headerElement = tableElement.findElements(By.xpath(".//th")).get(columnIndex);
        headerElement.click();
    }
    
    /**
     * Belirtilen sütunda belirtilen metni içeren hücreyi bulur.
     *
     * @param columnName Sütun adı
     * @param text Aranacak metin
     * @return Hücre elementi, bulunamazsa null
     */
    public WebElement findCellInColumn(String columnName, String text) {
        int rowIndex = findRowIndexByColumnValue(columnName, text);
        if (rowIndex != -1) {
            int columnIndex = getColumnIndex(columnName);
            return getCell(rowIndex, columnIndex);
        }
        return null;
    }
    
    /**
     * Belirtilen satırdaki belirtilen sütunda bulunan düğmeye tıklar.
     *
     * @param rowIndex Satır indeksi (0-tabanlı)
     * @param columnName Sütun adı
     * @param buttonText Düğme metni veya title özelliği
     */
    public void clickButtonInCell(int rowIndex, String columnName, String buttonText) {
        logger.debug("Clicking button '{}' in cell at row {}, column {}", buttonText, rowIndex, columnName);
        WebElement cell = getCell(rowIndex, getColumnIndex(columnName));
        WebElement button = cell.findElement(By.xpath(".//button[contains(text(),'" + buttonText + "') or @title='" + buttonText + "']"));
        button.click();
    }
}
