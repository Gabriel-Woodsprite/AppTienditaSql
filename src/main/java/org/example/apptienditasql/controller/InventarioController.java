package org.example.apptienditasql.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.apptienditasql.dao.InventoryDao;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.dao.SuppliersDao;
import org.example.apptienditasql.model.Purchase;
import org.example.apptienditasql.model.PurchaseDetail;
import org.example.apptienditasql.model.Supplier;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.validate;
import static org.example.apptienditasql.utils.UserMessage.message;

public class InventarioController {
	/// DAO
	InventoryDao inventoryDao = null;
	ProductsDao productsDao = null;
	SuppliersDao suppliersDao = null;

	/// PARTE SUPERIOR
	@FXML
	TextField txtFolio;
	@FXML
	DatePicker datePickerFecha;
	@FXML
	ComboBox<String> comboProveedor;

	/// PARTE SUPERIOR
	@FXML
	TextField txtBuscarProducto;
	@FXML
	TextField txtCantidad;
	@FXML
	TextField txtPrecioCompra;
	@FXML
	DatePicker datePickerCaducidad;
	@FXML
	Button btnAgregarProducto;

	/// TABLA DE COMPRAS
	@FXML
	TableView<PurchaseDetail> tablaDetalleCompra;
	@FXML
	TableColumn<PurchaseDetail, String> colProducto;
	@FXML
	TableColumn<PurchaseDetail, String> colCantidad;
	@FXML
	TableColumn<PurchaseDetail, String> colPrecioUnitario;
	@FXML
	TableColumn<PurchaseDetail, String> colSubtotal;
	@FXML
	TableColumn<PurchaseDetail, String> colCaducidad;
	@FXML
	TableColumn<PurchaseDetail, String> colEliminar;

	/// TOTAL Y BOTONES FINALES
	@FXML
	Label lblTotalCompra;
	@FXML
	Button btnGuardarCompra;
	@FXML
	Button btnCancelarCompra;

	private void agregarProducto() {
		PurchaseDetail purchaseDetail = new PurchaseDetail();
		if (validate(List.of(txtBuscarProducto, txtCantidad, txtPrecioCompra))) return;

		String barcode = txtBuscarProducto.getText();
		String productName;

		/// OBTENER PRODUCTO CON ID
		if (productsDao.getProductByBarcode(barcode) != null) {
			productName = productsDao.getProductByBarcode(barcode);
		} else {
			message("Atención", "El producto no existe ", Alert.AlertType.INFORMATION);
			return;
		}

		/// VALIDACIÓN DE CANTIDADES
		int inventoryQuantity = inventoryDao.selectInventoryQuantity(Integer.parseInt(barcode));
		int minQuantity = Integer.parseInt((productsDao.read(barcode)).getMinStock());
		int maxQuantity = Integer.parseInt((productsDao.read(barcode)).getMaxStock());
		int currentQuantity = Integer.parseInt(txtCantidad.getText());
		if (inventoryQuantity == -1) {
			if (currentQuantity < minQuantity) {
				message("Advertencia", "Debe registrar más producto", Alert.AlertType.INFORMATION);
				return;
			}
			if (currentQuantity > maxQuantity) {
				message("Advertencia", "No hay Suficiente Espacio", Alert.AlertType.INFORMATION);
				return;
			}
		} else {
			int updatedQuantity = inventoryQuantity + currentQuantity;
			if (updatedQuantity < minQuantity) {
				message("Advertencia", "Debe registrar más producto", Alert.AlertType.INFORMATION);
				return;
			}
			if (updatedQuantity > maxQuantity) {
				message("Advertencia", "No hay suficiente espacio\nYa existen " + inventoryQuantity + " elementos en el inventario", Alert.AlertType.INFORMATION);
				return;
			}
		}

		/// VALIDACIÓN DE FECHAS
		if (datePickerCaducidad.getValue() != null) {
			if (datePickerCaducidad.getValue().isBefore(LocalDate.now())) {
				message("Atención", "El producto está caducado", Alert.AlertType.WARNING);
				return;
			}
			if (datePickerFecha.getValue() != null) {
				if (datePickerCaducidad.getValue().isBefore(datePickerFecha.getValue())) {
					message("Atención", "El producto caducó antes de la fecha de compra", Alert.AlertType.WARNING);
					return;
				}
			} else {
				message("Atención", "Ingrese la fecha de compra primero", Alert.AlertType.INFORMATION);
				return;
			}
		}

		purchaseDetail.setBarcode(Integer.parseInt(barcode));
		purchaseDetail.setProductName(productName);
		purchaseDetail.setQuantity(txtCantidad.getText());
		purchaseDetail.setUnitPrice(txtPrecioCompra.getText());
		double subtotal = Double.parseDouble(txtPrecioCompra.getText()) * Double.parseDouble(txtCantidad.getText());
		purchaseDetail.setTotal(String.valueOf(subtotal));
		purchaseDetail.setExpiryDate(datePickerCaducidad.getValue());

		tablaDetalleCompra.getItems().add(purchaseDetail);
		updateTotal();

		resetValues();
	}

