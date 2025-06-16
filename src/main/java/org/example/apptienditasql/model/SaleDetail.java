package org.example.apptienditasql.model;

import java.math.BigDecimal;

public class SaleDetail {
	/// PA LA BASE DE DATOS
	String quantity;
	BigDecimal price;
	BigDecimal subTotal;
	BigDecimal total;
	int saleFolio;
	String catalogueBarcode;

	///////////////////
	//////GETTERS//////
	///////////////////
	public int getSaleFolio() {
		return saleFolio;
	}

	public String getCatalogueBarcode() {
		return catalogueBarcode;
	}

	public String getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	///////////////////
	//////SETTERS//////
	///////////////////
	public void setSaleFolio(int saleFolio) {
		this.saleFolio = saleFolio;
	}

	public void setCatalogueBarcode(String catalogueBarcode) {
		this.catalogueBarcode = catalogueBarcode;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
