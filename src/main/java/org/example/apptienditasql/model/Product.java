package org.example.apptienditasql.model;

public class Product {
	private String barcode;
	private String name;
	private String brand;
	private String category;
	private String content;
	private String presentacion;
	private String measurementUnit;
	private boolean active;
	private String description;
	private String price;
	private String image;

	public Product(String barcode) {
		this.barcode = barcode;
	}
	public Product() {}

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

	public String getPresentacion() {
		return presentacion;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public boolean isActive() {
		return active;
	}

	public String getDescription() {
		return description;
	}

	public String getPrice() {
		return price;
	}

	public String getImage() {
		return image;
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

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
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
				", presentacion='" + presentacion + '\'' +
				", measurementUnit='" + measurementUnit + '\'' +
				", active=" + active +
				", description='" + description + '\'' +
				", price='" + price + '\'' +
				", image='" + image + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return false;

		if(obj instanceof Product product) {
			return product.getBarcode().equals(this.getBarcode());
		}
		return false;
	}
}
