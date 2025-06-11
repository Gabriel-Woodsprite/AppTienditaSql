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
		int subtotal = 0;
		if (!txtBuscarProducto.getText().isBlank()) {
			if (!txtCantidad.getText().isBlank()) {
				product = productsDao.read(txtBuscarProducto.getText());
				product.setPurchaseQuantity(txtCantidad.getText());

				if (product != null) {
					System.out.println("Txt Cant: " + txtCantidad.getText() + " productCant: " + product.getCantidad());
					if (Integer.parseInt(txtCantidad.getText()) <= Integer.parseInt(product.getCantidad())) {
						lblFecha.setText(LocalDate.now().toString());
						subtotal += Integer.parseInt(product.getPrice());
						lblSubtotal.setText(subtotal+"");
						lblIVA.setText(subtotal*0.16+"");
						lista.add(product);
					} else {
						message("Error", "No hay tantos productos", Alert.AlertType.ERROR);
					}
				} else {
					message("Error", "No se encontró el producto", Alert.AlertType.ERROR);
				}
			}
		} else {
			message("Error", "Datos incorrectos o incompletos", Alert.AlertType.ERROR);
		}
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
		colProducto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		colCantidad.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getpCantidad()));
		colPrecio.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPrice()));
		colSubtotal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSubTotal()));

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