	private void guardarCompra() {
		Purchase purchase = new Purchase();

		List<Control> requiredList = List.of(txtFolio, datePickerFecha, comboProveedor, tablaDetalleCompra);
		if (validate(requiredList)) return;
		purchase.setPurchaseDate(datePickerFecha.getValue());
		purchase.setFolio(txtFolio.getText());
		purchase.setCost(new BigDecimal(lblTotalCompra.getText()));
		purchase.setSaleDetails(getDetails());
		purchase.setSupplierId(suppliersDao.getSupplierByName(comboProveedor.getValue()));

		inventoryDao.create(purchase);
		message("Exito", "Compra Guardada", Alert.AlertType.INFORMATION);
		resetValues();
		tablaDetalleCompra.getItems().clear();
	}

	private List<PurchaseDetail> getDetails() {
		return tablaDetalleCompra.getItems();
	}

	private void updateTotal() {
		double total = tablaDetalleCompra.getItems().stream()
				.mapToDouble(detail -> Double.parseDouble(detail.getTotal()))
				.sum();
		lblTotalCompra.setText(String.format("%.2f", total));
	}
	private void resetValues() {
		txtBuscarProducto.clear();
		txtCantidad.clear();
		txtPrecioCompra.clear();
		datePickerCaducidad.setValue(null);
	}

	private void cancelarCompra() {
		txtFolio.clear();
		datePickerFecha.setValue(null);
		comboProveedor.setValue(null);
		resetValues();
		tablaDetalleCompra.getItems().clear();
		lblTotalCompra.setText("");
	}

	private void tableColumnSetup() {
		colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
		colCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity()));
		colPrecioUnitario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnitPrice()));
		colSubtotal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotal()));
		colCaducidad.setCellValueFactory(cellData -> {
			System.out.println("FECHA INVENTCONT 127 " + cellData.getValue().getExpiryDate());
			if (cellData.getValue().getExpiryDate() != null) {
				return new SimpleStringProperty(String.valueOf(cellData.getValue().getExpiryDate()));
			}
			return new SimpleStringProperty("No aplica");
		});
	}


	@FXML
	public void initialize() throws SQLException {
		inventoryDao = new InventoryDao(DatabaseConnection.getConnection());
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		suppliersDao = new SuppliersDao(DatabaseConnection.getConnection());

		List<Supplier> supplierList = suppliersDao.readAll();
		supplierList.forEach(supplier -> comboProveedor.getItems().add(supplier.getName()));

		tableColumnSetup();

		btnAgregarProducto.setOnAction(_ -> agregarProducto());
		btnGuardarCompra.setOnAction(_ -> guardarCompra());
		btnCancelarCompra.setOnAction(_ -> cancelarCompra());
	}
}
