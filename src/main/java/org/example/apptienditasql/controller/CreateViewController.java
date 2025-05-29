package org.example.apptienditasql.controller;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.example.apptienditasql.utils.fileChooserCreator.createFileChooser;

public class CreateViewController {
	///////////////
	//////DAO//////
	///////////////
	private ProductsDao productsDao = null;
	private File imageFile = null;

	///////////////////////////////
	//////REFERENCE VARIABLES//////
	///////////////////////////////
	private MainViewController mainViewController;
	private Product editableProduct = null;

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}

	public void setEditableProduct(Product product) {
		this.editableProduct = product;

		if (editableProduct != null) {
			insertProductValues(rootPane.lookupAll(".input-field"));
		}
	}

	/////////////////////////
	//////FXML ELEMENTS//////
	/////////////////////////
	@FXML
	private Button saveButton;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Pane rootPane;
	@FXML
	private Label imgLabel;
	@FXML
	private ChoiceBox<String> categoryCB;
	@FXML
	private ChoiceBox<String> unitCB;
	@FXML
	private ChoiceBox<String> presentationCB;

	////////////////////////
	//////ClOSE WINDOW//////
	////////////////////////
	@FXML
	private void closeButtonAction() {
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
	}

	private void imageChooseAction() {
		System.out.println("imagefile? " + imageFile);
		chooseFileButton.setOnAction(_ -> {
			imageFile = createFileChooser().showOpenDialog(null);
			imgLabel.setText(imageFile.getName());
		});
	}

	private <T> void storeChanges() throws IOException {
		List<T> inputValues = new ArrayList<>();


		String imageNameToUse = null;
		boolean shouldDeleteOldImage = false;
		String oldImageName = editableProduct != null ? editableProduct.getImage() : null;
		/////////////////////
		//////NODE LOOP//////
		/////////////////////
		for (Node node : rootPane.lookupAll(".input-field")) {
			if (node instanceof TextField tf) {
				inputValues.add((T) tf.getText());
			} else if (node instanceof RadioButton rb) {
				inputValues.add((T) rb.selectedProperty().getValue());
			} else if (node instanceof ChoiceBox cb) {
				inputValues.add((T) cb.getValue());
			} else if (node instanceof TextArea ta) {
				inputValues.add((T) ta.getText());
			} else if (node instanceof DatePicker dp) {
				inputValues.add((T) dp.getValue());
			}

			if (node instanceof Label label && label.getId().equals("imgLabel")) {
				if (editableProduct != null) {
					// Editing mode
					if (imageFile != null) {
						// New file selected → generate new name and mark old image for deletion
						String extension = "";
						int i = imageFile.getName().lastIndexOf('.');
						if (i > 0) {
							extension = imageFile.getName().substring(i).toLowerCase();
						}
						imageNameToUse = UUID.randomUUID().toString().substring(0, 8) + extension;
						shouldDeleteOldImage = true;
					} else {
						// No new file selected → keep old name
						imageNameToUse = oldImageName;
					}
				} else {
					// Creation mode
					if (imageFile != null) {
						String extension = "";
						int i = imageFile.getName().lastIndexOf('.');
						if (i > 0) {
							extension = imageFile.getName().substring(i).toLowerCase();
						}
						imageNameToUse = UUID.randomUUID().toString().substring(0, 8) + extension;
					}
				}
				inputValues.add((T) imageNameToUse);
			}
		}

		////////////////////////////////
		//////UPDATE MAINVIEW LIST//////
		////////////////////////////////
		objectParse(inputValues);
		closeButtonAction();
	}

	////////////////////////////////
	//////FROM INPUT TO OBJECT//////
	////////////////////////////////
	private <T> void objectParse(List<T> inputValues) {
		Product product = new Product();
		product.setBarcode((String) inputValues.get(0));
		product.setName((String) inputValues.get(1));
		product.setBrand((String) inputValues.get(2));
		product.setDescription((String) inputValues.get(3));
		product.setContent((String) inputValues.get(4));
		product.setAvilable((Boolean) inputValues.get(5));
		product.setImage((String) inputValues.get(6));
		product.setCategory((String) inputValues.get(7));
		product.setMeasurementUnit((String) inputValues.get(8));
		product.setPresentation((String) inputValues.get(9));
		product.setMinStock((String) inputValues.get(10));
		product.setMaxStock((String) inputValues.get(11));
		product.setRegisterDate((LocalDate) inputValues.get(12));
		product.setExpiryDate((LocalDate) inputValues.get(13));
//		product.setProductLocation((String) inputValues.get(14));
		product.setProductLocation("Estante 1");


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
			setImage(inputValues.get(6).toString());
		}

		if (mainViewController != null) {
			mainViewController.insertProductList();
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

	/////////////////////////////
	//////UPDATE FIELD FILL//////
	/////////////////////////////
	private void insertProductValues(Set<Node> nodes) {
		if (editableProduct == null) return;

		System.out.println("editableProduct: " + editableProduct);
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
					case "unitCB" -> cb.setValue(editableProduct.getMeasurementUnit());
					case "presentationCB" -> cb.setValue(editableProduct.getPresentation());
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
