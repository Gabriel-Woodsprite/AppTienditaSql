package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.apptienditasql.dao.SuppliersDao;
import org.example.apptienditasql.model.Supplier;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.isFieldEmpty;
import static org.example.apptienditasql.utils.UserMessage.message;

public class ProveedoresController {
	SuppliersDao supplierDao = null;
	ObservableList<HBox> suppliersObsList = FXCollections.observableArrayList();

	@FXML
	private ListView<HBox> suppliersListView;
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

	private void insertToListView() {
		List<Supplier> supplierList = supplierDao.readAll();
		suppliersListView.getItems().clear();

		for (Supplier supplier : supplierList) {
			HBox hbox = new HBox();
			Button delete = new Button("Eliminar");
			Button edit = new Button("Editar");
			edit.setDisable(true);

			delete.setOnAction(_ -> {
				supplierDao.delete(String.valueOf(supplier.getSupplierId()));
				insertToListView();
			});
//			edit.setOnAction(e -> {
//			});

			hbox.setSpacing(30);
			hbox.getChildren().addAll(new Pane(new Label(supplier.getName())), new Pane(delete), new Pane(edit));

			suppliersObsList.add(hbox);
		}

		suppliersListView.setItems(suppliersObsList);
	}

	private void createSupplier() {
		Supplier supplier = new Supplier();

		List<Control> requiredFields = List.of(nombre_razon, contactPerson, tel, email, type, address, notes);

		if (Validate(requiredFields)) return;
		supplier.setName(nombre_razon.getText());
		supplier.setContactPerson(contactPerson.getText());
		supplier.setEmail(email.getText());
		supplier.setAddress(address.getText());
		supplier.setNotes(notes.getText());
		supplier.setType(type.getValue());
		supplier.setTel(tel.getText());
		supplierDao.create(supplier);

		insertToListView();
	}

	static boolean Validate(List<Control> requiredFields) {
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
				return true;
			}
		}
		return false;
	}

	@FXML
	public void initialize() throws SQLException {
		supplierDao = new SuppliersDao(DatabaseConnection.getConnection());
		insertToListView();
		type.setValue("Super Mercado");
		saveButton.setOnAction(e -> {
			createSupplier();
		});
	}

}
