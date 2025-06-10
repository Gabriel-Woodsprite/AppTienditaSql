package org.example.apptienditasql.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.dao.SuppliersDao;
import org.example.apptienditasql.model.Supplier;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.isFieldEmpty;
import static org.example.apptienditasql.utils.UserMessage.message;

public class ProveedoresController {
	SuppliersDao supplierDao = null;
	@FXML
	private TextField nombre_razon;
	@FXML
	private TextField contactPerson;
	@FXML
	private TextField tel;
	@FXML
	private TextField email;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private TextField address;
	@FXML
	private TextArea notes;
	@FXML
	private Button saveButton;

	private void createSupplier() {
		Supplier supplier = new Supplier();

		List<Control> requiredFields = List.of(nombre_razon, contactPerson, tel, email, type, address, notes);

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
		supplier.setName(nombre_razon.getText());
		supplier.setContactPerson(contactPerson.getText());
		supplier.setEmail(email.getText());
		supplier.setAddress(address.getText());
		supplier.setNotes(notes.getText());
		supplier.setType(type.getValue());
		supplier.setTel(tel.getText());

		if(supplierDao.create(supplier)){
			message("Exito", "Proveedor creado", Alert.AlertType.INFORMATION);
		}else{
			message("Error", "Hubo un error", Alert.AlertType.ERROR);
		}
	}

	@FXML
	public void initialize() throws SQLException {
		supplierDao = new SuppliersDao(DatabaseConnection.getConnection());
		type.setValue("Super Mercado");
		saveButton.setOnAction(e -> {
			createSupplier();
		});
	}

}
