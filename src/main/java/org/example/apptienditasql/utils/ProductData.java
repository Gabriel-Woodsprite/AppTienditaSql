package org.example.apptienditasql.utils;

import org.example.apptienditasql.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductData {

	public static List<String> parseProduct(Product product) {
		List<String> productInfo = new ArrayList<>();
		productInfo.add(product.getBarcode());
		productInfo.add(product.getName());
		productInfo.add(product.getBrand());
		productInfo.add(product.getDescription());
		productInfo.add(product.getContent());
		productInfo.add((product.isAvilable() ? "true" : "false"));
		productInfo.add(product.getImage());
		productInfo.add(product.getCategory());
		productInfo.add(product.getMeasurementUnit());
		productInfo.add(product.getPresentation());
		productInfo.add(product.getMinStock());
		productInfo.add(product.getMaxStock());
		productInfo.add(product.getRegisterDate().toString());
		productInfo.add(product.getExpiryDate().toString());
		productInfo.add(product.getProductLocation());
		return productInfo;
	}
}
