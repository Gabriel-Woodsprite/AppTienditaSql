package org.example.apptienditasql.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.apptienditasql.dao.InventoryDao;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.dao.SalesDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.model.Sale;
import org.example.apptienditasql.model.SaleDetail;
import org.example.apptienditasql.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.apptienditasql.utils.ControllerUtils.validate;
import static org.example.apptienditasql.utils.UserMessage.message;

public class VentasController {
	ProductsDao productsDao;
	SalesDao salesDao;
	InventoryDao inventoryDao;

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

	private void agregarProducto() {
		if (validate(List.of(txtBuscarProducto, txtCantidad))) return;

		try {
			int barCode = Integer.parseInt(txtBuscarProducto.getText());
			int cantidad = Integer.parseInt(txtCantidad.getText());

			Product producto = productsDao.read(txtBuscarProducto.getText());
			if (producto == null) {
				message("Error", "Este producto no existe", Alert.AlertType.WARNING);
				return;
			}

			int disponible = inventoryDao.selectInventoryQuantity(barCode);
			if (cantidad > disponible) {
				message("Advertencia", "No hay suficientes productos en inventario", Alert.AlertType.INFORMATION);
				return;
			}

			double precioBase = inventoryDao.selectProductPrice(barCode);
			double margen = Double.parseDouble(productsDao.readPriceMargin());

			double precioUnitario = precioBase + (precioBase * (margen / 100.0));
			System.out.println("PRECIO UNITARIO 87: " + precioUnitario);
			double subtotal = precioUnitario * cantidad;

			Product productoVenta = new Product();
			productoVenta.setName(producto.getName());
			productoVenta.setQuantity(cantidad);
			productoVenta.setPrice(precioUnitario);
			productoVenta.setSubtotal(subtotal);

			System.out.println("PRODUCTOVENTA 99: "+productoVenta);

			lista.add(productoVenta);
			tablaVenta.setItems(lista);

			txtBuscarProducto.clear();
			txtCantidad.clear();

			double total = lista.stream().mapToDouble(Product::getSubtotal).sum();
			double iva = total * 0.16;
			double totalConIVA = total + iva;

			lblSubtotal.setText(String.format("%.2f", total));
			lblIVA.setText(String.format("%.2f", iva));
			lblTotal.setText(String.format("%.2f", totalConIVA));


		} catch (NumberFormatException e) {
			message("Error", "Formato inválido en código o cantidad", Alert.AlertType.ERROR);
		} catch (Exception e) {
			message("Error", "Hubo un problema al agregar el producto", Alert.AlertType.ERROR);
			e.printStackTrace();
		}
	}

	private void tableColumnSet() {
		colProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		colCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
		colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
		colSubtotal.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSubtotal())));
	}


	private void cancelarVenta() {
		txtBuscarProducto.setText("");
		txtCantidad.setText("");
		lista.clear();
		setStartData();
	}

	private void registrarVenta() {
		Sale sale = new Sale();
		if (tablaVenta.getItems().isEmpty()) {
			message("Ups", "No se realizó ninguna compra", Alert.AlertType.INFORMATION);
			return;
		}

		sale.setFolio(Integer.parseInt(lblFolio.getText()));
		sale.setDateSold(LocalDate.parse(lblFecha.getText()));
		sale.setSaleDetails(getDetails());
		salesDao.create(sale);
		for (SaleDetail detalle : getDetails()) {
			inventoryDao.decreaseQuantity(detalle.getCatalogueBarcode(), Integer.parseInt(detalle.getQuantity()));
		}

		message("Exito", "La venta se realizó exitosamente", Alert.AlertType.INFORMATION);
		tablaVenta.getItems().clear();
	}

	private List<SaleDetail> getDetails() {
		List<SaleDetail> detalles = new ArrayList<>();

		int folio = Integer.parseInt(lblFolio.getText());

		for (Product p : lista) {
			SaleDetail detalle = new SaleDetail();

			detalle.setQuantity(String.valueOf(p.getQuantity()));
			detalle.setPrice(BigDecimal.valueOf(p.getPrice()));
			detalle.setSubTotal(BigDecimal.valueOf(p.getSubtotal()));

			detalle.setTotal(BigDecimal.valueOf(p.getSubtotal()));

			detalle.setSaleFolio(folio);

			String barCode = productsDao.getBarcodeByName(p.getName());
			System.out.println("BARCODE 170: "+barCode);
			detalle.setCatalogueBarcode(barCode);

			detalles.add(detalle);
		}

		return detalles;
	}


	private void setStartData() {
		int salesCount = salesDao.getCount();

		lblFolio.setText(String.valueOf(salesCount + 1));
		lblFecha.setText(String.valueOf(LocalDate.now()));
		lblSubtotal.setText("0.00");
		lblIVA.setText("0.00");
		lblTotal.setText("0.00");
	}


	@FXML
	public void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		salesDao = new SalesDao(DatabaseConnection.getConnection());
		inventoryDao = new InventoryDao(DatabaseConnection.getConnection());

		tableColumnSet();
		setStartData();

		btnAgregar.setOnAction(_ -> agregarProducto());
		btnCancelarVenta.setOnAction(_ -> cancelarVenta());
		btnFinalizarVenta.setOnAction(_ -> registrarVenta());
	}
}