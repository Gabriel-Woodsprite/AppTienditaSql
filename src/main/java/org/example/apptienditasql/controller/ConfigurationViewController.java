package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.isAllEmpty;
import static org.example.apptienditasql.utils.ControllerUtils.isFieldEmpty;
import static org.example.apptienditasql.utils.UserMessage.message;

public class ConfigurationViewController {
	ProductsDao productsDao = null;
	ObservableList<HBox> observableUnitList = FXCollections.observableArrayList();
	ObservableList<HBox> observableCategoryList = FXCollections.observableArrayList();
	ObservableList<HBox> observablePresentationList = FXCollections.observableArrayList();
	ObservableList<HBox> observableLocationList = FXCollections.observableArrayList();
	@FXML
	private TextField unitsTxt;
	@FXML
	private TextField categoryTxt;
	@FXML
	private TextField presentationTxt;
	@FXML
	private TextField locationTxt;
	@FXML
	private TextField margenPrecioTxt;
	@FXML
	private Button saveButton;
	@FXML
	private Button resetButton;
	@FXML
	private ListView<HBox> unitsList;
	@FXML
	private ListView<HBox> locationList;
	@FXML
	private ListView<HBox> categoryList;
	@FXML
	private ListView<HBox> presentationList;

	private void insertToList() {
		List<String> categories = productsDao.readCategories();
		List<String> locations = productsDao.readLocation();
		List<String> presentations = productsDao.readPresentations();
		List<String> units = productsDao.readUnits();

		unitsList.getItems().clear();
		locationList.getItems().clear();
		categoryList.getItems().clear();
		presentationList.getItems().clear();

		for (String category : categories) {
			HBox hbox = new HBox();
			Label label = new Label(category);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			Button delete = new Button("×");
			delete.setOnAction(_ -> {
				if (!productsDao.deleteOptions("category", "idCategory", "category", label.getText())) {
					message("Error", "Esta Categoría está relacionada con un producto\nNo es posible eliminar", Alert.AlertType.ERROR);
				}
				insertToList();
			});

			hbox.getStyleClass().add("hbox");
			label.getStyleClass().add("label");
			delete.getStyleClass().add("btns");

			hbox.getChildren().addAll(label, spacer, delete);
			observableCategoryList.add(hbox);
		}
		for (String location : locations) {
			HBox hbox = new HBox();
			Label label = new Label(location);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			Button delete = new Button("×");
			delete.setOnAction(_ -> {
				if (!productsDao.deleteOptions("location", "idLocation", "location", label.getText())) {
					message("Error", "Esta Ubicación está relacionada con un producto\nNo es posible eliminar", Alert.AlertType.ERROR);
				}
				insertToList();
			});

			hbox.getStyleClass().add("hbox");
			label.getStyleClass().add("label");
			delete.getStyleClass().add("btns");

			hbox.getChildren().addAll(label,spacer, delete);
			observableLocationList.add(hbox);
		}
		for (String presentation : presentations) {
			HBox hbox = new HBox();
			Label label = new Label(presentation);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			Button delete = new Button("×");
			delete.setOnAction(_ -> {
				if (!productsDao.deleteOptions("presentation", "idPresentation", "presentation", label.getText())) {
					message("Error", "Esta Presentación está relacionada con un producto\nNo es posible eliminar", Alert.AlertType.ERROR);
				}
				insertToList();
			});

			hbox.getStyleClass().add("hbox");
			label.getStyleClass().add("label");
			delete.getStyleClass().add("btns");

			hbox.getChildren().addAll(label,spacer, delete);
			observablePresentationList.add(hbox);
		}
		for (String unit : units) {
			HBox hbox = new HBox();
			Label optionLabel = new Label(unit);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			Button delete = new Button("×");

			delete.setOnAction(_ -> {
				if (!productsDao.deleteOptions("units", "idUnits", "unit", optionLabel.getText())) {
					message("Error", "Esta unidad está relacionada con un producto\nNo es posible eliminar", Alert.AlertType.ERROR);
				}
				insertToList();
			});

			hbox.getStyleClass().add("hbox");
			optionLabel.getStyleClass().add("label");
			delete.getStyleClass().add("btns");
			hbox.getChildren().addAll(optionLabel,spacer, delete);
			observableUnitList.add(hbox);
		}

		unitsList.setItems(observableUnitList);
		locationList.setItems(observableLocationList);
		categoryList.setItems(observableCategoryList);
		presentationList.setItems(observablePresentationList);

		unitsTxt.clear();
		locationTxt.clear();
		margenPrecioTxt.clear();
		presentationTxt.clear();
	}

	private void saveConfig() {
		List<TextField> required = List.of(unitsTxt, categoryTxt, presentationTxt, locationTxt);
		System.out.println("IS ALL EMPTY CONFIGURATION 131" + isAllEmpty(required));
		if (!isAllEmpty(required)) {
			for (TextField requiredField : required) {
				String text = requiredField.getText();
				switch (requiredField.getId()) {
					case "unitsTxt" -> {
						if (!productsDao.readUnits().contains(text)) {
							if (!text.isBlank()) {
								productsDao.createOptions("units", text);
							}
						} else {
							message("Info", "Esta Unidad ya existe", Alert.AlertType.INFORMATION);
						}
					}

					case "categoryTxt" -> {
						if (!productsDao.readCategories().contains(text)) {
							if (!text.isBlank()) {
								productsDao.createOptions("category", text);
							}
						} else {
							message("Info", "Esta categoría ya existe", Alert.AlertType.INFORMATION);
						}
					}
					case "presentationTxt" -> {
						if (!productsDao.readPresentations().contains(text)) {
							if (!text.isBlank()) {
								productsDao.createOptions("presentation", text);
							}
						} else {
							message("Info", "Esta presentation ya existe", Alert.AlertType.INFORMATION);
						}
					}
					case "locationTxt" -> {
						if (!productsDao.readLocation().contains(text)) {
							if (!text.isBlank()) {
								productsDao.createOptions("location", text);
							}
						} else {
							message("Info", "Esta ubicación ya existe", Alert.AlertType.INFORMATION);
						}
					}
					default ->
							message("Info", "Debes agregar información para guardar cambios", Alert.AlertType.INFORMATION);
				}
			}
			insertToList();
		} else {
			message("Info", "Debes agregar información para guardar cambios", Alert.AlertType.INFORMATION);
		}
	}

	private void resetConfig() {
		productsDao.resetConfig();
		insertToList();
	}

	@FXML
	public void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		insertToList();
		saveButton.setOnAction(_ -> saveConfig());
		resetButton.setOnAction(_ -> resetConfig());
	}
}
