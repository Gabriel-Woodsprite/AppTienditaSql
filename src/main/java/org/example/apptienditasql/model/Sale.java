package org.example.apptienditasql.model;

import java.time.LocalDate;
import java.util.List;

public class Sale {
	int folio;
	LocalDate dateSold;
	List<SaleDetail> saleDetails;

	///////////////////
	//////GETTERS//////
	///////////////////
	public int getFolio() {
		return folio;
	}

	public LocalDate getDateSold() {
		return dateSold;
	}

	public List<SaleDetail> getSaleDetails() {
		return saleDetails;
	}

	///////////////////
	//////SETTERS//////
	///////////////////
	public void setFolio(int folio) {
		this.folio = folio;
	}

	public void setDateSold(LocalDate dateSold) {
		this.dateSold = dateSold;
	}

	public void setSaleDetails(List<SaleDetail> saleDetails) {
		this.saleDetails = saleDetails;
	}
}
