package org.example.apptienditasql.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.example.apptienditasql.utils.UserMessage.message;
import static org.example.apptienditasql.utils.createProductUtil.createFileChooser;
import static org.example.apptienditasql.utils.createProductUtil.isFieldEmpty;

public class CreateViewController {
	///////////////
	//////DAO//////
	///////////////
	private ProductsDao productsDao = null;
	private File imageFile = null;

	///////////////////////////////
	//////REFERENCE VARIABLES//////
	///////////////////////////////
	private CatalogoController catalogoController;
	private Product editableProduct = null;

	public void setCatalogoController(CatalogoController catalogoController) {
		this.catalogoController = this.catalogoController;
	}

	public void setEditableProduct(Product product) {
		this.editableProduct = product;

		if (editableProduct != null) {
			insertOnEditView(rootPane.lookupAll(".input-field"));
		}
	}

	/////////////////////////
	//////FXML ELEMENTS//////
	/////////////////////////
	@FXML
	private Pane rootPane;
	@FXML
	private Button saveButton;


	/////////////////////////
	//////FORM ELEMENTS//////
	/////////////////////////
	@FXML
	public Pane imagePane;
	@FXML
	private TextField nameField;
	@FXML
	private TextField brandField;
	@FXML
	private TextField contentField;
	@FXML
	private TextField barcodeField;
	@FXML
	private TextField minStockField;
	@FXML
	private TextField maxStockField;
	@FXML
	private Button chooseFileButton;
	@FXML
	private TextArea descriptionArea;
	@FXML
	private RadioButton availableRadio;
	@FXML
	private ChoiceBox<String> categoryCB;
	@FXML
	private ChoiceBox<String> unitCB;
	@FXML
	private ChoiceBox<String> presentationCB;
	@FXML
	private ChoiceBox<String> locationCB;
	@FXML
	private DatePicker registerDatePicker;
	@FXML
	private DatePicker expiryDatePicker;

	////////////////////////
	//////ClOSE WINDOW//////
	////////////////////////
	@FXML
	private void closeButtonAction() {
		editableProduct = null;
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}

	////////////////////////////
	//////CHOICEBOX VALUES//////
	///////////////////////////
	private void choiceBoxValues() {
		categoryCB.getItems().addAll(productsDao.readCategories());
		unitCB.getItems().addAll(productsDao.readUnits());
		presentationCB.getItems().addAll(productsDao.redPresentations());
		locationCB.getItems().addAll(productsDao.readLocation());
	}

	private void imageChooseAction() {
		chooseFileButton.setOnAction(_ -> {
			imageFile = createFileChooser().showOpenDialog(null);
			if (imageFile != null) {
				showImageFromFile(imageFile);
			}
		});
	}

	private void showImageFromFile(File file) {
		Image image = new Image(file.toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.getStyleClass().add("img");
		imageView.setFitWidth(200);
		imageView.setFitHeight(200);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);

		imagePane.getChildren().clear();
		imagePane.getChildren().add(imageView);
	}


	/////////////////////////////
	//////UPDATE FIELD FILL//////
	/////////////////////////////
	private void insertOnEditView(Set<Node> nodes) {
		if (editableProduct == null) return;

		if (editableProduct.getImage() != null && !editableProduct.getImage().isEmpty()) {
			Path imagePath = Path.of("src/main/resources/org/example/apptienditasql/view/imgDirectory", editableProduct.getImage());
			if (Files.exists(imagePath)) {
				Image image = new Image(imagePath.toUri().toString());
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(200);
				imageView.setFitHeight(200);
				imageView.setPreserveRatio(true);
				imageView.setSmooth(true);

				imagePane.getChildren().clear();
				imagePane.getChildren().add(imageView);
			}
		}

		for (Node node : nodes) {
			if (node instanceof TextField tf) {
				switch (tf.getId()) {
					case "barcodeField" -> tf.setText(editableProduct.getBarcode());
					case "nameField" -> tf.setText(editableProduct.getName());
					case "brandField" -> tf.setText(editableProduct.getBrand());
					case "contentField" -> tf.setText(editableProduct.getContent());
					case "minStockField" -> tf.setText(editableProduct.getMinStock());
					case "maxStockField" -> tf.setText(editableProduct.getMaxStock());
				}
			} else if (node instanceof TextArea ta) {
				if ("descriptionArea".equals(ta.getId())) {
					ta.setText(editableProduct.getDescription());
				}
			} else if (node instanceof RadioButton rb) {
				if ("availableRadio".equals(rb.getId())) {
					rb.setSelected(editableProduct.isAvilable());
				}
			} else if (node instanceof ChoiceBox cb) {
				switch (cb.getId()) {

					case "categoryCB" -> cb.setValue(editableProduct.getCategory());
					case "unitCB" -> cb.setValue(editableProduct.getUnits());
					case "presentationCB" -> cb.setValue(editableProduct.getPresentation());
					case "locationCB" -> cb.setValue(editableProduct.getProductLocation());
				}
			} else if (node instanceof DatePicker dp) {
				switch (dp.getId()) {
					case "registerDatePicker" -> dp.setValue(editableProduct.getRegisterDate());
					case "expiryDatePicker" -> dp.setValue(editableProduct.getExpiryDate());
				}
			} else if (node instanceof Label l && "imgLabel".equals(l.getId())) {
				l.setText(editableProduct.getImage());
			}
		}
		System.out.println("Setoneditview: cat" + categoryCB);
		System.out.println("Setoneditview: meas" + unitCB);
		System.out.println("Setoneditview: pres" + presentationCB);
		System.out.println("Setoneditview: loc" + locationCB);
	}

