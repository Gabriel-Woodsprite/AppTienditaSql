package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.SuppliersDao;
import org.example.apptienditasql.model.Supplier;
import org.example.apptienditasql.utils.DatabaseConnection;
import org.example.apptienditasql.view.MainView;

import java.sql.SQLException;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.isFieldEmpty;
import static org.example.apptienditasql.utils.ControllerUtils.validate;
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
	@FXML
	private Button historialBtn;

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

		if (validate(requiredFields)) return;
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

	private void openHistoryView() throws Exception {
		/*___FXML___*/
		FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("history-view.fxml"));

		Scene scene = new Scene(fxmlLoader.load()/*, 1200, 864*/);
		Stage newStage = new Stage();

		newStage.setScene(scene);
		newStage.setTitle("Historial de Compras a Proveedores");
		newStage.setResizable(false);
		newStage.show();
	}


	@FXML
	public void initialize() throws SQLException {
		supplierDao = new SuppliersDao(DatabaseConnection.getConnection());
		insertToListView();
		type.setValue("Super Mercado");
		saveButton.setOnAction(_ -> createSupplier());
		historialBtn.setOnAction(_ -> {
			try {
				openHistoryView();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

}
