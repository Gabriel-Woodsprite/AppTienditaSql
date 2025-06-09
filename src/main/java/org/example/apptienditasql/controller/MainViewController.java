package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Set;

public class MainViewController {
	///////////////
	//////DAO//////
	///////////////
	ProductsDao productsDao = new ProductsDao(DatabaseConnection.getConnection());
	ObservableList<HBox> interactiveElements = FXCollections.observableArrayList();

	/////////////////////////
	//////FXML ELEMENTS//////
	/////////////////////////
	@FXML
	public ListView<HBox> productsListView;
	@FXML
	public Button addProductButton;
	@FXML
	public Pane imagePane;
	@FXML
	public Pane rightPane;
	@FXML
	public CheckBox availableCheckbox;
	@FXML
	public TextArea descriptionTextArea;

	///////////////////////////////
	//////REFERENCE VARIABLES//////
	///////////////////////////////
	public Product selectedProduct;

	public MainViewController() throws SQLException {
	}


	////////////////////////////////////////////
	//////FUNCIONALIDAD LISTA DE PRODUCTOS//////
	////////////////////////////////////////////
	@FXML
	public void insertProductList() {
		List<Product> productsList = productsDao.readAll();

		productsListView.getItems().clear();
		for (Product product : productsList) {


			HBox hbox = new HBox();
			String barCodeString = product.getBarcode();
			String nameString = product.getName();
			Button editar = new Button("");
			Button eliminar = new Button("");

			

			editar.getStyleClass().add("edit_button");
			editar.setPrefSize(25, 25);
			editar.setMinSize(25, 25);

			eliminar.getStyleClass().add("delete_button");
			eliminar.setPrefSize(25, 25);
			eliminar.setMinSize(25,25);
			eliminar.setId(product.getBarcode());

			/// BOTÓN ELIMINAR
			eliminar.setOnAction(_ -> {
				try {
					File img = new File("src/main/resources/org/example/apptienditasql/view/imgDirectory/" + product.getImage());
					Files.delete(img.toPath());
					removeProductAction(product.getBarcode());
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});

			/// BOTÓN EDITAR
			editar.setOnAction(_ -> {
				try {
					selectedProduct = productsDao.read(product.getBarcode());
					addProductAction("Editar Producto", selectedProduct);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});

			hbox.setSpacing(30);
			hbox.getChildren().addAll(new Pane(new Label(barCodeString)), new Pane(new Label(nameString)), new Pane(editar), new Pane(eliminar));

			interactiveElements.add(hbox);
		}

		productsListView.setItems(interactiveElements);
		infoCardAction();
	}

	//////////////////////////////////////
	//////FUCIONALIDAD PANEL DERECHO//////
	//////////////////////////////////////
	@FXML
	private void infoCardAction(){
		interactiveElements.forEach(e -> e.setOnMouseClicked(_ -> {
			/////////////////////////////////
			//////VALORES INICIALIZADOS//////
			/////////////////////////////////
			Pane pane = (Pane) e.getChildren().getFirst();
			Label label = (Label) pane.getChildren().getFirst();
			String barCodeString = label.getText();
			Product product = productsDao.read(barCodeString);
			String imgName = product.getImage();

			//////////////////////////////////////////
			//////ACTUALIZAR CARD DE INFORMACIÓN//////
			//////////////////////////////////////////
			Set<Node> descriptionLabels = rightPane.lookupAll(".descriptionLabels");
			List<String> descriptions = new ArrayList<>();
			descriptions.add(barCodeString);
			descriptions.add(product.getBrand());
			descriptions.add(product.getContent());
			descriptions.add(product.getMaxStock());
			descriptions.add(product.getMinStock());
			descriptions.add(product.getExpiryDate().toString());
			descriptions.add(product.getName());

			int[] i = new int[1];
			descriptionLabels.forEach(labelNode -> {
				((Label) labelNode).setText(descriptions.get(i[0]));
				i[0]++;
			});
			availableCheckbox.setSelected(product.isAvilable());
			descriptionTextArea.setText(product.getDescription());
			System.out.println("Descripcion_"+product.getDescription());


			//////////////////////////////////////
			//////ACTUALIZAR IMAGEN DEL CARD//////
			//////////////////////////////////////
			imagePane.getChildren().clear();
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

	////////////////////////////////////
	//////ACCIÓN ELIMINAR PRODUCTO//////
	////////////////////////////////////
	private void removeProductAction(String barcode) {
		productsDao.delete(barcode);
		insertProductList();
	}

	//////////////////////////////////
	//////ACCIÓN AÑADIR PRODUCTO//////
	//////////////////////////////////
	private void addProductAction(String title, Product selected) throws Exception {
		/*___FXML___*/
		FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("add-products.fxml"));

		Scene scene = new Scene(fxmlLoader.load()/*, 1200, 864*/);
		Stage newStage = new Stage();

		CreateViewController controller = fxmlLoader.getController();
		controller.setMainViewController(this);
//		controller.setEditableProduct(selectedProduct);
		controller.setEditableProduct(selected);
		newStage.setScene(scene);
		newStage.setTitle(title);
		newStage.setResizable(false);
		newStage.show();
	}

	@FXML
	public void initialize() {
		addProductButton.setOnAction(_ -> {
			try {
				addProductAction("Añadir Producto", null);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		insertProductList();
		infoCardAction();
	}
}