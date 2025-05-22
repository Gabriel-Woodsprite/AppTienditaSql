package org.example.apptienditasql.model;

import java.time.LocalDate;

public class Product {
	private String barcode;
	private String name;
	private String brand;
	private String category;
	private String content;
	private String measurementUnit;
	private String minStock;
	private String maxStock;
	private String presentation;
	private String description;
	private boolean avilable;
	private String image;
	private LocalDate registerDate;
	private String productLocation;
	private LocalDate expiryDate;


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

	public String getMeasurementUnit() {
		return measurementUnit;
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

	public LocalDate getExpiryDate() {
		return expiryDate;
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

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
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

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
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
				", measurementUnit='" + measurementUnit + '\'' +
				", minStock='" + minStock + '\'' +
				", maxStock='" + maxStock + '\'' +
				", presentation='" + presentation + '\'' +
				", description='" + description + '\'' +
				", avilable=" + avilable +
				", image='" + image + '\'' +
				", registerDate=" + registerDate +
				", productLocation='" + productLocation + '\'' +
				", expiryDate=" + expiryDate +
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


