package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;
import org.example.apptienditasql.view.MainView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.apptienditasql.utils.ProductData.parseProduct;

public class MainViewController {
	@FXML
	private VBox vBox;

	@FXML
	public ListView productsListView;

	@FXML
	public Button addProductButton;

	public static List<String> editableProductList = new ArrayList<>();

	ProductsDao productsDao = new ProductsDao(DatabaseConnection.getConnection());
	ObservableList<HBox> interactiveElements = FXCollections.observableArrayList();


	public MainViewController() throws SQLException {
	}

	public List<String> getEditableProductList() {
		return editableProductList;
	}
	@FXML
	public void insertProductList() throws Exception {
		List<Product> productsList = productsDao.readAll();

		productsListView.getItems().clear();
		for (Product product : productsList) {
			HBox hbox = new HBox();
			String productDetails = product.getBarcode() + " | " + product.getName();
			Button editar = new Button("Editar");
			Button eliminar = new Button("Eliminar");
			eliminar.setId(product.getBarcode());

			eliminar.setOnAction(e -> {
				try {
					removeProductButton(product.getBarcode());
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});
			editar.setOnAction(e -> {
				try {
					editableProductList =  parseProduct(productsDao.read(product.getBarcode()));
					addProductButton("Editar Producto");
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


	private void addProductButton(String title) throws Exception {
		/*___FXML___*/
		FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("add-products.fxml"));

		Scene scene = new Scene(fxmlLoader.load(), 1200, 864);
		Stage newStage = new Stage();

		CreateViewController controller = fxmlLoader.getController();
		controller.setMainViewController(this);
		newStage.setScene(scene);
		newStage.setTitle(title);
		newStage.setResizable(false);
		newStage.show();
	}

	@FXML
	public void initialize() throws Exception {
		addProductButton.setOnAction(e -> {
			try {
				addProductButton("Añadir Producto");
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		insertProductList();
	}
}