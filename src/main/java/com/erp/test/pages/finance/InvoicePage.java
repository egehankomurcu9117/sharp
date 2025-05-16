package com.erp.test.pages.finance;

import com.erp.test.components.ERPDataTable;
import com.erp.test.core.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * ERP sistemi fatura sayfası.
 * Fatura işlemlerini yönetir.
 */
public class InvoicePage extends BasePage {
    // Locators
    private final By pageTitle = By.xpath("//h1[contains(text(),'Faturalar')]");
    private final By newInvoiceButton = By.id("newInvoiceBtn");
    private final By invoiceTable = By.id("invoiceTable");
    private final By searchInput = By.id("searchInput");
    private final By searchButton = By.id("searchBtn");
    private final By clearFilterButton = By.id("clearFilterBtn");
    private final By exportButton = By.id("exportBtn");
    private final By printButton = By.id("printBtn");
    private final By statusFilter = By.id("statusFilter");
    private final By dateRangeFilter = By.id("dateRangeFilter");
    private final By customerFilter = By.id("customerFilter");
    
    // Table column headers
    private final By invoiceNumberColumn = By.xpath("//th[contains(text(),'Fatura No')]");
    private final By customerColumn = By.xpath("//th[contains(text(),'Müşteri')]");
    private final By dateColumn = By.xpath("//th[contains(text(),'Tarih')]");
    private final By amountColumn = By.xpath("//th[contains(text(),'Tutar')]");
    private final By statusColumn = By.xpath("//th[contains(text(),'Durum')]");
    
    // Table row template
    private final String invoiceRowTemplate = "//tr[contains(.,'%s')]";
    private final String invoiceActionButtonTemplate = "//tr[contains(.,'%s')]//button[@title='%s']";
    
    // ERPDataTable component
    private ERPDataTable invoiceDataTable;
    
    /**
     * Fatura sayfası constructor'ı.
     */
    public InvoicePage() {
        super("Invoice");
        waitForPageLoad();
        initComponents();
    }
    
    /**
     * Bileşenleri başlatır.
     */
    private void initComponents() {
        invoiceDataTable = new ERPDataTable(driver, invoiceTable);
    }
    
    /**
     * Sayfanın yüklendiğini doğrular.
     *
     * @return InvoicePage instance
     */
    public InvoicePage verifyPageLoaded() {
        logger.info("Verifying invoice page is loaded");
        waitForElementVisible(pageTitle);
        waitForElementVisible(invoiceTable);
        return this;
    }
    
    /**
     * Yeni fatura butonuna tıklar.
     *
     * @return NewInvoicePage instance
     */
    public NewInvoicePage clickNewInvoiceButton() {
        logger.info("Clicking new invoice button");
        click(newInvoiceButton);
        return new NewInvoicePage();
    }
    
