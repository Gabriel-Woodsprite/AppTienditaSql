package org.example.apptienditasql.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
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

import static org.example.apptienditasql.utils.UserMessage.message;
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
	private final List<String> editableProductList = MainViewController.editableProductList;

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}

	/////////////////////////
	//////FXML ELEMENTS//////
	/////////////////////////
	@FXML
	private Button saveButton;
	@FXML
	private Pane rootPane;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Label imgName;

	////////////////////////
	//////ClOSE WINDOW//////
	////////////////////////
	@FXML
	private void closeButtonAction() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public <T> void initialize() throws SQLException {
		System.out.println(editableProductList);
		productsDao = new ProductsDao(DatabaseConnection.getConnection());

		if (!editableProductList.isEmpty()) {
			insertValues(rootPane.lookupAll(".input-field"));
		}


		////////////////////////////
		//////ESCOGER IMAGENES//////
		////////////////////////////
		chooseFileButton.setOnAction(e -> {
			imageFile = createFileChooser().showOpenDialog(null);
			imgName.setText(imageFile.getName());
		});

		///////////////////////////
		//////GUARDAR CAMBIOS//////
		///////////////////////////
		saveButton.setOnAction(event -> {
			List<T> inputValues = new ArrayList<>();

			/////////////////////
			//////NODE LOOP//////
			/////////////////////
			for (Node node : rootPane.lookupAll(".input-field")) {
				if (node instanceof TextField tf) {
					System.out.println("TextField: " + tf.getText());
					inputValues.add((T) tf.getText());
				} else if (node instanceof RadioButton rb) {
					System.out.println("RadioButton: " + rb.isSelected());
					inputValues.add((T) rb.selectedProperty().getValue());
				} else if (node instanceof SplitMenuButton sp) {
					System.out.println("SplitMenuButton: " + sp.getId() + sp.getText());
					inputValues.add((T) sp.getText());
				} else if (node instanceof TextArea ta) {
					System.out.println("TextArea: " + ta.getText());
					inputValues.add((T) ta.getText());
				} else if (node instanceof DatePicker dp) {
					System.out.println("Date: " + dp.getValue());
					inputValues.add((T) dp.getValue());
				}

				if (node instanceof Label label && label.getId().equals("imgName")) {
					try {
						String extension = "";
						String originalName = imageFile.getName();
						int i = originalName.lastIndexOf('.');
						if (i > 0) {
							extension = originalName.substring(i).toLowerCase();
						}
						String newName = UUID.randomUUID().toString().substring(0, 8) + extension;
						inputValues.add((T) newName);
						Path destination = Path.of("src/main/resources/org/example/apptienditasql/view/imgDirectory", newName);
						Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				}
			}

			////////////////////////////////
			//////UPDATE MAINVIEW LIST//////
			////////////////////////////////
			objectParse(inputValues);
			closeButtonAction();
		});
	}

	////////////////////////////////
	//////FROM INPUT TO OBJECT//////
	////////////////////////////////
	private <T> void objectParse(List<T> inputValues) {
		System.out.println("objectParse: " + inputValues);
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
		product.setProductLocation((String) inputValues.get(14));

		try {
			productsDao.create(product);
			if (mainViewController != null) {
				mainViewController.insertProductList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/////////////////////////////
	//////UPDATE FIELD FILL//////
	/////////////////////////////
	private void insertValues(Set<Node> nodes) {
		int i = 0;
		for (Node node : nodes) {
			if (node instanceof TextField tf) {
				tf.setText(editableProductList.get(i));
			} else if (node instanceof RadioButton rb) {
				System.out.println("Bulian?" + Boolean.parseBoolean(editableProductList.get(i)));
				System.out.println("Bulian?" + editableProductList.get(i));
				rb.setSelected(Boolean.parseBoolean(editableProductList.get(i)));
			} else if (node instanceof SplitMenuButton sp) {
				sp.setText(editableProductList.get(i));
			} else if (node instanceof TextArea ta) {
				ta.setText(editableProductList.get(i));
			} else if (node instanceof DatePicker dp) {
				dp.setValue(LocalDate.parse(editableProductList.get(i)));
			}
			i++;
		}
		editableProductList.clear();
	}
}
