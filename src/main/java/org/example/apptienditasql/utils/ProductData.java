package org.example.apptienditasql.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import org.example.apptienditasql.model.Product;

public class ProductData {
	private static final ObservableList<HBox> products = FXCollections.observableArrayList();

	public static ObservableList<HBox> getProducts() {
		return products;
	}
	public static void setProducts(ObservableList<HBox> products) {

	}
}
