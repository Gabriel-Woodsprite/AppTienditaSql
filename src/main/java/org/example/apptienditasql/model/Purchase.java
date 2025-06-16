package org.example.apptienditasql.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Purchase {
	private LocalDate purchaseDate;
	private String folio;
	private BigDecimal cost;
	List<PurchaseDetail> saleDetails;
	private int supplierId;

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public String getFolio() {
		return folio;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public List<PurchaseDetail> getSaleDetails() {
		return saleDetails;
	}
	public int getSupplierId() {
		return supplierId;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setSaleDetails(List<PurchaseDetail> saleDetails) {
		this.saleDetails = saleDetails;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
}
