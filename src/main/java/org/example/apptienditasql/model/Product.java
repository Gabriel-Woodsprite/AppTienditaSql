package org.example.apptienditasql.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;

public class Product {
	private String barcode;
	private String name;
	private String brand;
	private String content;
	private String minStock;
	private String maxStock;
	private String description;
	private boolean avilable;
	private String image;
	private LocalDate registerDate;
	private String category;
	private String presentation;
	private String productLocation;
	private String units;


	/// ATTRIBUTES FOR SALES
	private int quantity;
	private double price;
	private double subTotal;

	public Product() {
	}


	///////////////////
	//////GETTERS//////
	///////////////////
	public String getBarcode() {
		return barcode;
	}

	public String getName() {
		return name;
	}

	public String getBrand() {
		return brand;
	}

	public String getCategory() {
		return category;
	}

	public String getContent() {
		return content;
	}

	public String getUnits() {
		return units;
	}

	public String getMinStock() {
		return minStock;
	}

	public String getMaxStock() {
		return maxStock;
	}

	public String getPresentation() {
		return presentation;
	}

	public String getDescription() {
		return description;
	}

	public boolean isAvilable() {
		return avilable;
	}

	public String getImage() {
		return image;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public String getProductLocation() {
		return productLocation;
	}

	/// SALES ATTRIBUTES GETTERS
	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public double getSubtotal() {
		return subTotal;
	}

	///////////////////
	//////SETTERS//////
	///////////////////
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}

	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvilable(boolean avilable) {
		this.avilable = avilable;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public void setProductLocation(String productLocation) {
		this.productLocation = productLocation;
	}

	/// SALES ATTRIBUTES SETTERS
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setSubtotal(double subTotal) {
		this.subTotal = subTotal;
	}

	///////////////////
	//////OVERRIDES////
	///////////////////

	@Override
	public String toString() {
		return "Product{" +
				"barcode='" + barcode + '\'' +
				", name='" + name + '\'' +
				", brand='" + brand + '\'' +
				", category='" + category + '\'' +
				", content='" + content + '\'' +
				", measurementUnit='" + units + '\'' +
				", minStock='" + minStock + '\'' +
				", maxStock='" + maxStock + '\'' +
				", presentation='" + presentation + '\'' +
				", description='" + description + '\'' +
				", avilable=" + avilable +
				", image='" + image + '\'' +
				", registerDate=" + registerDate +
				", productLocation='" + productLocation + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return false;

		if (obj instanceof Product product) {
			return product.getBarcode().equals(this.getBarcode());
		}
		return false;
	}
}


