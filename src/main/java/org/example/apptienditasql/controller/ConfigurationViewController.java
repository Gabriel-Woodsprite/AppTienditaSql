package org.example.apptienditasql.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.isFieldEmpty;
import static org.example.apptienditasql.utils.UserMessage.message;

public class ConfigurationViewController {
	ProductsDao productsDao = null;
	@FXML
	private TextField unidadMedida;
	@FXML
	private TextField categoria;
	@FXML
	private TextField presentacion;
	@FXML
	private TextField ubicacion;
	@FXML
	private TextField margenPrecio;
	@FXML
	private Button saveButton;
	@FXML
	private Button resetButton;

	private void saveConfig() {
		List<Control> required = List.of(unidadMedida, categoria, presentacion, ubicacion);
		for (Control requiredField : required) {
			TextField textField = (TextField) requiredField;
			String text = textField.getText();
			if (!isFieldEmpty(requiredField)) {
				switch (requiredField.getId()) {
					case "unidadMedida" -> productsDao.createUnit(text);
					case "categoria" -> productsDao.createCategory(text);
					case "presentacion" -> productsDao.createPresentation(text);
					case "ubicacion" -> productsDao.createLocation(text);
				}
				message("Exito", "Se actualizaron las opciones", Alert.AlertType.INFORMATION);
			}else{
				message("No hay información", "Si desea agregar o cambiar configuraciónes, llene algun campo", Alert.AlertType.INFORMATION);
				break;
			}
		}
	}
	private void resetConfig() {
		productsDao.resetConfig();
		message("Exito", "Se reiniciaron los valores correctamente", Alert.AlertType.CONFIRMATION);
	}

	@FXML
	private void closeWindow() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		saveButton.setOnAction(_ -> {
			saveConfig();
		});
		resetButton.setOnAction(_ -> {
			resetConfig();
		});
	}
}
