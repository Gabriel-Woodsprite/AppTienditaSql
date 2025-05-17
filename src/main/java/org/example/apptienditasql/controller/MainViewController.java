package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;

import java.util.List;

public class MainViewController {

	@FXML
	public ListView productsListView;

	ProductsDao productsDao = new ProductsDao();
	ObservableList<HBox> interactiveElements = FXCollections.observableArrayList();


	@FXML
	private void insertProductList() throws Exception {
		List<Product> productsList = productsDao.readAll();

		productsListView.getItems().clear();
		for (Product product : productsList) {
			HBox hbox = new HBox();
			String productDetails = product.getBarcode() + " | " + product.getName();
			Button editar = new Button("Editar");
			Button eliminar = new Button("Eliminar");
			eliminar.setId(product.getBarcode());

//			editar.setOnAction(e -> {})
			eliminar.setOnAction(e -> {
				try {
					removeProductButton(product.getBarcode());
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});

			hbox.setSpacing(30);
			hbox.getChildren().addAll(new Pane(new Label(productDetails)), new Pane(editar), new Pane(eliminar));

			interactiveElements.add(hbox);
		}

		productsListView.setItems(interactiveElements);
	}

	private void removeProductButton(String barcode) throws Exception {
		productsDao.delete(barcode);
		insertProductList();
	}

	@FXML
	public void initialize() throws Exception {
		insertProductList();
	}
}