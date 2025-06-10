package org.example.apptienditasql.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.apptienditasql.utils.createProductUtil.isFieldEmpty;

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
	private Button saveButton;

	private void saveConfig(){
		List<Control> required = List.of(unidadMedida, categoria, presentacion, ubicacion);
		for (Control requiredField : required) {
			if (!isFieldEmpty(requiredField)) {
				switch (requiredField.getId()) {
					case "unidadMedida":

				}
			}
		}
	}

	@FXML
	public void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		saveButton.setOnAction(e -> {
			saveConfig();
		});
	}
}
