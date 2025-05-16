package com.erp.test.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Fatura verilerini temsil eden sınıf.
 */
public class InvoiceData {
    private String invoiceNumber;
    private String customerName;
    private String invoiceDate;
    private List<InvoiceItemData> items;
    private double totalAmount;
    private String status;
    private String notes;
    
    /**
     * Varsayılan constructor.
     */
    public InvoiceData() {
        this.items = new ArrayList<>();
    }
    
    /**
     * Parametreli constructor.
     *
     * @param invoiceNumber Fatura numarası
     * @param customerName Müşteri adı
     * @param invoiceDate Fatura tarihi
     * @param items Fatura kalemleri
     */
    public InvoiceData(String invoiceNumber, String customerName, String invoiceDate, List<InvoiceItemData> items) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        this.items = items;
        this.calculateTotalAmount();
        this.status = "Taslak";
    }
    
    /**
     * Toplam tutarı hesaplar.
     */
    private void calculateTotalAmount() {
        this.totalAmount = 0;
        for (InvoiceItemData item : items) {
            this.totalAmount += item.getLineTotal();
        }
    }
    
    /**
     * Fatura numarasını döndürür.
     *
     * @return Fatura numarası
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    /**
     * Fatura numarasını ayarlar.
     *
     * @param invoiceNumber Fatura numarası
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    /**
     * Müşteri adını döndürür.
     *
     * @return Müşteri adı
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Müşteri adını ayarlar.
     *
     * @param customerName Müşteri adı
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * Fatura tarihini döndürür.
     *
     * @return Fatura tarihi
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }
    
    /**
     * Fatura tarihini ayarlar.
     *
     * @param invoiceDate Fatura tarihi
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    /**
     * Fatura kalemlerini döndürür.
     *
     * @return Fatura kalemleri
     */
    public List<InvoiceItemData> getItems() {
        return items;
    }
    
    /**
     * Fatura kalemlerini ayarlar.
     *
     * @param items Fatura kalemleri
     */
    public void setItems(List<InvoiceItemData> items) {
        this.items = items;
        this.calculateTotalAmount();
    }
    
    /**
     * Fatura kalemi ekler.
     *
     * @param item Fatura kalemi
     */
    public void addItem(InvoiceItemData item) {
        this.items.add(item);
        this.calculateTotalAmount();
    }
    
    /**
     * Toplam tutarı döndürür.
     *
     * @return Toplam tutar
     */
    public double getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * Durumu döndürür.
     *
     * @return Durum
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Durumu ayarlar.
     *
     * @param status Durum
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Notları döndürür.
     *
     * @return Notlar
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Notları ayarlar.
     *
     * @param notes Notlar
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "InvoiceData{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