    /**
     * Fatura arar.
     *
     * @param searchTerm Arama terimi
     * @return InvoicePage instance
     */
    public InvoicePage searchInvoice(String searchTerm) {
        logger.info("Searching for invoice: {}", searchTerm);
        sendKeys(searchInput, searchTerm);
        click(searchButton);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Filtreleri temizler.
     *
     * @return InvoicePage instance
     */
    public InvoicePage clearFilters() {
        logger.info("Clearing filters");
        click(clearFilterButton);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Duruma göre filtreler.
     *
     * @param status Durum
     * @return InvoicePage instance
     */
    public InvoicePage filterByStatus(String status) {
        logger.info("Filtering by status: {}", status);
        selectByVisibleText(statusFilter, status);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Müşteriye göre filtreler.
     *
     * @param customer Müşteri
     * @return InvoicePage instance
     */
    public InvoicePage filterByCustomer(String customer) {
        logger.info("Filtering by customer: {}", customer);
        selectByVisibleText(customerFilter, customer);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Belirtilen faturanın görüntülenip görüntülenmediğini kontrol eder.
     *
     * @param invoiceNumber Fatura numarası
     * @return Fatura görüntüleniyorsa true, değilse false
     */
    public boolean isInvoiceDisplayed(String invoiceNumber) {
        logger.info("Checking if invoice is displayed: {}", invoiceNumber);
        WebElement invoiceRow = invoiceDataTable.findCellInColumn("Fatura No", invoiceNumber);
        return invoiceRow != null;
    }
    
    /**
     * Belirtilen faturayı açar.
     *
     * @param invoiceNumber Fatura numarası
     * @return InvoiceDetailPage instance
     */
    public InvoiceDetailPage openInvoice(String invoiceNumber) {
        logger.info("Opening invoice: {}", invoiceNumber);
        int rowIndex = invoiceDataTable.findRowIndexByColumnValue("Fatura No", invoiceNumber);
        
        if (rowIndex == -1) {
            logger.error("Invoice not found: {}", invoiceNumber);
            throw new IllegalArgumentException("Invoice not found: " + invoiceNumber);
        }
        
        invoiceDataTable.clickButtonInCell(rowIndex, "İşlemler", "Görüntüle");
        return new InvoiceDetailPage();
    }
    
    /**
     * Belirtilen faturayı düzenler.
     *
     * @param invoiceNumber Fatura numarası
     * @return EditInvoicePage instance
     */
    public EditInvoicePage editInvoice(String invoiceNumber) {
        logger.info("Editing invoice: {}", invoiceNumber);
        int rowIndex = invoiceDataTable.findRowIndexByColumnValue("Fatura No", invoiceNumber);
        
        if (rowIndex == -1) {
            logger.error("Invoice not found: {}", invoiceNumber);
            throw new IllegalArgumentException("Invoice not found: " + invoiceNumber);
        }
        
        invoiceDataTable.clickButtonInCell(rowIndex, "İşlemler", "Düzenle");
        return new EditInvoicePage();
    }
    
    /**
     * Belirtilen faturayı siler.
     *
     * @param invoiceNumber Fatura numarası
     * @return InvoicePage instance
     */
    public InvoicePage deleteInvoice(String invoiceNumber) {
        logger.info("Deleting invoice: {}", invoiceNumber);
        int rowIndex = invoiceDataTable.findRowIndexByColumnValue("Fatura No", invoiceNumber);
        
        if (rowIndex == -1) {
            logger.error("Invoice not found: {}", invoiceNumber);
            throw new IllegalArgumentException("Invoice not found: " + invoiceNumber);
        }
        
        invoiceDataTable.clickButtonInCell(rowIndex, "İşlemler", "Sil");
        
        // Handle confirmation dialog
        By confirmButton = By.xpath("//button[text()='Evet, Sil']");
        click(confirmButton);
        waitForPageLoad();
        
        return this;
    }
    
    /**
     * Tüm fatura numaralarını döndürür.
     *
     * @return Fatura numaraları listesi
     */
    public List<String> getAllInvoiceNumbers() {
        logger.info("Getting all invoice numbers");
        List<String> invoiceNumbers = new ArrayList<>();
        int rowCount = invoiceDataTable.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
            String invoiceNumber = invoiceDataTable.getCellText(i, "Fatura No");
            invoiceNumbers.add(invoiceNumber);
        }
        
        return invoiceNumbers;
    }
    
    /**
     * Fatura numarasına göre sıralar.
     *
     * @return InvoicePage instance
     */
    public InvoicePage sortByInvoiceNumber() {
        logger.info("Sorting by invoice number");
        click(invoiceNumberColumn);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Müşteriye göre sıralar.
     *
     * @return InvoicePage instance
     */
    public InvoicePage sortByCustomer() {
        logger.info("Sorting by customer");
        click(customerColumn);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Tarihe göre sıralar.
     *
     * @return InvoicePage instance
     */
    public InvoicePage sortByDate() {
        logger.info("Sorting by date");
        click(dateColumn);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Tutara göre sıralar.
     *
     * @return InvoicePage instance
     */
    public InvoicePage sortByAmount() {
        logger.info("Sorting by amount");
        click(amountColumn);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Duruma göre sıralar.
     *
     * @return InvoicePage instance
     */
    public InvoicePage sortByStatus() {
        logger.info("Sorting by status");
        click(statusColumn);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Faturaları dışa aktarır.
     *
     * @return InvoicePage instance
     */
    public InvoicePage exportInvoices() {
        logger.info("Exporting invoices");
        click(exportButton);
        // Wait for export to complete
        sleep(2);
        return this;
    }
    
    /**
     * Faturaları yazdırır.
     *
     * @return InvoicePage instance
     */
    public InvoicePage printInvoices() {
        logger.info("Printing invoices");
        click(printButton);
        // Wait for print dialog
        sleep(2);
        return this;
    }
}
