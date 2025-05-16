package com.erp.test.data.models;

/**
 * Fatura kalemi verilerini temsil eden sınıf.
 */
public class InvoiceItemData {
    private String productName;
    private int quantity;
    private double unitPrice;
    private double lineTotal;
    private String taxRate;
    
    /**
     * Varsayılan constructor.
     */
    public InvoiceItemData() {
    }
    
    /**
     * Parametreli constructor.
     *
     * @param productName Ürün adı
     * @param quantity Miktar
     * @param unitPrice Birim fiyat
     */
    public InvoiceItemData(String productName, int quantity, double unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.calculateLineTotal();
        this.taxRate = "18%"; // Varsayılan KDV oranı
    }
    
    /**
     * Parametreli constructor.
     *
     * @param productName Ürün adı
     * @param quantity Miktar
     * @param unitPrice Birim fiyat
     * @param taxRate Vergi oranı
     */
    public InvoiceItemData(String productName, int quantity, double unitPrice, String taxRate) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.taxRate = taxRate;
        this.calculateLineTotal();
    }
    
    /**
     * Satır toplamını hesaplar.
     */
    private void calculateLineTotal() {
        this.lineTotal = this.quantity * this.unitPrice;
    }
    
    /**
     * Ürün adını döndürür.
     *
     * @return Ürün adı
     */
    public String getProductName() {
        return productName;
    }
    
    /**
     * Ürün adını ayarlar.
     *
     * @param productName Ürün adı
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    /**
     * Miktarı döndürür.
     *
     * @return Miktar
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Miktarı ayarlar.
     *
     * @param quantity Miktar
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.calculateLineTotal();
    }
    
    /**
     * Birim fiyatı döndürür.
     *
     * @return Birim fiyat
     */
    public double getUnitPrice() {
        return unitPrice;
    }
    
    /**
     * Birim fiyatı ayarlar.
     *
     * @param unitPrice Birim fiyat
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.calculateLineTotal();
    }
    
    /**
     * Satır toplamını döndürür.
     *
     * @return Satır toplamı
     */
    public double getLineTotal() {
        return lineTotal;
    }
    
    /**
     * Vergi oranını döndürür.
     *
     * @return Vergi oranı
     */
    public String getTaxRate() {
        return taxRate;
    }
    
    /**
     * Vergi oranını ayarlar.
     *
     * @param taxRate Vergi oranı
     */
    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }
    
    @Override
    public String toString() {
        return "InvoiceItemData{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", lineTotal=" + lineTotal +
                ", taxRate='" + taxRate + '\'' +
                '}';
    }
}
