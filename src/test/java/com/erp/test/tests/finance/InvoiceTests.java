package com.erp.test.tests.finance;

import com.erp.test.core.reporting.ExtentReportManager;
import com.erp.test.data.models.InvoiceData;
import com.erp.test.data.models.InvoiceItemData;
import com.erp.test.pages.common.DashboardPage;
import com.erp.test.pages.finance.EditInvoicePage;
import com.erp.test.pages.finance.InvoiceDetailPage;
import com.erp.test.pages.finance.InvoicePage;
import com.erp.test.pages.finance.NewInvoicePage;
import com.erp.test.pages.login.LoginPage;
import com.erp.test.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Fatura işlemleri için test sınıfı.
 */
@Epic("ERP Sistemi")
@Feature("Fatura Yönetimi")
public class InvoiceTests extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private InvoicePage invoicePage;

    /**
     * Her test metodundan önce çalışır.
     * Kullanıcı girişi yapar ve fatura sayfasına gider.
     */
    @BeforeMethod
    public void navigateToInvoicePage() {
        loginPage = new LoginPage();

        ExtentReportManager.getTest().info("Kullanıcı girişi yapılıyor");
        dashboardPage = loginPage.loginWithConfigCredentials();

        ExtentReportManager.getTest().info("Fatura sayfasına gidiliyor");
        invoicePage = dashboardPage.navigateToInvoicePage();

        ExtentReportManager.getTest().info("Fatura sayfasının yüklendiği doğrulanıyor");
        invoicePage.verifyPageLoaded();
    }

    /**
     * Yeni fatura oluşturma işlemini doğrular.
     */
    @Test(description = "Yeni fatura oluşturma işlemini doğrular")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fatura Oluşturma")
    @Description("Yeni bir fatura oluşturulabildiğini ve fatura listesinde görüntülendiğini doğrular")
    public void testCreateInvoice() {
        // Test verileri
        String invoiceNumber = "INV-" + System.currentTimeMillis();
        String customerName = "Test Müşteri";
        String invoiceDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<InvoiceItemData> items = new ArrayList<>();
        items.add(new InvoiceItemData("Ürün A", 2, 100.00));
        items.add(new InvoiceItemData("Ürün B", 1, 50.00));

        InvoiceData invoiceData = new InvoiceData(invoiceNumber, customerName, invoiceDate, items);

        ExtentReportManager.getTest().info("Yeni fatura oluşturuluyor");
        NewInvoicePage newInvoicePage = invoicePage.clickNewInvoiceButton();

        ExtentReportManager.getTest().info("Fatura numarası giriliyor: " + invoiceNumber);
        newInvoicePage.enterInvoiceNumber(invoiceNumber);

        ExtentReportManager.getTest().info("Müşteri seçiliyor: " + customerName);
        newInvoicePage.selectCustomer(customerName);

        ExtentReportManager.getTest().info("Fatura tarihi giriliyor: " + invoiceDate);
        newInvoicePage.enterInvoiceDate(invoiceDate);

        // Fatura kalemlerini ekle
        for (InvoiceItemData item : invoiceData.getItems()) {
            ExtentReportManager.getTest().info("Fatura kalemi ekleniyor: " + item.getProductName() +
                    ", Miktar: " + item.getQuantity() + ", Birim Fiyat: " + item.getUnitPrice());
            newInvoicePage.addInvoiceItem(item.getProductName(), item.getQuantity(), item.getUnitPrice());
        }

        ExtentReportManager.getTest().info("Fatura kaydediliyor");
        invoicePage = newInvoicePage.saveInvoice();

        ExtentReportManager.getTest().info("Faturanın listelendiği doğrulanıyor");
        Assert.assertTrue(invoicePage.isInvoiceDisplayed(invoiceNumber),
                "Oluşturulan fatura listede görüntülenmiyor");

        // Temizlik: Oluşturulan faturayı sil
        ExtentReportManager.getTest().info("Test temizliği: Oluşturulan fatura siliniyor");
        invoicePage.deleteInvoice(invoiceNumber);
    }

    /**
     * Fatura arama işlemini doğrular.
     */
    @Test(description = "Fatura arama işlemini doğrular")
    @Severity(SeverityLevel.NORMAL)
    @Story("Fatura Arama")
    @Description("Fatura arama işlevinin çalıştığını ve doğru sonuçları döndürdüğünü doğrular")
    public void testSearchInvoice() {
        // Önce bir fatura oluştur
        String invoiceNumber = "SEARCH-" + System.currentTimeMillis();
        String customerName = "Arama Test Müşterisi";
        String invoiceDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<InvoiceItemData> items = new ArrayList<>();
        items.add(new InvoiceItemData("Arama Ürünü", 1, 100.00));

        ExtentReportManager.getTest().info("Test için fatura oluşturuluyor");
        NewInvoicePage newInvoicePage = invoicePage.clickNewInvoiceButton();
        newInvoicePage.enterInvoiceNumber(invoiceNumber)
                     .selectCustomer(customerName)
                     .enterInvoiceDate(invoiceDate)
                     .addInvoiceItem(items.get(0).getProductName(), items.get(0).getQuantity(), items.get(0).getUnitPrice())
                     .saveInvoice();

        // Fatura araması yap
        ExtentReportManager.getTest().info("Fatura araması yapılıyor: " + invoiceNumber);
        invoicePage.searchInvoice(invoiceNumber);

        ExtentReportManager.getTest().info("Arama sonuçlarının doğruluğu kontrol ediliyor");
        Assert.assertTrue(invoicePage.isInvoiceDisplayed(invoiceNumber),
                "Aranan fatura bulunamadı");

        // Filtreleri temizle
        ExtentReportManager.getTest().info("Filtreler temizleniyor");
        invoicePage.clearFilters();

        // Temizlik: Oluşturulan faturayı sil
        ExtentReportManager.getTest().info("Test temizliği: Oluşturulan fatura siliniyor");
        invoicePage.deleteInvoice(invoiceNumber);
    }

    /**
     * Fatura düzenleme işlemini doğrular.
     */
    @Test(description = "Fatura düzenleme işlemini doğrular")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fatura Düzenleme")
    @Description("Fatura düzenleme işlevinin çalıştığını ve değişikliklerin kaydedildiğini doğrular")
    public void testEditInvoice() {
        // Önce bir fatura oluştur
        String invoiceNumber = "EDIT-" + System.currentTimeMillis();
        String customerName = "Düzenleme Test Müşterisi";
        String invoiceDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<InvoiceItemData> items = new ArrayList<>();
        items.add(new InvoiceItemData("Düzenleme Ürünü", 1, 100.00));

        ExtentReportManager.getTest().info("Test için fatura oluşturuluyor");
        NewInvoicePage newInvoicePage = invoicePage.clickNewInvoiceButton();
        newInvoicePage.enterInvoiceNumber(invoiceNumber)
                     .selectCustomer(customerName)
                     .enterInvoiceDate(invoiceDate)
                     .addInvoiceItem(items.get(0).getProductName(), items.get(0).getQuantity(), items.get(0).getUnitPrice())
                     .saveInvoice();

        // Faturayı düzenle
        ExtentReportManager.getTest().info("Fatura düzenleniyor: " + invoiceNumber);
        EditInvoicePage editInvoicePage = invoicePage.editInvoice(invoiceNumber);

        String updatedCustomerName = "Güncellenmiş Müşteri";
        ExtentReportManager.getTest().info("Müşteri güncelleniyor: " + updatedCustomerName);
        editInvoicePage.selectCustomer(updatedCustomerName);

        ExtentReportManager.getTest().info("Yeni fatura kalemi ekleniyor");
        editInvoicePage.addInvoiceItem("Yeni Ürün", 2, 75.00);

        ExtentReportManager.getTest().info("Fatura kaydediliyor");
        invoicePage = editInvoicePage.saveInvoice();

        // Düzenlenen faturayı kontrol et
        ExtentReportManager.getTest().info("Düzenlenen faturanın detayları kontrol ediliyor");
        InvoiceDetailPage detailPage = invoicePage.openInvoice(invoiceNumber);

        Assert.assertEquals(detailPage.getCustomerName(), updatedCustomerName,
                "Müşteri adı güncellenmemiş");
        Assert.assertEquals(detailPage.getInvoiceItemCount(), 2,
                "Fatura kalemi sayısı beklendiği gibi değil");

        // Fatura sayfasına geri dön
        ExtentReportManager.getTest().info("Fatura listesine geri dönülüyor");
        invoicePage = detailPage.backToInvoiceList();

        // Temizlik: Oluşturulan faturayı sil
        ExtentReportManager.getTest().info("Test temizliği: Oluşturulan fatura siliniyor");
        invoicePage.deleteInvoice(invoiceNumber);
    }

    /**
     * Fatura silme işlemini doğrular.
     */
    @Test(description = "Fatura silme işlemini doğrular")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fatura Silme")
    @Description("Fatura silme işlevinin çalıştığını ve silinen faturanın listede görüntülenmediğini doğrular")
    public void testDeleteInvoice() {
        // Önce bir fatura oluştur
        String invoiceNumber = "DELETE-" + System.currentTimeMillis();
        String customerName = "Silme Test Müşterisi";
        String invoiceDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<InvoiceItemData> items = new ArrayList<>();
        items.add(new InvoiceItemData("Silme Ürünü", 1, 100.00));

        ExtentReportManager.getTest().info("Test için fatura oluşturuluyor");
        NewInvoicePage newInvoicePage = invoicePage.clickNewInvoiceButton();
        newInvoicePage.enterInvoiceNumber(invoiceNumber)
                     .selectCustomer(customerName)
                     .enterInvoiceDate(invoiceDate)
                     .addInvoiceItem(items.get(0).getProductName(), items.get(0).getQuantity(), items.get(0).getUnitPrice())
                     .saveInvoice();

        // Faturayı sil
        ExtentReportManager.getTest().info("Fatura siliniyor: " + invoiceNumber);
        invoicePage.deleteInvoice(invoiceNumber);

        // Silinen faturanın artık görüntülenmediğini doğrula
        ExtentReportManager.getTest().info("Faturanın silindiği doğrulanıyor");
        Assert.assertFalse(invoicePage.isInvoiceDisplayed(invoiceNumber),
                "Silinen fatura hala listede görüntüleniyor");
    }
}