	private void storeChanges() throws IOException {
		Product newProduct = new Product();
		String imageNameToUse = null;
		boolean shouldDeleteOldImage = false;
		String oldImageName = editableProduct != null ? editableProduct.getImage() : null;
		List<Control> requiredFields = List.of(nameField, brandField, contentField, barcodeField, minStockField, maxStockField, chooseFileButton, descriptionArea, availableRadio, categoryCB, unitCB, presentationCB, locationCB, registerDatePicker, expiryDatePicker);


		/////////////////////////////////////
		//////CHECKING BEFORE INSERTING//////
		/////////////////////////////////////
		for (Control requiredField : requiredFields) {
			if (isFieldEmpty(requiredField)) {
				requiredField.setStyle("-fx-border-color: red;");
			} else {
				requiredField.setStyle("-fx-border-color: none;");
			}
		}
		for (Control requiredField : requiredFields) {
			if (isFieldEmpty(requiredField)) {
				message("Falta Información", "Debe llenar todos los campos", Alert.AlertType.WARNING);
				return;
			}
		}
		if (imageFile == null && (editableProduct == null || editableProduct.getImage() == null)) {
			message("Imagen faltante", "Debe seleccionar una imagen para el producto.", Alert.AlertType.WARNING);
			return;
		}

		////////////////////////////////
		//////CREATING NEW PRODUCT//////
		////////////////////////////////
		newProduct.setBarcode(barcodeField.getText());
		newProduct.setName(nameField.getText());
		newProduct.setDescription(descriptionArea.getText());
		newProduct.setBrand(brandField.getText());
		newProduct.setContent(contentField.getText());
		newProduct.setMinStock(minStockField.getText());
		newProduct.setMaxStock(maxStockField.getText());
		newProduct.setAvilable(availableRadio.isSelected());
		newProduct.setRegisterDate(registerDatePicker.getValue());
		newProduct.setExpiryDate(expiryDatePicker.getValue());
		newProduct.setCategory(categoryCB.getValue());
		System.out.println("storeChanges: category" + categoryCB);
		newProduct.setUnits(unitCB.getValue());
		System.out.println("storeChanges: unit" + unitCB);
		newProduct.setPresentation(presentationCB.getValue());
		System.out.println("storeChanges: pres" + presentationCB);
		newProduct.setProductLocation(locationCB.getValue());
		System.out.println("storeChanges: loc" + locationCB);
		if (imageFile != null) {
			newProduct.setImage(imageFile.getName());
		}

		if (editableProduct != null) {
			if (imageFile != null) {
				String extension = "";
				int i = imageFile.getName().lastIndexOf('.');
				if (i > 0) {
					extension = imageFile.getName().substring(i).toLowerCase();
				}
				imageNameToUse = UUID.randomUUID().toString().substring(0, 8) + extension;
				shouldDeleteOldImage = true;
			} else {
				imageNameToUse = oldImageName;
			}
		} else {
			if (imageFile != null) {
				String extension = "";
				int i = imageFile.getName().lastIndexOf('.');
				if (i > 0) {
					extension = imageFile.getName().substring(i).toLowerCase();
				}
				imageNameToUse = UUID.randomUUID().toString().substring(0, 8) + extension;
			}
		}
		newProduct.setImage(imageNameToUse);

		insertToDb(newProduct);

		////////////////////////////////
		//////UPDATE MAINVIEW LIST//////
		////////////////////////////////
		closeButtonAction();
	}

	////////////////////////////////
	//////FROM INPUT TO OBJECT//////
	////////////////////////////////
	private <T> void insertToDb(Product product) {
		if (editableProduct != null) {
			productsDao.update(product);
			if (editableProduct != null) {
				productsDao.update(product);
				if (imageFile != null) {
					// Save new image
					setImage(product.getImage());

					// Delete old image
					deleteOldImage(editableProduct.getImage());
				}
			} else {
				productsDao.create(product);
				if (imageFile != null) {
					setImage(product.getImage());
				}
			}
		} else {
			productsDao.create(product);
			setImage(product.getImage());
		}

		if (catalogoController != null) {
			catalogoController.insertProductList();
		}

	}

	private void deleteOldImage(String imageName) {
		if (imageName == null || imageName.isEmpty()) return;
		Path oldImagePath = Path.of("src/main/resources/org/example/apptienditasql/view/imgDirectory", imageName);
		try {
			Files.deleteIfExists(oldImagePath);
		} catch (IOException e) {
			System.err.println("Failed to delete old image: " + imageName);
			e.printStackTrace();
		}
	}

	private void setImage(String imgName) {
		Path destination = Path.of("src/main/resources/org/example/apptienditasql/view/imgDirectory", imgName);
		try {
			Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@FXML
	public void initialize() throws SQLException {

		productsDao = new ProductsDao(DatabaseConnection.getConnection());

		//////////////////////////////
		//////INSERTAR CHOICEBOX//////
		//////////////////////////////
		choiceBoxValues();

		////////////////////////////
		//////ESCOGER IMAGENES//////
		////////////////////////////
		imageChooseAction();

		///////////////////////////
		//////GUARDAR CAMBIOS//////
		///////////////////////////
		saveButton.setOnAction(_ -> {
			try {
				storeChanges();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
