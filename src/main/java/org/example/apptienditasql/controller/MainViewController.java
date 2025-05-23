package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;
import org.example.apptienditasql.view.MainView;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.apptienditasql.utils.ProductData.parseProduct;

public class MainViewController {

	@FXML
	public ListView<HBox> productsListView;

	@FXML
	public Button addProductButton;
	@FXML
	public Pane imagePane;

	public static List<String> editableProductList = new ArrayList<>();

	ProductsDao productsDao = new ProductsDao(DatabaseConnection.getConnection());
	ObservableList<HBox> interactiveElements = FXCollections.observableArrayList();


	public MainViewController() throws SQLException {
	}


	@FXML
	public void insertProductList() {
		List<Product> productsList = productsDao.readAll();

		productsListView.getItems().clear();
		for (Product product : productsList) {
			HBox hbox = new HBox();
			String barCodeString = product.getBarcode();
			String nameString = product.getName();
			Button editar = new Button("Editar");
			Button eliminar = new Button("Eliminar");
			eliminar.setId(product.getBarcode());

			eliminar.setOnAction(_ -> {
				try {
					File img = new File("src/main/resources/org/example/apptienditasql/view/imgDirectory/" + product.getImage());
					Files.delete(img.toPath());
					removeProductButton(product.getBarcode());
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});
			editar.setOnAction(_ -> {
				try {
					editableProductList = parseProduct(productsDao.read(product.getBarcode()));
					addProductButton("Editar Producto");
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});

			hbox.setSpacing(30);
			hbox.getChildren().addAll(new Pane(new Label(barCodeString)), new Pane(new Label(nameString)), new Pane(editar), new Pane(eliminar));

			interactiveElements.add(hbox);
		}

		productsListView.setItems(interactiveElements);

		interactiveElements.forEach(e -> e.setOnMouseClicked(_ -> {
			imagePane.getChildren().clear();
			Pane pane = (Pane) e.getChildren().getFirst();
			Label label = (Label) pane.getChildren().getFirst();
			String barCodeString = label.getText();
			Product product = productsDao.read(barCodeString);
			String imgName = product.getImage();
			System.out.println(imgName);

			Image producImage = new Image(Objects.requireNonNull(MainView.class.getResourceAsStream("imgDirectory/" + imgName)));
			ImageView producImageView = new ImageView(producImage);
			producImageView.getStyleClass().add("img");
			producImageView.setFitWidth(200);
			producImageView.setFitHeight(200);
			producImageView.setPreserveRatio(true);
			producImageView.setSmooth(true);

			producImageView.setLayoutX((imagePane.getWidth() - producImageView.getFitWidth()) / 2);
			producImageView.setLayoutY((imagePane.getHeight() - producImageView.getFitHeight()) / 2);

			imagePane.getChildren().add(producImageView);
		}));

	}

	private void removeProductButton(String barcode){
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
	public void initialize() {
		addProductButton.setOnAction(_ -> {
			try {
				addProductButton("Añadir Producto");
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		insertProductList();
	}
}