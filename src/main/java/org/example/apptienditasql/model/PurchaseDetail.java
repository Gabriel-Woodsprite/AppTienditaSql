package org.example.apptienditasql.model;

import java.time.LocalDate;

public class PurchaseDetail {
	/// INFORMACIÓN COMPLEMENTARIA
	String productName;

	/// INFORMACIÓN PARA BASE DE DATOS
	String quantity;
	String unitPrice;
	String total;
	LocalDate expiryDate;

	/// FOREIGN KEYS
	int purchaseId;
	int barcode;

	public int getBarcode() {
		return barcode;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public String getProductName() {
		return productName;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public String getTotal() {
		return total;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "PurchaseDetail{" +
				"barcode='" + barcode + '\'' +
				", productName='" + productName + '\'' +
				", quantity='" + quantity + '\'' +
				", unitPrice='" + unitPrice + '\'' +
				", total='" + total + '\'' +
				", expiryDate=" + expiryDate +
				'}';
	}
}
