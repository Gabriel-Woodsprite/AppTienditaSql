package org.example.apptienditasql.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.example.apptienditasql.utils.UserMessage.message;

public class VentasController {
	ProductsDao productsDao;
	@FXML
	private TextField txtBuscarProducto;
	@FXML
	private TextField txtCantidad;
	@FXML
	private Button btnAgregar;
	@FXML
	private TableView<Product> tablaVenta;
	@FXML
	private TableColumn<Product, String> colProducto;
	@FXML
	private TableColumn<Product, String> colCantidad;
	@FXML
	private TableColumn<Product, String> colPrecio;
	@FXML
	private TableColumn<Product, String> colSubtotal;
	@FXML
	private TableColumn<Product, String> colEliminar;
	@FXML
	private Button btnCancelarVenta;
	@FXML
	private Label lblFolio;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblSubtotal;
	@FXML
	private Label lblIVA;
	@FXML
	private Label lblTotal;
	@FXML
	private Button btnFinalizarVenta;
	@FXML
	private TextArea txtAreaTicket;

	private final ObservableList<Product> lista = FXCollections.observableArrayList();

	Product product;

	private void agregarProducto() {
	}

	private void cancelarVenta() {
		txtBuscarProducto.setText("");
		txtCantidad.setText("");
		lista.clear();
	}

	private void registrarVenta() {
		message("Ups", "No se realizó ninguna compra", Alert.AlertType.INFORMATION);
	}


	@FXML
	public void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());

		tablaVenta.setItems(lista);

		btnAgregar.setOnAction(_ -> {
			agregarProducto();
		});
		btnCancelarVenta.setOnAction(_ -> {
			cancelarVenta();
		});
		btnFinalizarVenta.setOnAction(_ -> {
			registrarVenta();
		});
	}
}

